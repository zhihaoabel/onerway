package com.abel.onerway.utils;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class JsonUtil {

    public static Map<String, Object> json2Map(String str) {
        TreeMap<String, Object> map = JSON.parseObject(str, TreeMap.class);
        log.info("map = " + map);
        return map;
    }

    public static String map2Json(Map<String, Object> map) {
        String json = JSON.toJSONString(map);
        log.info("json = " + json);
        return json;
    }
}
