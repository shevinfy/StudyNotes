package com.shevinfy.algorithm_subject.array_input_output;

import java.util.Scanner;
/**
 * 题目：
 * 全是英文，题目名字是Minesweeper
 * 就是扫雷的算法
 *
 * 题目理解
 * 写出扫雷算法，输出安全格（安全格要输出附件雷的数量）和雷格。注意输出格式，会有多个扫雷游戏一起输出，每一个扫雷游戏间空一行
 *
 * 解题思路：
 * 1.二维数组 双重for循环遍历存入 双重for循环遍历判断是否当前遍历的数组内容为雷，如果是，上（左上，上，右上）下（左下，下，右下）左右，
 * 这8个格加一并判断不为雷。最后输出即可
 * 2.第一种简单操作，换一种更有操作性与编程思想的算法
 *      ·准备好变量：X Y 有多少雷格  有多少安全格
 *      ·注意把错误数据判断出去，比如X=0 Y=0
 *      ·二维数组 双for循环存入
 *      ·判断这个扫雷游戏是雷格多还是安全格多
 *      ·雷格多用轰炸法（遇到雷格 传入当前的x-1 y-1 在方法中双重for遍历  if判断本身不是雷格和在二维数组中，就+1）针对传入雷格，给雷格周围安全格+1
 *      ·安全格多用扫雷法（遇到雷格 传入当前的x-1 y-1 在方法中双重for遍历  if判断本身是雷格和在二维数组中，传入格+1）针对传入安全格 只给传入安全格 +1
 *      ·这样就合理运用CPU运算力
 *      ·输出，注意输出格式
 */
public class 扫雷 {
    public static int X;
    public static int Y;
    public static char[][] minesweeper;
    public static void main(String[] args) {


        // 输入行列 存入变量XY
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        // 因为要同时输入多个扫雷游戏 一个while代表一个扫雷游戏
        while (scanner.hasNext()) {
            count++;
            X = scanner.nextInt();
            Y = scanner.nextInt();
            if (X == 0 && Y == 0) {
                break;
            }
            minesweeper = new char[X][Y];
            // 输入雷格和安全格
            int sum_safe = 0;
            int sum_mine = 0;


            for (int i = 0; i < X; i++) {
                int index = 0;
                // 这个scanner在第一个for循环要注意
                String string = scanner.next();
                char[] chars = string.toCharArray();
                for (int j = 0; j < Y; j++) {
                    //发现有多少雷格  有多少安全格
                    if (chars[index++] == '.') {
                        minesweeper[i][j] = '0';
                        sum_safe++;
                    } else {
                        sum_mine++;
                        minesweeper[i][j] = '*';
                    }

                }
            }

            for (int i = 0; i < minesweeper.length; i++) {
                for (int j = 0; j < minesweeper[0].length; j++) {
                    if (minesweeper[i][j] == '*') {
                        if (i - 1 >= 0) {// 上
                            if (minesweeper[i - 1][j] != '*') {
                                minesweeper[i - 1][j] += 1;
                            }

                            if (j - 1 >= 0) {// 左上
                                if (minesweeper[i - 1][j - 1] != '*') {
                                    minesweeper[i - 1][j - 1] += 1;
                                }

                            }

                            if (j + 1 < minesweeper[0].length) {// 右上
                                if (minesweeper[i - 1][j + 1] != '*') {
                                    minesweeper[i - 1][j + 1] += 1;
                                }

                            }
                        }
                        if (i + 1 < minesweeper.length) {// 下
                            if (minesweeper[i + 1][j] != '*') {
                                minesweeper[i + 1][j] += 1;
                            }

                            if (j - 1 >= 0) {// 左下
                                if (minesweeper[i + 1][j - 1] != '*') {
                                    minesweeper[i + 1][j - 1] += 1;
                                }

                            }

                            if (j + 1 < minesweeper[0].length) {// 右下
                                if (minesweeper[i + 1][j + 1] != '*') {
                                    minesweeper[i + 1][j + 1] += 1;
                                }
                            }
                        }

                        if (j - 1 >= 0) {// 左
                            if (minesweeper[i][j - 1] != '*') {
                                minesweeper[i][j - 1] += 1;
                            }

                        }
                        if (j + 1 < minesweeper[0].length) {// 右
                            if (minesweeper[i][j + 1] != '*') {
                                minesweeper[i][j + 1] += 1;
                            }

                        }

                    }
                }
            }

            //遍历输出
            System.out.println("Field #" + count + ":");
            for (int i = 0; i < minesweeper.length; i++) {
                for (int j = 0; j < minesweeper[0].length; j++) {
                    System.out.print(minesweeper[i][j]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

}

