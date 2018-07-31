package com.lorin.algorithm.leetcode;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lorin on 2018/4/30.
 */
public class SingleNumber {

    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = Arrays.stream(nums1)
                .mapToObj(i -> i)
                .collect(Collectors.toSet());
        int[] res = Arrays.stream(nums2)
                .filter(i -> set.contains(i))
                .distinct()
                .toArray();
        return res;
    }


}
