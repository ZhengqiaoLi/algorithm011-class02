package com.xa.leetcode.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Combine77 {
    private List<List<Integer>> result = new ArrayList<>();
    public List<List<Integer>> combine(int n, int k) {
        // 特判
        if (n <= 0 || k <= 0 || n < k) {
            return result;
        }
        // 从 1 开始是题目的设定
        generate(n, k, 1, new LinkedList<>());
        return result;
    }

    private void generate(int n, int k, int begin, LinkedList<Integer> pre) {
        //退出条件
        if(pre.size() == k) {
            result.add(new ArrayList<>(pre));
            return;
        }
        //处理当前层数据
        for(int i = begin; i <= n; i++) {
            pre.offerLast(i);
            //递归进下一层
            generate(n, k, i + 1, pre);
            //弹出一个数据，开始加入下一个数据
            pre.pollLast();
        }
    }

    public static void main(String[] args) {
        System.out.println(new Combine77().combine(4, 2));
    }
}
