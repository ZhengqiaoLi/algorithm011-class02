package com.xa.algorithm.week01;

public class PlusOne66 {
    public int[] plusOne(int[] digits) {
        int index = digits.length - 1;
        while(index >= 0) {
            if(++digits[index] % 10 != 0) {
                return digits;
            }
            digits[index--] = 0;
        }
        int[] res = new int[digits.length + 1];
        res[0] = 1;
        return res;
    }
}
