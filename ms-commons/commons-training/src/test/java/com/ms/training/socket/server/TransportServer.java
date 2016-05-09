package com.ms.training.socket.server;

import com.ms.training.socket.SocketConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by mark.zhu on 2016/3/11.
 */
public class TransportServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransportServer.class);
    private final int port;
    private SocketConfig socketConfig;
    private AtomicBoolean started = new AtomicBoolean();
    private boolean stopped = false;

    public TransportServer(int port) {
        this.port = port;
    }

    public void start() {
        if (started.compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
            ServerSocket server;
            try {
                server = new ServerSocket(port);
                //TODO setting max concurrent connections threshold
                while (!stopped && !Thread.interrupted()) {
                    final Socket socket = server.accept();
                    configure(socket);
                    //TODO using a thread pool
                    Thread t = new Thread(new SocketHandler(socket));
                    t.start();
                }
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }

    public void stop() {
        if (started.compareAndSet(Boolean.TRUE, Boolean.FALSE)) {
            stopped = true;
        }
    }
    private void configure(Socket socket) {
        if (socketConfig != null) {
            SocketConfig.configure(socketConfig, socket);
        }
    }
    public void setSocketConfig(SocketConfig socketConfig) {
        this.socketConfig = socketConfig;
    }

    public static void main(String[] args) {
        TransportServer socketServer = new TransportServer(8998);
        try {
            socketServer.start();
            LOGGER.info("STARTED!");
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            LOGGER.error("",e);
        } catch (Exception e) {
            LOGGER.error("",e);
        }finally {
            socketServer.stop();
            LOGGER.info("STOPPED!");
        }
    }
}
