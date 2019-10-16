package com.hk.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Main {


    //缓存设置
    public static void main(String[] args) {
        Cache<Integer, Integer> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(5)
                .build(k-> null);


        List<Integer> keys = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            cache.put(i, i);
            keys.add(i);
        }

        try {
            Thread.sleep(5 * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<Integer, Integer> values = cache.getAllPresent(keys);
        for(Integer key: keys) {
            System.out.println("key: " + key);
            System.out.println("value: " + values.get(key));
        }

    }
}
