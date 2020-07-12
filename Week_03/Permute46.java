package com.xa.leetcode.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Permute46 {
    private List<List<Integer>> out = new ArrayList<>();
    public List<List<Integer>> permute(int[] nums) {
        generate(new Stack<>(), nums, nums.length);
        return out;
    }

    private void generate(Stack<Integer> result, int[] nums, int length) {
        //退出条件
        if(result.size() == length) {
            out.add(new ArrayList<>(result));
            return;
        }
        for(int i = 0; i < length; i++) {
            //跳过已经处理过的数据
            if(!result.contains(nums[i])) {
                //将当前值压栈
                result.push(nums[i]);
                //继续下一个数据
                generate(result, nums, length);
                //将满足上述退出条件的数据的最后一个数据弹出，重新压入最新的下一个数据
                result.pop();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new Permute46().permute(new int[] {1 ,2, 3}));
    }
}
