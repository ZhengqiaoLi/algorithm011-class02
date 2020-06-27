package com.xa.algorithm.week01;

import java.util.HashMap;
import java.util.Map;

public class TwoSums1 {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numIndexRelation = new HashMap<>();
        for(int index = 0; index < nums.length; index++) {
            int first = target - nums[index];
            if(numIndexRelation.containsKey(first)) {
                return new int[] {numIndexRelation.get(first), index};
            }
            numIndexRelation.put(nums[index], index);
        }
        throw new IllegalArgumentException("don't find two elements");
    }
}
