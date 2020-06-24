package com.asia.paint.base.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by chenhong14 on 2019-12-09.
 */
public final class MockUtils {

    private MockUtils() {

    }

    public static List<String> mockData(int count) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(String.valueOf(i));
        }
        return result;
    }
}
