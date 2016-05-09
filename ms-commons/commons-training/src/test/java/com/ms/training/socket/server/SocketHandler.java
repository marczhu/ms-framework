package com.ms.training.socket.server;

import com.ms.training.socket.Closer;
import com.ms.training.socket.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * Created by mark.zhu on 2016/3/11.
 */
public class SocketHandler implements Runnable {
    private final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);
    private Socket socket;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            byte[] buffer = new byte[4096];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int length;
            while (-1 != (length = in.read(buffer))) {
                baos.write(buffer, 0, length);
            }
            MessageHandler messageHandler = null;
            byte[] b = "result".getBytes();
            out.write(b);
            out.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            LOGGER.error("", e);
            Closer.closeQuietly(socket);
        } finally {
            Closer.closeQuietly(in);
            Closer.closeQuietly(out);
            Closer.closeQuietly(socket);
        }
    }

}
