package com.shevinfy.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序
 *
 * 算法思想
 * 自己：
 * 将一个数组从下标从中间分割成left数组和right数组
 * 将这两个数组用递归的方式以形参传入m函数（比对大小并移动位置）
 * 将这两个数组递归传入就可以这个数组最底层，就只有数组中两个相邻的内容
 * 比较完数组分出来最底层只有两个数组后，因为之前的递归如何就是倒数第二层，相邻4个数组，继续排序
 *
 * 官方：
 * 归并排序（Merge sort）是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。
 *
 * 作为一种典型的分而治之思想的算法应用，归并排序的实现由两种方法：
 *
 * 自上而下的递归（所有递归的方法都可以用迭代重写，所以就有了第 2 种方法）；
 * 自下而上的迭代；
 *
 * 时间复杂度与空间复杂度
 * 始终都是 O(nlogn) 的时间复杂度。代价是需要额外的内存空间。
 *
 * 算法动态演示
 * https://www.runoob.com/wp-content/uploads/2019/03/mergeSort.gif
 * 变量解释
 */
public class MergeSort {
    public static int[] sort(int[] sourceArr){
        int[] arr = Arrays.copyOf(sourceArr,sourceArr.length);
        // 排除乱数
        if(arr.length < 2){
            return arr;
        }

        //设置中间数，用来分割
        int mid = (int)Math.floor(arr.length/2); // Math.floor()方法输出整数如 Math.floor(46.95) 输入：46
        int[] left = Arrays.copyOfRange(arr,0,mid);
        int[] right = Arrays.copyOfRange(arr,mid,arr.length);

        return merge(sort(left),sort(right));
    }
    public static int[] merge(int[] left,int[] right){
        // 结果数组，排序后的数组
        int[] result = new int[left.length+right.length];
        int i = 0; // 结果数组的下标

        while(left.length > 0 && right.length > 0){
            if(left[0] < right[0]){
                result[i++] = left[0];
                left = Arrays.copyOfRange(left,1,left.length);
            }else if(left[0] == right[0]){
                result[i++] = left[0];
                left = Arrays.copyOfRange(left,1,left.length);
            }else {
                result[i++] = right[0];
                right = Arrays.copyOfRange(right, 1, right.length);
            }
        }
        // 最后一次对比有一边没有进入结果数组，加上。
        while(left.length > 0){
            result[i++] = left[0];
            left = Arrays.copyOfRange(left,1,left.length);
        }
        while(right.length > 0){
            result[i++] = right[0];
            right = Arrays.copyOfRange(right, 1, right.length);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {5,9,26,78,25,36,45,45,99,87,52,01,33};
        int[] sortArr = sort(arr);
        for (int i = 0; i < sortArr.length; i++) {
            System.out.println(sortArr[i]);
        }
    }
}
