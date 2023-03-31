package com.shevinfy.algorithm_subject.array_input_output;

import java.util.Scanner;

public class 扫雷2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 1;
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            if (n == 0 && m == 0) {
                break;
            }
            char[][] nums = new char[n][m];
            for (int i = 0; i < nums.length; i++) {
                char[] input = scanner.next().toCharArray();
                for (int j = 0; j < input.length; j++) {
                    if (input[j] != '*') {
                        nums[i][j] = '0';
                    } else {
                        nums[i][j] = '*';
                    }
                }
            }

            for (int i = 0; i < nums.length; i++) {
                for (int j = 0; j < nums[0].length; j++) {
                    if (nums[i][j] == '*') {
                        if (i - 1 >= 0) {// 上
                            if (nums[i - 1][j] != '*') {
                                nums[i - 1][j] += 1;
                            }

                            if (j - 1 >= 0) {// 左上
                                if (nums[i - 1][j - 1] != '*') {
                                    nums[i - 1][j - 1] += 1;
                                }

                            }

                            if (j + 1 < nums[0].length) {// 右上
                                if (nums[i - 1][j + 1] != '*') {
                                    nums[i - 1][j + 1] += 1;
                                }

                            }
                        }
                        if (i + 1 < nums.length) {// 下
                            if (nums[i + 1][j] != '*') {
                                nums[i + 1][j] += 1;
                            }

                            if (j - 1 >= 0) {// 左下
                                if (nums[i + 1][j - 1] != '*') {
                                    nums[i + 1][j - 1] += 1;
                                }

                            }

                            if (j + 1 < nums[0].length) {// 右下
                                if (nums[i + 1][j + 1] != '*') {
                                    nums[i + 1][j + 1] += 1;
                                }
                            }
                        }

                        if (j - 1 >= 0) {// 左
                            if (nums[i][j - 1] != '*') {
                                nums[i][j - 1] += 1;
                            }

                        }
                        if (j + 1 < nums[0].length) {// 右
                            if (nums[i][j + 1] != '*') {
                                nums[i][j + 1] += 1;
                            }

                        }

                    }
                }
            }

            //遍历输出
            System.out.println("Field #"+count+":");
            for (int i = 0; i < nums.length; i++) {
                for (int j = 0; j < nums[0].length; j++) {
                    System.out.print(nums[i][j]);
                }
                System.out.println();
            }
            System.out.println();
            count++;
        }

    }

}