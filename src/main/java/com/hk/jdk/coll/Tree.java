package com.hk.jdk.coll;

import java.util.*;

public class Tree {

    private static void treeSet() {
        TreeSet<Integer> set = new TreeSet<>();
        Random r = new Random();
        for(int i = 0; i < 100; i++) {
            set.add( r.nextInt(100));
        }

        int t = 50;
        System.out.println("first: " + set.first());
        System.out.println("lower: " + set.lower(t));
        for (Integer integer : set) {
            System.out.println(integer);
        }

        System.out.println("=========");
        Iterator<Integer> it = set.descendingIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    private static void treeMap() {
        NavigableMap<String, String> map = new TreeMap<>();
    }

    public static void main(String[] args) {
        treeSet();
    }
}
