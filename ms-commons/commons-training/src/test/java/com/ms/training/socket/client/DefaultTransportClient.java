package com.ms.training.socket.client;

import com.ms.training.socket.Closer;
import com.ms.training.socket.SocketConfig;

import java.io.*;
import java.net.Socket;

/**
 * Created by mark.zhu on 2016/3/30.
 */
public class DefaultTransportClient {
    private final int DEFAULT_SO_TIMEOUT = 60000;
    private final String ip;
    private final int port;
    private int soTimeout;
    private SocketConfig socketConfig;

    public DefaultTransportClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.soTimeout = DEFAULT_SO_TIMEOUT;
    }

    public byte[] send(byte[] data) throws IOException {
        Socket socket = null;
        OutputStream out = null;
        InputStream in = null;
        ByteArrayOutputStream baos = null;
        try {
            socket = new Socket(ip, port);
            socket.setKeepAlive(true);
            socket.setSoTimeout(soTimeout);
            configure(socket);
            out = new BufferedOutputStream(socket.getOutputStream());
            out.write(data);
            in = socket.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            for(int len = in.read(buffer);len != -1;){
                baos.write(buffer);
            }
        } finally {
            Closer.closeQuietly(out);
            Closer.closeQuietly(in);
            Closer.closeQuietly(socket);
        }
        return baos.toByteArray();
    }

    private void configure(Socket socket) {
        if (socketConfig != null) {
            SocketConfig.configure(socketConfig, socket);
        }
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public void setSocketConfig(SocketConfig socketConfig) {
        this.socketConfig = socketConfig;
    }
}
