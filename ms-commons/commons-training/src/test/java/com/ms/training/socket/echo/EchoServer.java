package com.ms.training.socket.echo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mark.zhu on 2016/4/14.
 */
public class EchoServer {
    private final int port;
    private static boolean running = false;
    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 7076;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        new EchoServer(port).start();
    }

    private void start() {
        try {
            ServerSocket server = new ServerSocket(port);
            while (!Thread.interrupted()) {
                try {
                    final Socket socket = server.accept();
                    socket.setSoTimeout(10 * 1000);  // Ten seconds
                    handleSocket(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSocket(Socket socket) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            // read message length
            byte[] headBuffer = new byte[10];
            in.read(headBuffer);
            int messageLength = getMessageLength(new String(headBuffer));

            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(headBuffer);
            long count = 0;
            int len;
            while (-1 != (len = in.read(buffer))) {
                baos.write(buffer, 0, len);
                count += len;
                if (baos.toByteArray().length >= messageLength) {
                    break;
                }
            }
            byte[] b = handleMessage(baos.toByteArray());
            out.write(b);
            out.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
            closeQuietly(socket);
        } finally {
            closeQuietly(in);
            closeQuietly(out);
            closeQuietly(socket);
        }
    }

    private byte[] handleMessage(byte[] data) {
        try {
            System.out.println("received:" + new String(data));
            System.out.println("To UTF-8: " + new String(data, "UTF-8"));
            System.out.println("To GBK: " + new String(data, "GBK"));
            System.out.println("To GB18030: " + new String(data, "GB18030"));
            System.out.println("To ISO8859-1: " + new String(data, "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignore) {
            }
        }
    }

    private void closeQuietly(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception ignore) {
            }
        }
    }

    private int getMessageLength(String strLength) {
        int position = 0;
        for (int i = 0; i < strLength.length(); i++) {
            if (strLength.charAt(i) == '0') {
                continue;
            } else {
                position = i;
                break;
            }
        }
        return Integer.parseInt(strLength.substring(position));
    }
}
