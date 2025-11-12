package cn.keepbx.jpom.system;

import ch.qos.logback.core.PropertyDefinerBase;
import cn.hutool.core.util.StrUtil;
import cn.jiangzeyin.common.DefaultSystemLog;
import cn.keepbx.jpom.BaseJpomApplication;
import cn.keepbx.jpom.model.system.JpomManifest;
import cn.keepbx.jpom.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * 自动记录日志
 *
 * @author jiangzeyin
 * @date 2017/5/11
 */
@Aspect
@Component
public class WebAopLog extends PropertyDefinerBase {
    private static final ThreadLocal<Boolean> IS_LOG = new ThreadLocal<>();

    private static volatile AopLogInterface aopLogInterface;

    synchronized public static void setAopLogInterface(AopLogInterface aopLogInterface) {
        WebAopLog.aopLogInterface = aopLogInterface;
    }

    @Pointcut("execution(public * cn.keepbx.jpom.controller..*.*(..))")
    public void webLog() {
        //
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        if (aopLogInterface != null) {
            aopLogInterface.before(joinPoint);
        }
        // 接收到请求，记录请求内容
        IS_LOG.set(true);
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            ResponseBody responseBody = methodSignature.getMethod().getAnnotation(ResponseBody.class);
            if (responseBody == null) {
                RestController restController = joinPoint.getTarget().getClass().getAnnotation(RestController.class);
                if (restController == null) {
                    IS_LOG.set(false);
                }
            }
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        if (aopLogInterface != null) {
            aopLogInterface.afterReturning(ret);
        }
        if (ret == null) {
            return;
        }
        // 处理完请求，返回内容
        Boolean isLog = IS_LOG.get();
        if (isLog != null && !isLog) {
            return;
        }
        DefaultSystemLog.LOG().info(" :" + ret.toString());
        IS_LOG.remove();
    }

    @Override
    public String getPropertyValue() {
        String path = StringUtil.getArgsValue(BaseJpomApplication.getArgs(), "jpom.log");
        if (StrUtil.isEmpty(path)) {
            //
            File file = JpomManifest.getRunPath();
            if (file.isFile()) {
                file = file.getParentFile().getParentFile();
            }
            file = new File(file, "log");
            path = file.getPath();
        }
        // 配置默认日志路径
        DefaultSystemLog.configPath(path, false);
        return path;
    }
}
