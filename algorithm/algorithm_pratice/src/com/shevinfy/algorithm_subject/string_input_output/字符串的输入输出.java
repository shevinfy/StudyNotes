package com.shevinfy.algorithm_subject.string_input_output;

import java.util.Scanner;
/**
 * 字符串的输入输出处理。
 * 输入格式
 * 第一行是一个正整数N，最大为100。之后是多行字符串（行数大于N）， 每一行字符串可能含有空格，字符数不超过1000。
 * 输出格式
 * 先将输入中的前N行字符串（可能含有空格）原样输出，再将余下的字符串（不含有空格）以空格或回车分割依次按行输出。每行输出之间输出一个空行。
 * 样例输入
 * 2
 * www.dotcpp.com DOTCPP
 * A C M
 * D O T CPP
 * 样例输出
 * www.dotcpp.com DOTCPP
 *
 * A C M
 *
 * D
 *
 * O
 *
 * T
 *
 * CPP
 * 了解题意：
 * 将字符串做输入输出处理  一开始输入一个数字 这个数字代表后面字符串不用处理，这个数字以后如何还有行字符串，
 * 就这些字符串以空格或者回车分割依次输出，输出结果隔一个空行
 *
 * 算法思路：
 * 先将数字输入，然后for循环单独处理，这个数字对应的字符串行
 * 然后检查是否有文字输入，用java String类中的split分割方法以空格或者回车去分割字符
 */
public class 字符串的输入输出 {
    public static void main(String[] args){
        stringInputOutput();
    }
    public static void stringInputOutput(){
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        if(a==1){
            return;
        }
        for (int i = 0; i < a+1; i++) {
            if(i == 0){
                continue;
            }
            String s = scanner.nextLine();
            System.out.println(s+"\n");
        }
        while(scanner.hasNext()){
            String s = scanner.next();
            String s1[] = s.split("\n");  // 分割转行的String

            for (int i = 0; i < s1.length; i++) {
                String s2[] = s1[i].split(" ");
                for (int j = 0; j < s2.length; j++) {
                    System.out.println(s2[j]+"\n");
                }
            }
        }

    }
}
