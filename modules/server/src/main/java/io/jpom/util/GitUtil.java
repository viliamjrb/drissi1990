package io.jpom.util;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Tuple;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import io.jpom.system.ConfigBean;
import io.jpom.system.JpomRuntimeException;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * git工具
 * <p>
 * https://developer.aliyun.com/ask/275691
 *
 * @author bwcx_jzy
 * @date 2019/7/15
 **/
public class GitUtil {

	/**
	 * 检查本地的remote是否存在对应的url
	 *
	 * @param url  要检查的url
	 * @param file 本地仓库文件
	 * @return true 存在对应url
	 * @throws IOException     IO
	 * @throws GitAPIException E
	 */
	private static boolean checkRemoteUrl(String url, File file) throws IOException, GitAPIException {
		try (Git git = Git.open(file)) {
			RemoteListCommand remoteListCommand = git.remoteList();
			boolean urlTrue = false;
			List<RemoteConfig> list = remoteListCommand.call();
			end:
			for (RemoteConfig remoteConfig : list) {
				for (URIish urIish : remoteConfig.getURIs()) {
					if (urIish.toString().equals(url)) {
						urlTrue = true;
						break end;
					}
				}
			}
			return urlTrue;
		}
	}

	/**
	 * 删除重新clone
	 *
	 * @param url                 url
	 * @param file                文件
	 * @param credentialsProvider 凭证
	 * @return git
	 * @throws GitAPIException api
	 * @throws IOException     删除文件失败
	 */
	private static Git reClone(String url, String branchName, File file, CredentialsProvider credentialsProvider, PrintWriter printWriter) throws GitAPIException, IOException {
		if (!FileUtil.clean(file)) {
			FileUtil.del(file.toPath());
			//throw new IOException("del error:" + file.getPath());
		}
		CloneCommand cloneCommand = Git.cloneRepository();
		if (printWriter != null) {
			cloneCommand
					.setProgressMonitor(new TextProgressMonitor(printWriter));
		}
		if (branchName != null) {
			cloneCommand.setBranch(Constants.R_HEADS + branchName);
			cloneCommand.setBranchesToClone(Collections.singletonList(Constants.R_HEADS + branchName));
		}
		return cloneCommand.setURI(url)
				.setDirectory(file)
				.setCredentialsProvider(credentialsProvider)
				.call();
	}

	private static Git initGit(String url, String branchName, File file, CredentialsProvider credentialsProvider, PrintWriter printWriter) throws IOException, GitAPIException {
		Git git;
		if (FileUtil.file(file, Constants.DOT_GIT).exists()) {
			if (checkRemoteUrl(url, file)) {
				git = Git.open(file);
				//
				PullCommand pull = git.pull();
				if (printWriter != null) {
					pull.setProgressMonitor(new TextProgressMonitor(printWriter));
				}
				pull.setCredentialsProvider(credentialsProvider).call();
			} else {
				git = reClone(url, branchName, file, credentialsProvider, printWriter);
			}
		} else {
			git = reClone(url, branchName, file, credentialsProvider, printWriter);
		}
		return git;
	}

	/**
	 * 获取仓库远程的所有分支
	 *
	 * @param url                 远程url
	 * @param credentialsProvider 凭证
	 * @return Tuple
	 * @throws GitAPIException api
	 */
	public static Tuple getBranchAndTagList(String url, CredentialsProvider credentialsProvider) throws GitAPIException {
		synchronized (url.intern()) {
			try {
				Collection<Ref> call = Git.lsRemoteRepository()
						.setRemote(url)
						.setCredentialsProvider(credentialsProvider)
						.setHeads(true)
						.setTags(true)
						.call();
				if (CollUtil.isEmpty(call)) {
					return null;
				}
				Map<String, List<Ref>> refMap = CollStreamUtil.groupByKey(call, ref -> {
					String name = ref.getName();
					if (name.startsWith(Constants.R_TAGS)) {
						return Constants.R_TAGS;
					} else if (name.startsWith(Constants.R_HEADS)) {
						return Constants.R_HEADS;
					}
					return null;
				});

				// branch list
				List<Ref> branchListRef = refMap.get(Constants.R_HEADS);
				List<String> branchList = branchListRef.stream().map(ref -> {
					String name = ref.getName();
					if (name.startsWith(Constants.R_HEADS)) {
						return name.substring((Constants.R_HEADS).length());
					}
					return null;
				}).filter(Objects::nonNull).collect(Collectors.toList());

				// list tag
				List<Ref> tagListRef = refMap.get(Constants.R_TAGS);
				List<String> tagList = tagListRef.stream().map(ref -> {
					String name = ref.getName();
					if (name.startsWith(Constants.R_TAGS)) {
						return name.substring((Constants.R_TAGS).length());
					}
					return null;
				}).filter(Objects::nonNull).collect(Collectors.toList());
				return new Tuple(branchList, tagList);
			} catch (TransportException t) {
				checkTransportException(t);
				return null;
			}
		}
	}

	public static List<String> getBranchList(String url, String userName, String userPwd) throws GitAPIException {
		Tuple tuple = getBranchAndTagList(url, new UsernamePasswordCredentialsProvider(userName, userPwd));
		List<String> tag = tuple == null ? null : tuple.get(0);
		if (CollUtil.isEmpty(tag)) {
			throw new JpomRuntimeException("该仓库还没有任何分支");
		}
		return tuple.get(0);
	}


	/**
	 * 拉取对应分支最新代码
	 *
	 * @param url                 远程url
	 * @param file                仓库路径
	 * @param branchName          分支名
	 * @param credentialsProvider 凭证
	 * @throws IOException     IO
	 * @throws GitAPIException api
	 */
	public static void checkoutPull(String url, File file, String branchName, CredentialsProvider credentialsProvider, PrintWriter printWriter) throws IOException, GitAPIException {
		synchronized (url.intern()) {
			try (Git git = initGit(url, branchName, file, credentialsProvider, printWriter)) {
				// 判断本地是否存在对应分支
				List<Ref> list = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
				boolean createBranch = true;
				for (Ref ref : list) {
					String name = ref.getName();
					if (name.startsWith(Constants.R_HEADS + branchName)) {
						createBranch = false;
						break;
					}
				}
				// 切换分支
				if (!StrUtil.equals(git.getRepository().getBranch(), branchName)) {
					printWriter.println(StrUtil.format("start switch branch from {} to {}", git.getRepository().getBranch(), branchName));
					git.checkout().
							setCreateBranch(createBranch).
							setName(branchName).
							setForceRefUpdate(true).
							setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM).
							setProgressMonitor(new TextProgressMonitor(printWriter)).
							call();
				}
				git.pull().setCredentialsProvider(credentialsProvider).call();
			} catch (TransportException t) {
				checkTransportException(t);
			}
		}
	}

	/**
	 * 检查异常信息
	 *
	 * @param ex 异常信息
	 * @throws TransportException 非账号密码异常
	 */
	private static void checkTransportException(TransportException ex) throws TransportException {
		String msg = ex.getMessage();
		if (msg.contains(JGitText.get().notAuthorized) || msg.contains(JGitText.get().authenticationNotSupported)) {
			throw new JpomRuntimeException("git账号密码不正常", ex);
		}
		throw ex;
	}

	private static AnyObjectId getAnyObjectId(Git git, String branchName) throws GitAPIException {
		List<Ref> list = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
		for (Ref ref : list) {
			String name = ref.getName();
			if (name.startsWith(Constants.R_HEADS + branchName)) {
				return ref.getObjectId();
			}
		}
		return null;
	}

	/**
	 * 获取对应分支的最后一次提交记录
	 *
	 * @param file       仓库文件夹
	 * @param branchName 分支
	 * @return 描述
	 * @throws IOException     IO
	 * @throws GitAPIException api
	 */
	public static String getLastCommitMsg(File file, String branchName) throws IOException, GitAPIException {
		try (Git git = Git.open(file)) {
			AnyObjectId anyObjectId = getAnyObjectId(git, branchName);
			Objects.requireNonNull(anyObjectId, "没有" + branchName + "分支");
			RevWalk walk = new RevWalk(git.getRepository());
			RevCommit revCommit = walk.parseCommit(anyObjectId);
			String time = new DateTime(revCommit.getCommitTime() * 1000L).toString();
			PersonIdent personIdent = revCommit.getAuthorIdent();
			return StrUtil.format("{} {} {}[{}] {}", branchName, revCommit.getShortMessage(), personIdent.getName(), personIdent.getEmailAddress(), time);
		}
	}
}
