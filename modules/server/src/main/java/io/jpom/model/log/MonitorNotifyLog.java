/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 码之科技工作室
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.jpom.model.log;

import io.jpom.model.BaseJsonModel;
import io.jpom.model.data.MonitorModel;

/**
 * 监控日志
 *
 * @author bwcx_jzy
 * @date 2019/7/13
 */
public class MonitorNotifyLog extends BaseJsonModel {
    /**
     * 表名
     */
    public static final String TABLE_NAME = MonitorNotifyLog.class.getSimpleName().toUpperCase();
    /**
     *
     */
    private String logId;
    private String nodeId;
    private String projectId;
    /**
     * 异常发生时间
     */
    private Long createTime;
    private String title;
    private String content;
    /**
     * 项目状态状态
     */
    private boolean status;
    /**
     * 通知方式
     *
     * @see MonitorModel.NotifyType
     */
    private int notifyStyle;
    /**
     * 通知发送状态
     */
    private boolean notifyStatus;
    /**
     * 监控id
     */
    private String monitorId;
    /**
     * 通知对象
     */
    private String notifyObject;
    /**
     * 通知异常消息
     */
    private String notifyError;

    public String getNotifyObject() {
        return notifyObject;
    }

    public void setNotifyObject(String notifyObject) {
        this.notifyObject = notifyObject;
    }

    public String getNotifyError() {
        return notifyError;
    }

    public void setNotifyError(String notifyError) {
        this.notifyError = notifyError;
    }

    public boolean isNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(boolean notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public int getNotifyStyle() {
        return notifyStyle;
    }

    public void setNotifyStyle(int notifyStyle) {
        this.notifyStyle = notifyStyle;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
