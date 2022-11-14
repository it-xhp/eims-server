package com.gdupt.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuhuaping
 * @date 2022/9/13
 */
public class MemoryData {
    private static ConcurrentHashMap<String, String> sessionIdMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, String> getSessionIdMap() {
        return sessionIdMap;
    }
}