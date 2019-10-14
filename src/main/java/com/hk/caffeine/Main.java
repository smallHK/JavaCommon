package com.hk.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.net.CacheRequest;
import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.DAYS)
                .maximumSize(10_000)
                .build();

    }
}
