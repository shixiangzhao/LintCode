package com.shixzh.lintcode.f1t99;

import java.util.*;

/**
 * 20. Dices Sum
 * Description
 * Throw n dices, the sum of the dices' faces is S. Given n, find the all possible value of S along with its probability.
 * <p>
 * Example
 * Given n = 1, return [ [1, 0.17], [2, 0.17], [3, 0.17], [4, 0.17], [5, 0.17], [6, 0.17]].
 * <p>
 * Given n = 2,
 * 1, 1 = 2
 * 1, 2 = 3
 * 2, 1 = 3
 * 2, 2 = 4
 * 1, 3 = 4
 * 3, 1 = 4
 * 1, 4 = 5
 * 4, 1 = 5
 * 2, 3 = 5
 * 3, 2 = 5
 * 1, 5 = 6
 * 5, 1 = 6
 * 2, 4 = 6
 * 4, 2 = 6
 * 3, 3 = 6
 * 1, 6 = 7
 * 6, 1 = 7
 * 2, 5 = 7
 * 5, 2 = 7
 * 3, 4 = 7
 * 4, 3 = 7
 * 2, 6 = 8
 * 6, 2 = 8
 * 3, 5 = 8
 * 5, 3 = 8
 * 4, 4 = 8
 * 3, 6 = 9
 * 6, 3 = 9
 * 4, 5 = 9
 * 5, 4 = 9
 * 4, 6 = 10
 * 6, 4 = 10
 * 5, 5 = 10
 * 5, 6 = 11
 * 6, 5 = 11
 * 6, 6 = 12
 * ...
 * Given n = 3,
 * 1, 1, 1 = 3
 * 2, 1, 1 = 4
 * 1, 2, 1 = 4
 * 1, 1, 2 = 4
 * ...
 * ...0.......1.......2.......3.......4......5........6........7........8........9........10.......11.......12.......13.......14.......15......16......17.....18...
 * [1, 0], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1], [1, 1]
 * [2, 0], [2, 0], [2, 1], [2, 2], [2, 3], [2, 4], [2, 5], [2, 6], [2, 5], [2, 4], [2, 3], [2, 2], [2, 1]
 * [3, 0], [3, 0], [3, 0], [3, 1], [3, 3], [3, 6], [3, 10], [3, 15], [3, 21], [3, 25], [3, 27], [3, 27], [3, 25], [3, 21], [3, 15], [3, 10], [3, 6], [3, 3], [3, 1]
 * ...
 */
public class DicesSum {

    public static void main(String[] args) {
        Date start = new Date();
        List<Map.Entry<Integer, Double>> list = dicesSum2(5);
        Date end = new Date();
        System.out.println("time: " + (end.getTime() - start.getTime()) + " ms");
        System.out.print("[");
        for (Map.Entry<Integer, Double> map : list) {
            System.out.print("[" + map.getKey() + "," + map.getValue() + "]");
        }
        System.out.print("]");

    }

    /**
     * 递归算法，
     *
     * @param n
     * @return
     */
    public static List<Map.Entry<Integer, Double>> dicesSum(int n) {
        // Write your code here
        // Ps. new AbstractMap.SimpleEntry<Integer, Double>(sum, pro)
        // to create the pair
        final double PRECISION = 100.0;
        final double singleProbability = 1.0 / Math.pow(6, n); //n个骰子每种可能性的概率
        Map<Integer, Double> map = new HashMap<>();
        for (int s = n; s <= n * 6; s++) { // 遍历每一种可能 sum
            double resCount = getNSumCount(n, s);
            double probability = Math.round((resCount * singleProbability) * PRECISION) / PRECISION;
            map.put(s, probability);
        }
        List<Map.Entry<Integer, Double>> result = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : map.entrySet()) { // 逐一存入list
            AbstractMap.SimpleEntry<Integer, Double> ey = new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue());
            result.add(ey);
        }
        return result;
    }

    /**
     * 动态规划：已知有限项，递推出第n项的值
     * 一个公式：i 为骰子的个数，j 为可能的sum值，dp[i][j] 为当骰子为 i 时，和为 j 的场景数量
     * dp[i][j] = dp[i-1][j-1] + dp[i-1][j-2] + dp[i-1][j-3] + dp[i-1][j-4] + dp[i-1][j-5] + dp[i-1][j-6]
     *
     * @param n
     * @return
     */
    public static List<Map.Entry<Integer, Double>> dicesSum2(int n) {
        // Write your code here
        // Ps. new AbstractMap.SimpleEntry<Integer, Double>(sum, pro)
        // to create the pair
        final double PRECISION = 100.0;
        long[][] dp = new long[n + 1][6 * n + 1]; // 二维数组：x轴为骰子个数n，y轴为每个sum（下标表示sum数字，dp[i][j]值表示次数）
        dp[1][1] = 1;
        dp[1][2] = 1;
        dp[1][3] = 1;
        dp[1][4] = 1;
        dp[1][5] = 1;
        dp[1][6] = 1;
        for (int i = 2; i <= n; i++) {// 骰子的个数
            for (int j = i; j <= i * 6; j++) { // 遍历每一种可能 sum
                long x1 = 0, x2 = 0, x3 = 0, x4 = 0, x5 = 0, x6 = 0;
                if (j - 1 > 0) {
                    x1 = dp[i - 1][j - 1];
                }
                if (j - 2 > 0) {
                    x2 = dp[i - 1][j - 2];
                }
                if (j - 3 > 0) {
                    x3 = dp[i - 1][j - 3];
                }
                if (j - 4 > 0) {
                    x4 = dp[i - 1][j - 4];
                }
                if (j - 5 > 0) {
                    x5 = dp[i - 1][j - 5];
                }
                if (j - 6 > 0) {
                    x6 = dp[i - 1][j - 6];
                }
                dp[i][j] = x1 + x2 + x3 + x4 + x5 + x6;
            }
        }

        List<Map.Entry<Integer, Double>> result = new ArrayList<>();
        for (int i = n; i <= 6 * n; i++) {
            AbstractMap.SimpleEntry<Integer, Double> entry = new AbstractMap.SimpleEntry<>(i, Math.round(dp[n][i] / Math.pow(6, n) * PRECISION) / PRECISION);
            result.add(entry);
        }
        return result;
    }

    /**
     * 递归算法
     *
     * @param n
     * @param sum
     * @return
     */
    private static int getNSumCount(int n, int sum) {
        if (n < 1 || sum < n || sum > 6 * n) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int resCount;
        resCount = getNSumCount(n - 1, sum - 1) + getNSumCount(n - 1, sum - 2) + getNSumCount(n - 1, sum - 3) +
                getNSumCount(n - 1, sum - 4) + getNSumCount(n - 1, sum - 5) + getNSumCount(n - 1, sum - 6);
        return resCount;
    }
}
