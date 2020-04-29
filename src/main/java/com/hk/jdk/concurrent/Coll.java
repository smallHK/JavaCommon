package com.hk.jdk.concurrent;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Coll {

    private void copyOnWrite() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        for(int i = 0; i < 100; i++) {
            list.add(i);
        }
        System.out.println(list.size());
    }


    /**
     * 并发调表映射
     */
    private void run() {
        ConcurrentSkipListMap<Integer, Object> map = new ConcurrentSkipListMap<>();
        map.put(123, 123);
        map.put(34, 123);
        map.put(99, 2222);

        System.out.println(map.pollFirstEntry().getKey());;
        System.out.println(map.pollFirstEntry().getKey());
        System.out.println(map.size());
    }


    public static void main(String[] args) {
        var main = new Coll();
//        main.run();
        main.copyOnWrite();
    }
}
