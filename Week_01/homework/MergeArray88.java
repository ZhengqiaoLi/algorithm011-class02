package com.xa.algorithm.week01;

public class MergeArray88 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int indexOfNums1 = m - 1;
        int indexOfNums2 = n - 1;
        int end = m + n - 1;
        while(indexOfNums2 >= 0) {
            nums1[end--] = indexOfNums1 >= 0 && nums1[indexOfNums1] > nums2[indexOfNums2]
                    ? nums1[indexOfNums1--]
                    : nums2[indexOfNums2--];
        }
    }
}
