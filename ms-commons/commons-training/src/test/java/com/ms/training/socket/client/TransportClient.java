package com.ms.training.socket.client;


import java.io.IOException;

/**
 * Created by mark.zhu on 2016/3/11.
 */
public interface TransportClient {
    byte[] send(byte[] data) throws IOException;
}
