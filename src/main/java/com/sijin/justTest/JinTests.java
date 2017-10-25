package com.sijin.justTest;

/**
 * Created by sijinzhang on 16/12/29.
 */
public class JinTests {

    static  Integer a = 1;
    public static void main(String[] args) {
        JinTests j = new JinTests();
        System.out.println(j.a);
        JinTests.a =2;
        System.out.println(j.a);
        JinTests jf = new JinTests();
        System.out.println(jf.a);

    }
}
