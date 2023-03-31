package com.shevinfy.algorithm_subject.recursion;

import java.util.Scanner;

/**
 * 题目：有一头母牛，它每年年初生一头小母牛。每头小母牛从第四个年头开始，每年年初也生一头小母牛。请编程实现在第n年的时候，共有多少头母牛？
 * 算法思路：
 * 审清题干，养成习惯。
 * 有一头母牛，它每年年初生一头小牛（就是从第二年开始生），每头小牛从第四年开始，每年年初生一头小牛（就是从第五牛开始生）
 * 与斐波那契数列很相似，递增规律发生改变
 * 一个输入，一个输出，输入0结束。
 * 分析一下题目的规律
 * 简单列一个表格：
 * 年份       1       2       3       4       5       6                           7
 * 母牛数量     1       2       3       4       6       9（前面有6头加上会生的3头）       13（前面有9头加上会生的4头）
 * 得出结论： 当年的母牛数量=前一年的母牛数量+前三年的母牛数量
 * 得出算式，f（n） = f（n-1）+f（n-3）
 *
 * 递归：既然是递归，那就离不开这三点：1.找重复、2.找变化、3.找边界
 * 找重复：就是递归时重复操作的部分，详见上面的公式；
 * 找变化：就是递归时参数的变化，上面的公式一起囊括了；
 * 找边界：就是递归脱出的条件，这里很明显是n<=3，因为此时上面的公式不适用了。
 */
public class 母牛的故事 {
    public static int getSum(int n){
        // 一头牛前四年不生产
        if(n <= 4){
            return n;
        }

        //总数是前一年的母牛数量加上有多少可以生产的母牛（前3年之前的母牛数量）
        return getSum(n-1)+getSum(n-3);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            int n = scanner.nextInt();
            // 输入0程序结束
            if(n<=0){
                return;
            }
            int cowSum = getSum(n);
            System.out.println(cowSum);
        }
    }
}


