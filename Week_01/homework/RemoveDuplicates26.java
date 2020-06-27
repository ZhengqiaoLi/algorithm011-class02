package com.xa.algorithm.week01;

public class RemoveDuplicates26 {
    public int removeDuplicates(int[] nums) {
        int start = 0, index = 1;
        for(; index < nums.length; index++) {
            if(nums[start] != nums[index]) {
                nums[++start] = nums[index];
            }
        }
        return ++start;
    }
}
