package io.jpom.common;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.extra.servlet.ServletUtil;
import cn.jiangzeyin.common.DefaultSystemLog;
import cn.jiangzeyin.common.JsonMessage;
import io.jpom.system.AgentException;
import io.jpom.system.AuthorizeException;
import io.jpom.system.JpomRuntimeException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;

/**
 * 全局异常处理
 *
 * @author jiangzeyin
 * @date 2019/04/17
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
//	private static final TimedCache<String, String> TIMED_CACHE = new TimedCache<>(TimeUnit.MINUTES.toMillis(1));
//
//	public static String getErrorMsg(String id) {
//		return TIMED_CACHE.get(id);
//	}

	/**
	 * 声明要捕获的异常
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param e        异常
	 */
	@ExceptionHandler({AuthorizeException.class, RuntimeException.class, Exception.class})
	public void delExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		//DefaultSystemLog.getLog().error("controller " + request.getRequestURI(), e.getMessage());
		DefaultSystemLog.getLog().error("global handle exception: {}", request.getRequestURI(), e);
//        if (BaseJpomInterceptor.isPage(request)) {
//            try {
//                String id = IdUtil.fastUUID();
//                TIMED_CACHE.put(id, getErrorMsg(e));
//                BaseJpomInterceptor.sendRedirects(request, response, "/error.html?id=" + id);
//            } catch (IOException ex) {
//                /**
//                 * @author Hotstrip
//                 * don't print stach trace into console
//                 */
//                // ex.printStackTrace();
//                DefaultSystemLog.getLog().error("catch exception: {}, and message: {}", ex.getCause(), ex.getMessage());
//            }
//        } else {
		if (e instanceof AuthorizeException) {
			AuthorizeException authorizeException = (AuthorizeException) e;
			ServletUtil.write(response, authorizeException.getJsonMessage().toString(), MediaType.APPLICATION_JSON_VALUE);
		} else if (e instanceof JpomRuntimeException) {
			ServletUtil.write(response, JsonMessage.getString(500, e.getMessage()), MediaType.APPLICATION_JSON_VALUE);
		} else {
			boolean causedBy = ExceptionUtil.isCausedBy(e, AccessDeniedException.class);
			if (causedBy) {
				ServletUtil.write(response, JsonMessage.getString(500, "操作文件权限异常,请手动处理：" + e.getMessage()), MediaType.APPLICATION_JSON_VALUE);
				return;
			}
			ServletUtil.write(response, JsonMessage.getString(500, "服务异常：" + e.getMessage()), MediaType.APPLICATION_JSON_VALUE);
		}
//        }
	}

	/**
	 * 插件端异常
	 * <p>
	 * 避免重复记录堆栈
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param e        异常
	 * @author jzy
	 * @since 2021-08-01
	 */
	@ExceptionHandler({AgentException.class})
	public void agentExceptionHandler(HttpServletRequest request, HttpServletResponse response, AgentException e) {
		Throwable cause = e.getCause();
		if (cause != null) {
			/**
			 * @author Hotstrip
			 * @date 2021-08-01
			 * only show exception message rather than exception object
			 */
			DefaultSystemLog.getLog().error("controller " + request.getRequestURI(), cause.getMessage());
		}
		ServletUtil.write(response, JsonMessage.getString(405, e.getMessage()), MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 声明要捕获的异常 (参数，状态，验证异常)
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param e        异常
	 */
	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, ValidateException.class})
	public void paramExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		DefaultSystemLog.getLog().error("controller " + request.getRequestURI(), e);
		ServletUtil.write(response, JsonMessage.getString(405, e.getMessage()), MediaType.APPLICATION_JSON_VALUE);
	}
}
