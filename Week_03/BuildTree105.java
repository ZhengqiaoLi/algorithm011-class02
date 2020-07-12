package com.xa.leetcode.leetcode;

import java.util.HashMap;
import java.util.Map;

public class BuildTree105 {
    //分治的思想，本题主要还是在下标，难受
    private Map<Integer, Integer> map = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for(int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return myBuildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode myBuildTree(int[] preorder, int p_start, int p_end, int[] inorder, int i_start, int i_end) {
        //p_start = p_start + 1, p_end = p_start + left_number
        //当left_number = 0时，就会跳出迭代，即p_start > p_end
        if(p_start > p_end) {
            return null;
        }
        //process current
        TreeNode root = new TreeNode(preorder[p_start]);
        int i_root_index = map.get(preorder[p_start]);
        int left_number = i_root_index - i_start;
        //droll down(subprocess)
        TreeNode leftNode = myBuildTree(preorder, p_start + 1, p_start + left_number, inorder, i_start, i_start + left_number -1);
        TreeNode rightNode = myBuildTree(preorder, p_start + left_number + 1, p_end, inorder, i_start + left_number + 1, i_end);
        //merge
        root.left = leftNode;
        root.right = rightNode;
        return root;
    }


    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) { val = x; }
    }
}
