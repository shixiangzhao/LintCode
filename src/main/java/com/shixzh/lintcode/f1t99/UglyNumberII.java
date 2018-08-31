package com.shixzh.lintcode.f1t99;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Description
 * Ugly number is a number that only have factors 2, 3 and 5.
 * <p>
 * Design an algorithm to find the nth ugly number. The first 10 ugly numbers are 1, 2, 3, 4, 5, 6, 8, 9, 10, 12...
 * <p>
 * Note that 1 is typically treated as an ugly number.
 * <p>
 * Example
 * If n=9, return 10.
 * <p>
 * Challenge
 * O(n log n) or O(n) time.
 */
public class UglyNumberII {

    private static final List<Integer> LAST_NUM_2 = Arrays.asList(0, 2, 4, 5, 6, 8);

    public static void main(String[] args) {
        Date start = new Date();
        int nth = nthUglyNumber1(5990);
        Date end = new Date();
        System.out.println("time: " + (end.getTime() - start.getTime()) + " ms");
        System.out.println("result: " + nth);
    }

    /**
     * 2,3,5三个数的组合排列，按顺序输出问题(不太现实，但依旧有人实现啦)
     * num[i]与num[i-1]没有关系，但是与x * 2或者x * 3或者x * 5有关系，
     * x 分别代表上一个没乘2,3,5的数，这么说吧丑数数组中数要么是乘2，要么乘3， 要么乘5得到，
     * 也就是说x * 2,3,5的结果肯定在丑数数组中，只不过要排序，每次取其中最小的数，
     * 那么x要逐一乘以2,3,5，这下懂了吧，我们要做的就是保证，丑数数组中每一个数都乘以2,3,5，然后放到指定位置
     *
     * @param n: An integer
     * @return: the nth prime number as description.
     */
    public static int nthUglyNumber1(int n) {
        // write your code here
        int p2 = 0, p3 = 0, p5 = 0;
        int[] num = new int[n + 1];
        num[0] = 1;
        for (int i = 1; i <= n; i++) {
            num[i] = min(num[p2] * 2, num[p3] * 3,num[p5] * 5); // 排序，取三者最小值
            if (num[p2] * 2 == num[i]) { // num[p2]代表上一个没乘2的数，乘以2 后自加1
                ++p2;
            }
            if (num[p3] * 3 == num[i]) {// num[p3]代表上一个没乘3 的数，乘以3 后自加1
                ++p3;
            }
            if (num[p5] * 5 == num[i]) {// num[p5]代表上一个没乘5的数，乘以5 后自加1
                ++p5;
            }
        }
        return num[n - 1];
    }

    private static int min(int a, int b, int c) {
        return min(min(a, b), min(a, c));
    }

    private static int min(int a, int b) {
        return a > b ? b : a;
    }

    /**
     * @param n: An integer
     * @return: the nth prime number as description.
     */
    public static int nthUglyNumber(int n) {
        // write your code here
        int count = 0;
        for (int i = 1; ; i++) {
            // 判断丑数的算法
            if (isUglyNumber(i)) {
                count++;
                System.out.println(count + " 号丑数：" + i);
            }
            if (count == n) {
                return i;
            }
        }
    }

    /**
     * 循环判断每个因子，是否仅包含2,3,5
     *
     * @param n
     * @return
     */
    private static boolean isUglyNumber(int n) {
        boolean flag = false;// 默认false，即只能被1和其本身整除
        if (n == 1 || n == 2 || n == 3 || n == 5)
            return true;
        // 如果不能被2,3,5整除(尾数为0, 2, 4, 5, 6, 8，各位和为3的倍数)，则跳过
        int lastNum = n % 10;
        if (!LAST_NUM_2.contains(lastNum) && procSum(n) % 3 != 0) {
            return false;
        }
        for (int i = 2; i <= n / 2; i++) {// 从2开始到该数的一半，逐一判断其因子是否能被n整除
            if (n % i == 0) { // 如果 i 能被 n 整除，则 i 为 n 的因子
                flag = true; // 存在除1和其本身之外的因子
                if (i % 2 != 0 && i % 3 != 0 && i % 5 != 0) {// 如果包含非2,3,5的因子，那么就不是丑数
                    return false;
                }
            }
        }
        if (flag == false) {// 如果只能被1和本身整除，且不为1,2,3,5，那么就不是丑数
            return false;
        }
        return true;
    }

    private static int procSum(int n) {
        int sum = 0;
        while (n > 10) {
            sum = n % 10;
            n /= 10;
        }
        return sum + n;
    }
}