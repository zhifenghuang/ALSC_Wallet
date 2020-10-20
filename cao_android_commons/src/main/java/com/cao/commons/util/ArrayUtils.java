package com.cao.commons.util;


import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class ArrayUtils {

    private static Random rand = new Random();

    public static <T> void swap(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * 随机排序
     * @param arr
     * @param <T>
     */
    public static <T> void shuffle(List<T> arr) {
        int length = arr.size();
        for (int i = length; i > 0; i--) {
            int randInd = rand.nextInt(i);
            swap(arr, randInd, i - 1);
        }
    }


    public static <T> void swap(List<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i,a.get(j));
        a.set(j,temp);
    }
}
