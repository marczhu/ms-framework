package com.ms.admin.init;


/**
 * TODO 统一service入口
 *
 * Created by mark.zhu on 2015/11/30.
 */
public interface Service {
    void init() throws Exception;
    void start() throws Exception;
    void stop();
    boolean isRunning();
    /**启动失败自动终止*/
    boolean isStopOnError();
    /**是否异步启动*/
    boolean isAsync();

}
