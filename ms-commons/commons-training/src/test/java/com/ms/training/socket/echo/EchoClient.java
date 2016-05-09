package com.ms.training.socket.echo;

import com.ms.training.socket.Closer;
import sun.io.*;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by mark.zhu on 2016/4/14.
 */
public class EchoClient {
    private String ip;
    private int port;

    public EchoClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args)  {

        System.out.println(Charset.defaultCharset());
        byte[] bytes;
        try {
            char c1[] = {'我'};
            CharToByteUTF8 charToByte = new CharToByteUTF8();
//            CharToByteGBK charToByte = new CharToByteGBK();
            byte b1[] = charToByte.convertAll(c1);
            for (int i = 0; i < b1.length; i++) {
                System.out.print(b1[i]+",");
            }
            System.out.println();

            String s = "我";
            for (byte b : s.getBytes("UTF-8")) {
                System.out.print(b+",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        EchoClient client = new EchoClient("127.0.0.1", 7076);
//        EchoClient client = new EchoClient("10.135.104.137", 10604);
        EchoClient client = new EchoClient("10.135.104.137", 8888);
        try {
            String message = "0000000617SCF11600010000210501010101TXID513543614E      0000000444201604181641540100000000000000000000                                                 <?xml version=\"1.0\" encoding=\"GBK\"?><scf><header><tranCode>3052</tranCode><cifMaster>1000001803</cifMaster><entSeqNo>TXID513543614E</entSeqNo><tranDate>20160418</tranDate><tranTime>164154</tranTime><retCode>000</retCode></header><body xsi:type=\"loanAmountInfoBody\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><creditno>XY14000452</creditno><custname>custname</custname><custno>custno</custno><financemode>01</financemode></body></scf>82A61D0E7608D70CD5BFD95FE0D1AA49";
//            String message = "0000000796SCF110000160002106010101011460963594445       0000000623201604181513140100000000000000000000                                                 <?xml version=\"1.0\" encoding=\"GBK\"?><scf><header><tranCode>3053</tranCode><cifMaster>1000001803</cifMaster><entSeqNo>1460963594445</entSeqNo><tranDate>20160418</tranDate><tranTime>151314</tranTime><retCode>000</retCode><entUserId>100001</entUserId><password>1q2w3e4r</password></header><body xsi:type=\"interestCalculationBody\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><ccycode>RMB</ccycode><creditno>xxxxxxx</creditno><custname>shandongjiuzhoushangyejituanyouxiangongsi</custname><custno>8700021658</custno><financedays>15</financedays><finclassno>FL</finclassno><orderamount>1111.00</orderamount></body></scf>1940033204DCFFA1E07F18E58CB0CD02";
            byte[] response = client.send(message.getBytes());
            System.out.println(new String(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String toBinary(byte b) {
        return Integer.toBinaryString(b & 0xFF);
    }
    private static byte toByte(String binary){
        byte result = 0;
        for (int i = binary.length() - 1, j = 0; i >= 0; i--, j++) {
            result += (Byte.parseByte(binary.charAt(i) + "") * Math.pow(2, j));
        }
        return result;
    }
    public byte[] send(byte[] data) throws IOException {
        Socket socket = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            socket = new Socket(ip, port);
            out = socket.getOutputStream();
            in = socket.getInputStream();
//            out.write(data);
            out.write("0000000796".getBytes());
            out.write("SCF11600010000210501010101TXID513543614E      0000000444201604181641540100000000000000000000                                                 ".getBytes());
            out.write("<?xml version=\\\"1.0\\\" encoding=\\\"GBK\\\"?><scf><header><tranCode>3053</tranCode><cifMaster>1000001803</cifMaster><entSeqNo>1460963594445</entSeqNo><tranDate>20160418</tranDate><tranTime>151314</tranTime><retCode>000</retCode><entUserId>100001</entUserId><password>1q2w3e4r</password></header><body xsi:type=\\\"interestCalculationBody\\\" xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\"><ccycode>RMB</ccycode><creditno>xxxxxxx</creditno><custname>shandongjiuzhoushangyejituanyouxiangongsi</custname><custno>8700021658</custno><financedays>15</financedays><finclassno>FL</finclassno><orderamount>1111.00</orderamount></body></scf>".getBytes());
            out.write("82A61D0E7608D70CD5BFD95FE0D1AA49".getBytes());
            out.flush();
            socket.shutdownOutput();

            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            long count = 0;
            int length;
            while (-1 != (length = in.read(buffer))) {
                baos.write(buffer, 0, length);
                count += length;
            }
            return baos.toByteArray();
        } finally {
            Closer.closeQuietly(out);
            Closer.closeQuietly(in);
            Closer.closeQuietly(socket);
        }
    }
}
