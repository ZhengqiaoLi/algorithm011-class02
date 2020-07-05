package com.xa.leetcode.leetcode;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    // 1、暴力求解
    // 2、map统计
    // public int[] twoSum(int[] nums, int target) {
    //     for(int i = 0; i < nums.length; i++) {
    //         for(int j = i + 1; j< nums.length; j++) {
    //             if(nums[i] + nums[j] == target) {
    //                 return new int[] {i, j};
    //             }
    //         }
    //     }
    //     return new int[0];
    // }
    // public int[] twoSum(int[] nums, int target) {
    //     Map<Integer, Integer> num2Index = new HashMap<>();
    //     for(int i = 0; i < nums.length; i++) {
    //         int first = target - nums[i];
    //         if(num2Index.containsKey(first)) {
    //             return new int[] {num2Index.get(first), i};
    //         }
    //         num2Index.put(nums[i], i);
    //     }
    //     return new int[0];
    // }
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> num2Index = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            num2Index.put(nums[i], i);
        }
        for(int i = 0; i < nums.length - 1; i++) {
            int second = target - nums[i];
            if(num2Index.containsKey(second)) {
                int j = num2Index.get(second);
                if(i != j) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[0];
    }
}
