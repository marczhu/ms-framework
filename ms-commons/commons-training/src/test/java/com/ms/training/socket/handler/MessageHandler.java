package com.ms.training.socket.handler;

/**
 * Created by mark.zhu on 2016/3/11.
 */
public interface MessageHandler<T extends Message> {
    T handle(T data) throws Exception;
}
