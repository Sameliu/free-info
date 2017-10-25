package com.sijin.justTest;

import java.io.FileInputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by sijinzhang on 16/12/26.
 */
public class ProgramTest {
    public static void main(String args[]) throws Exception {
        FileInputStream fin = new FileInputStream("/Users/sijinzhang/git/datapp/free-info/src/main/resources/b.txt");
        FileChannel fc = fin.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(20);
        output("初始化", buffer);

        fc.read(buffer);
        output("调用read()", buffer);

        buffer.flip();
        output("调用flip()", buffer);

        while (buffer.remaining() > 0) {
            byte b = buffer.get();
            System.out.print(((char)b));
            System.out.println();
        }
        output("调用get()", buffer);

        buffer.clear();
        output("调用clear()", buffer);

        fin.close();
    }

    public static void output(String step, Buffer buffer) {
        System.out.println(step + " : ");
        System.out.print("capacity: " + buffer.capacity() + ", ");
        System.out.print("position: " + buffer.position() + ", ");
        System.out.println("limit: " + buffer.limit());
        System.out.println();
    }
}
