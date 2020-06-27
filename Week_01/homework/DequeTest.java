package com.xa.algorithm.week01;

import java.util.Deque;
import java.util.LinkedList;

public class DequeTest {
    public static void main(String[] args) {
        Deque<String> deque = new LinkedList<>();
        deque.offerLast("a");
        deque.offerLast("b");
        deque.offerLast("c");
        System.out.println(deque);

        String str = deque.peekLast();
        System.out.println(str);
        System.out.println(deque);

        while(!deque.isEmpty()) {
            System.out.println(deque.pollLast());
        }
        System.out.println(deque);
    }
}
