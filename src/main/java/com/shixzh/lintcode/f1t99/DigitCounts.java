package com.shixzh.lintcode.f1t99;

import java.util.Date;

/**
 * 3. Digit Counts
 * Description
 * Count the number of k's between 0 and n. k can be 0 - 9.
 * <p>
 * Have you met this question in a real interview?
 * Example
 * if n = 12, k = 1 in
 * <p>
 * [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
 * we have FIVE 1's (1, 10, 11, 12)
 */
public class DigitCounts {
    public static void main(String[] args) {
        Date start = new Date();
        int count = digitCounts(2, 2000000);
        Date end = new Date();
        System.out.println("count: " + count);
        System.out.print("time: " + (end.getTime() - start.getTime()) + " ms");

        //splitInteger(3241);
        //splitInteger1(3241);
        //splitInteger2(3241);
    }

    /**
     * 对象思路：把int转化成char[]，逐一判断是否相等
     * 由于Integer对象的自动封装与转化，效率极低，
     * 200万条数据，耗时213ms
     *
     * @param k
     * @param n
     * @return
     */
    public static int digitCounts(int k, int n) {
        // write your code here
        int count = 0;
        for (int i = 0; i <= n; i++) {
            Integer in = i;
            char[] inChar = in.toString().toCharArray();
            for (char c : inChar) {
                if (Integer.valueOf(String.valueOf(c)) == k) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 算法思路：采用多位数拆分，逐位判断是否相等
     * 不需要对象之间转化，效率较好，
     * 200万条数据，耗时24ms
     *
     * @param k
     * @param n
     * @return
     */
    public static int digitCounts1(int k, int n) {
        // write your code here
        int count = 0;
        for (int i = 0; i <= n; i++) {
//            System.out.println(i + "是否包含" + k);
            int j = i;
            for (; j >= 10; j /= 10) {
                if (j % 10 == k) {
                    count++;
//                    System.out.println("已有" + count + "个数包含" + k);
                }
            }
//            System.out.println(i + "的" + j + "是否等于" + k);
            if (j == k) {
                count++;
//                System.out.println("已有" + count + "个数包含k");
            }
        }
        return count;
    }

    /**
     * 对象思路：把int转化成String，逐一判断char是否相等
     * 由于String对象占用内存，效率一般，
     * 200万条数据，耗时92ms
     *
     * @param k
     * @param n
     * @return
     */
    public static int digitCounts2(int k, int n) {
        // write your code here
        int count = 0;
        char strK = new Integer(k).toString().charAt(0);
        for (int i = 0; i <= n; i++) {
            String str = "" + i;
            for (int v = str.length() - 1; v >= 0; v--) {
//                System.out.println(strk + " is equals " + str.charAt(v));
                if (strK == str.charAt(v)) {
                    count++;
//                    System.out.println("count = " + count);
                }
            }
        }
        return count;
    }

    /**
     * 多位数拆分问题
     *
     * @param n 多位数
     */
    public static void splitInteger(int n) {
        for (; n > 10; ) {
            int count = n % 10;
            System.out.println(count);
            n /= 10;
        }
        System.out.println(n);
    }

    /**
     * 多位数拆分，仅适用已知位数
     *
     * @param n
     */
    public static void splitInteger1(int n) {
        int numArray[] = new int[4];
        numArray[3] = n / 1000;
        numArray[2] = n / 100 % 10;
        numArray[1] = n / 10 % 10;
        numArray[0] = n % 10;
        for (int i = 0; i < numArray.length; i++) {
            System.out.println(numArray[i]);
        }
    }

    public static void splitInteger2(int n) {
        String str = "" + n;
        char s;
        for (int v = str.length() - 1; v >= 0; v--) {
            s = str.charAt(v);
            System.out.println(s);
        }
    }

}
