package com.liugeng.cloud.study.thread;

import com.liugeng.cloud.common.util.StringUtil;

import java.io.*;

public class PipedTest {

    public static void main1(String[] args) throws Exception {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        //输出流与输入流连接
        out.connect(in);
        Thread t = new Thread(new ReaderPrint(in));
        t.start();
        int receive = 0;
        try {
            while ((receive = System.in.read()) != -1){
                out.write(receive);
            }
        }finally {
            out.close();
        }
    }

    public static void main(String[] args) throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream();
        //输出流与输入流连接
        outputStream.connect(inputStream);
        Thread t = new Thread(new InputReaderPrint(inputStream));
        t.start();
        int receive = 0;
        try {
            while ((receive = System.in.read()) != -1){
                outputStream.write(receive);
            }
        }finally {
            outputStream.close();
        }
    }

    static class ReaderPrint implements Runnable{
        private PipedReader in;
        public ReaderPrint(PipedReader in){
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1){
                    System.out.print((char)receive);
                    if(10 == receive){
                        System.out.println("回答完毕");
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    static class InputReaderPrint implements Runnable{
        private PipedInputStream inputStream;
        public InputReaderPrint(PipedInputStream inputStream){
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = inputStream.read()) != -1){
                    System.out.print((char)receive);
                    if(10 == receive){
                        System.out.println("回答完毕");
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
