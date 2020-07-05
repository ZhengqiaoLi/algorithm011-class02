package com.xa.leetcode.leetcode;

public class ValidAnagram {
    //1、暴力解法，两个Map统计字母出现的次数
    //2、排序后比较
    //3、用数组代替Map结构，因为String字符串中都是字母
    // public boolean isAnagram(String s, String t) {
    //     if(s.length() != t.length()) return false;
    //     char[] temp1 = s.toCharArray();
    //     char[] temp2 = t.toCharArray();
    //     Arrays.sort(temp1);
    //     Arrays.sort(temp2);
    //     return Arrays.equals(temp1, temp2);
    // }

    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()) return false;
        int[] alphabet = new int[26];
        for(int i = 0; i < s.length(); i++) {
            alphabet[s.charAt(i) - 'a']++;
            alphabet[t.charAt(i) - 'a']--;
        }
        for(int counter : alphabet) {
            if(counter != 0) return false;
        }
        return true;
    }
}
