package com.blibli.future.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParentJsonData {
    private static List<Map<String, Object>> listData = new ArrayList<>();

    public static void putParentData(Map<String, Object> value) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.putAll(value);
        listData.add(mapData);
    }

    public static List<Map<String, Object>> getParentData() {
        return listData;
    }

    public static void clearParentData() {
        listData.clear();
    }
}
