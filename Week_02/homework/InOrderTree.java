package com.xa.leetcode.leetcode;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InOrderTree {
    // 1、递归
    // 2、借用栈
    // public List<Integer> inorderTraversal(TreeNode root) {
    //     List<Integer> out = new ArrayList<>();
    //     helper(root, out);
    //     return out;
    // }

    // private void helper(TreeNode node, List<Integer> list) {
    //     if(node == null) {
    //         return;
    //     }
    //     helper(node.left, list);
    //     list.add(node.val);
    //     helper(node.right, list);
    // }
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                TreeNode node = stack.pop();
                out.add(node.val);
                cur = node.right;
            }
        }
        return out;
    }

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
