package com.matrix.algorithms.Searches;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.lang.String.format;

/**
 *
 *
 *
 * Binary search is one of the most popular algorithms
 * The algorithm finds the position of a target value within a sorted array
 *
 * Worst-case performance	O(log n)
 * Best-case performance	O(1)
 * Average performance	O(log n)
 * Worst-case space complexity	O(1)
 *
 *
 * @author Varun Upadhyay (https://github.com/varunu28)
 * @author Podshivalov Nikita (https://github.com/nikitap492)
 *
 * @see SearchAlgorithm
 * @see IterativeBinarySearch
 *
 */

class BinarySearch implements SearchAlgorithm {

    /**
     *
     * @param array is an array where the element should be found
     * @param key is an element which should be found
     * @param <T> is any comparable type
     * @return index of the element
     */
    @Override
    public  <T extends Comparable<T>> int find(T[] array, T key) {
        return search(array, key, 0, array.length);
    }

    /**
     * This method implements the Generic Binary Search
     *
     * @param array The array to make the binary search
     * @param key The number you are looking for
     * @param left The lower bound
     * @param right The  upper bound
     * @return the location of the key
     **/
    private <T extends Comparable<T>> int search(T array[], T key, int left, int right){
        if (right < left) return -1; // this means that the key not found

        // find median
        int median = (left + right) >>> 1;
        int comp = key.compareTo(array[median]);

        if (comp == 0) {
            return median;
        } else if (comp < 0) {
            return search(array, key, left, median - 1);
        } else {
            return search(array, key, median + 1, right);
        }
    }

    public static void main(String[] args) {
    	// 生成100个有序的随机数作为测试数据源，随机数的最大值为：100000
    	Random random = ThreadLocalRandom.current();
        int size = 100;
        int maxElement = 100000;
        Integer[] arrs = IntStream.generate(() -> random.nextInt(maxElement)).limit(size).sorted().boxed().toArray(Integer[]::new);

        int targetKey = arrs[random.nextInt(size - 1)];  // 从100个测试数据中心随机选择一个作为要查找的数据

        BinarySearch search = new BinarySearch();
        int atIndex = search.find(arrs, targetKey);
        System.out.println(format("查找目标key：%d；发现：%d； 数组下标：%d. 当前测试数据源数组长度：%d", targetKey, arrs[atIndex], atIndex, size ));

        int systemCheckIndex = Arrays.binarySearch(arrs, targetKey);
        System.out.println(format("JDK自身提供方法进行验证，结果为：%d。对比自定义方法结果：%b", systemCheckIndex, systemCheckIndex == atIndex));
    }
}










