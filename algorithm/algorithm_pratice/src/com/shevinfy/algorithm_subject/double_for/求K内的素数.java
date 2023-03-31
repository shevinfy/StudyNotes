package com.shevinfy.algorithm_subject.double_for;

import java.util.Scanner;
/**
 * 用筛法求之N内的素数。
 *
 * 解题思路：
 * 首先要了解素数是什么意思：素数是指整数中只能被1和自己本身整除的数
 * 0和1不算素数
 * for循环这个i(i等于这个k)，再第二个for循环一个j（j要不等于i，并且比i小），再第二个for循环中用i%j求余，如果等于0，说明有不是本身且可以整除的
 */
public class 求K内的素数 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            int k = scanner.nextInt();
            if(k < 2){
                return;
            }
            getPrimeNumber(k);
        }
    }

    public static void getPrimeNumber(int k){
        i:for (int i = 2; i < k; i++) {
            for (int j = 2; j < i; j++) {
                if(i%j == 0){
                    continue i;  // 跳过当前i数这个循环（因为已经证明这个数不是素数了）
                }
            }
            System.out.println(i); // 输出语句要写在第一层for的下面，因为如果放在else下面程序的i%j
            // j就只等于2的时候求余了一下，还没有循环到别的数字就被你当作可以的数取出来了
        }
    }
}
