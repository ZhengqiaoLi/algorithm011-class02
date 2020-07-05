package com.xa.leetcode.leetcode;

import java.util.*;

public class GroupAnagram {
    // public List<List<String>> groupAnagrams(String[] strs) {
    //     List<List<String>> result = new ArrayList<>();
    //     Map<String, List<String>> counter = new HashMap<>();
    //     for(String str : strs) {
    //         char[] ca = str.toCharArray();
    //         Arrays.sort(ca);
    //         String key = String.valueOf(ca);
    //         if(!counter.containsKey(key)) {
    //             counter.put(key, new ArrayList<>());
    //         }
    //         counter.get(key).add(str);
    //     }
    //     result.addAll(counter.values());
    //     return result;
    // }
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(String str : strs) {
            int[] arr = new int[26];
            for(int i = 0; i < str.length(); i++) {
                arr[str.charAt(i) - 'a']++;
            }
            String key = Arrays.toString(arr);
            if(!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }
}
