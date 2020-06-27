package com.xa.algorithm.week01;

public class Rotate189 {
    public void rotate(int[] nums, int k) {
        int count = 0;
        for(int start = 0; count < nums.length; start++) {
            int curIndex = start;
            int curElement = nums[curIndex];
            do {
                int nextIndex = (curIndex + k) % nums.length;
                int temp = nums[nextIndex];
                nums[nextIndex] = curElement;

                curIndex = nextIndex;
                curElement = temp;
                count++;
            } while(curIndex != start);
        }
    }
}
