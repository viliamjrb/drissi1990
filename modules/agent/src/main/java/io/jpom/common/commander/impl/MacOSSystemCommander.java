package io.jpom.common.commander.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.StrSpliter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import cn.jiangzeyin.common.DefaultSystemLog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jpom.common.commander.AbstractSystemCommander;
import io.jpom.model.system.ProcessModel;
import io.jpom.util.CommandUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MacOSSystemCommander extends AbstractSystemCommander {
    @Override
    public JSONObject getAllMonitor() {
        String result = CommandUtil.execSystemCommand("top -l 1 | head");
        if (StrUtil.isEmpty(result)) {
            return null;
        }
        String[] split = result.split(StrUtil.LF);
        int length = split.length;
        JSONObject jsonObject = new JSONObject();
        // cpu 在第 4 行，下标为 3
        if (length > 3) {
            String cpus = split[3];
            // cpu占比
            String cpu = getLinuxCpu(cpus);
            jsonObject.put("cpu", cpu);
        }
        // 内存占比 第 7 行 下标为 6
        if (length > 6) {
            String mem = split[6];
            //内存占比
            String memory = getLinuxMemory(mem);
            jsonObject.put("memory", memory);
        }
        jsonObject.put("disk", getHardDisk());
        DefaultSystemLog.getLog().info("Mac OS monitor data: {}", jsonObject.toJSONString());
        return jsonObject;
    }

    /**
     * 返回内存占比信息
     * 这里返回的数据跟 Mac OS 自带活动监视器显示的内存压力不是同一种东西
     * 内存使用占比高，不代表内存压力就大
     * @param info
     * @return 已使用的 / 总内存 * 100%
     */
    private String getLinuxMemory(final String info) {
        if (StrUtil.isEmpty(info)) {
            return null;
        }
        double used = 0, free = 0;
        DefaultSystemLog.getLog().debug("Mac Os mem info: {}", info);
        int index = info.indexOf(":") + 1;
        String[] split = info.substring(index).split(",");
        for (String str : split) {
            str = str.trim();
            if (str.contains("unused.")) {
                String value = str.split("\\s+")[0].replace("M", "");
                free = Convert.toDouble(value, 0.0);
            } else if (str.contains("used")) {
                String value = str.split("\\s+")[0].replace("M", "");
                used = Convert.toDouble(value, 0.0);
            }
        }
        DefaultSystemLog.getLog().debug("Mac OS mem: used: {}, unused: {}", used, free);
        return String.format("%.2f", used / (used + free) * 100);
    }

    /**
     * 返回 Mac OS cpu 占用信息
     * @param info
     * @return 100 - idle (100 - 空闲的 cpu)
     */
    private String getLinuxCpu(final String info) {
        if (StrUtil.isEmpty(info)) {
            return null;
        }
        DefaultSystemLog.getLog().debug("Mac Os cpu info: {}", info);
        int i = info.indexOf(":");
        String[] split = info.substring(i + 1).split(",");
        for (String str : split) {
            str = str.trim();
            if (str.contains("idle")) {
                String value = str.split("\\s+")[0].replace("%", "");
                double val = Convert.toDouble(value, 0.0);
                return String.format("%.2f", 100.00 - val);
            }
        }
        return "0";
    }

    @Override
    public List<ProcessModel> getProcessList() {
        String s = CommandUtil.execSystemCommand("top -l 1 | grep java");
        return formatLinuxTop(s, false);
    }

    /**
     * 把 top 返回的数据组装成集合
     * @param top
     * @param header 是否有 header
     * @return
     */
    private List<ProcessModel> formatLinuxTop(final String top, final boolean header) {
        List<String> list = StrSpliter.splitTrim(top, StrUtil.LF, true);
        if (list.size() <= 0) {
            return null;
        }
        List<ProcessModel> list1 = new ArrayList<>();
        ProcessModel processModel;
        for (String item : list) {
            processModel = new ProcessModel();
            DefaultSystemLog.getLog().debug("process item: {}", item);
            List<String> values = StrSpliter.splitTrim(item, StrUtil.SPACE, true);
            DefaultSystemLog.getLog().debug(JSON.toJSONString(values));
            processModel.setPid(Integer.parseInt(values.get(0)));
            processModel.setCommand(values.get(1));
            processModel.setCpu(values.get(2) + "%");
            processModel.setMem(values.get(14) + "%");
            processModel.setStatus(formStatus(values.get(12)));
            processModel.setTime(values.get(3));
            processModel.setRes(values.get(7));
            processModel.setUser(values.get(29));
            list1.add(processModel);
        }
        return list1;
    }

    private String formStatus(final String val) {
        String tempVal = val.toUpperCase();
        String value = "未知";
        if (tempVal.startsWith("S")) {
            value = "睡眠";
        } else if (tempVal.startsWith("R")) {
            value = "运行";
        } else if (tempVal.startsWith("T")) {
            value = "跟踪/停止";
        } else if (tempVal.startsWith("Z")) {
            value = "僵尸进程 ";
        } else if (tempVal.startsWith("D")) {
            value = "不可中断的睡眠状态 ";
        } else if (tempVal.startsWith("I")) {
            value = "多线程 ";
        }
        return value;
    }

    @Override
    public ProcessModel getPidInfo(int pid) {
        String command = "top -l 1 | grep " + pid;
        String internal = CommandUtil.execSystemCommand(command);
        List<ProcessModel> processModels = formatLinuxTop(internal, true);
        if (processModels == null || processModels.isEmpty()) {
            return null;
        }
        return processModels.get(0);
    }

    @Override
    public boolean getServiceStatus(String serviceName) {
        if (StrUtil.startWith(serviceName, StrUtil.SLASH)) {
            String ps = getPs(serviceName);
            return StrUtil.isNotEmpty(ps);
        }
        String format = StrUtil.format("service {} status", serviceName);
        String result = CommandUtil.execSystemCommand(format);
        return StrUtil.containsIgnoreCase(result, "RUNNING");
    }

    private String getPs(final String serviceName) {
        String ps = StrUtil.format(" ps -ef |grep -w {} | grep -v grep", serviceName);
        return CommandUtil.execSystemCommand(ps);
    }

    @Override
    public String startService(String serviceName) {
        if (StrUtil.startWith(serviceName, StrUtil.SLASH)) {
            try {
                CommandUtil.asyncExeLocalCommand(new File(SystemUtil.getUserInfo().getHomeDir()), serviceName);
                return "ok";
            } catch (Exception e) {
                DefaultSystemLog.getLog().error("执行异常", e);
                return "执行异常：" + e.getMessage();
            }
        }
        String format = StrUtil.format("service {} start", serviceName);
        return CommandUtil.execSystemCommand(format);
    }

    @Override
    public String stopService(String serviceName) {
        if (StrUtil.startWith(serviceName, StrUtil.SLASH)) {
            String ps = getPs(serviceName);
            List<String> list = StrUtil.splitTrim(ps, StrUtil.LF);
            if (list == null || list.isEmpty()) {
                return "stop";
            }
            String s = list.get(0);
            list = StrUtil.splitTrim(s, StrUtil.SPACE);
            if (list == null || list.size() < 2) {
                return "stop";
            }
            File file = new File(SystemUtil.getUserInfo().getHomeDir());
            int pid = Convert.toInt(list.get(1), 0);
            if (pid <= 0) {
                return "error stop";
            }
            return kill(file, pid);
        }
        String format = StrUtil.format("service {} stop", serviceName);
        return CommandUtil.execSystemCommand(format);
    }

    @Override
    public String buildKill(int pid) {
        return String.format("kill  %s", pid);
    }
}
