package com.exigenservices.lectures.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Ti_g_programmist(no) on 03.12.2016.
 */
public final class InMemoryStorage {

    private static final List<String> storage = new CopyOnWriteArrayList<String>();

    private InMemoryStorage() {
    }

    public static void add(String value) {
        storage.add(value);
    }

    public static List<String> getValues() {
        return new ArrayList<String>(storage);
    }
}

