package com.exigenservices.lectures.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Ti_g_programmist(no) on 03.12.2016.
 */
public final class ViewHelper {

    private ViewHelper() {
    }

    public static String getValuesAsString() {
        return StringUtils.join(InMemoryStorage.getValues(), "<br/>");
    }
}
