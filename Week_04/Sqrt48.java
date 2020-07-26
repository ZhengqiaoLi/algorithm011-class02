package com.xa.lzq.test;

public class Sqrt48 {
    // y = x*x,求y的平方根，单调递增，存在边界->二分查找
    public int mySqrt(int x) {
        int low = 0, high = x, ans = -1;
        while(low <= high) {
            int mid = low + (high - low)/2;
            if((long)mid * mid <= x) {
                ans = mid;
                low = low + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }
}
