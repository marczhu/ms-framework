package com.ms.gateway.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.AccessControlException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * tomcat、activemq、至少3个启动和停止
 * 资源释放
 * Created by mark.zhu on 2016/4/8.
 *
 */
public class Bootstrap {
    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);
    private static AtomicBoolean started = new AtomicBoolean(Boolean.FALSE);
    private static Bootstrap daemon = null;

    private void start() throws Exception {
        if (started.compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
            //do start
        }
    }

    private void stop() throws Exception {
        if (!started.get()) {
            LOGGER.info("NOT RUNNING!");
        }else{
            //do stop
        }
    }

    public static void main(String[] args) {

        try {
            if (daemon == null) {
                daemon = new Bootstrap();
            }

            daemon.await(false);

            String command = "start";
            if (args.length > 0) {
                command = args[args.length - 1];
            }

            if (command.equals("start")) {
                daemon.start();
            } else if (command.equals("stop")) {
                daemon.stop();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public void await(boolean stopAwait) {

        ServerSocket awaitSocket;
        // Set up a server socket to wait on
        try {
            awaitSocket = new ServerSocket(1000, 1,
                    InetAddress.getByName("localhost"));
        } catch (IOException e) {
            LOGGER.error("StandardServer.await: create[" + "localhost"
                    + ":" + 1000
                    + "]: ", e);
            return;
        }


        Thread awaitThread = Thread.currentThread();

        // Loop waiting for a connection and a valid command
        while (!stopAwait) {
            ServerSocket serverSocket = awaitSocket;
            if (serverSocket == null) {
                break;
            }

            // Wait for the next connection
            Socket socket = null;
            StringBuilder command = new StringBuilder();
            try {
                InputStream stream = null;
                long acceptStartTime = System.currentTimeMillis();
                try {
                    socket = serverSocket.accept();
                    socket.setSoTimeout(10 * 1000);  // Ten seconds
                    stream = socket.getInputStream();
                } catch (SocketTimeoutException ste) {
                } catch (AccessControlException ace) {
                } catch (IOException e) {
                    break;
                }

                // Read a set of characters from the socket
                int expected = 1024; // Cut off to avoid DoS attack
                while (expected < "SHUTDOWN".length()) {
                    Random random = new Random();
                    expected += (random.nextInt() % 1024);
                }
                while (expected > 0) {
                    int ch = -1;
                    try {
                        ch = stream.read();
                    } catch (IOException e) {
                        LOGGER.warn("StandardServer.await: read: ", e);
                        ch = -1;
                    }
                    if (ch < 32)  // Control character or EOF terminates loop
                        break;
                    command.append((char) ch);
                    expected--;
                }
            } finally {
                // Close the socket now that we are done with it
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }

}
