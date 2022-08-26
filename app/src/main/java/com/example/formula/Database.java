package com.example.formula;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Database {

    public static SortedMap<Integer,Float> backupValues = new TreeMap<>();

    public static SortedMap<Integer, Float> stackValues = new TreeMap<Integer, Float>() {
        @NonNull
        @Override
        public String toString() {
            Iterator<Map.Entry<Integer, Float>> i = entrySet().iterator();
            if (!i.hasNext())
                return "{}";

            StringBuilder sb = new StringBuilder();
            while (i.hasNext()) {
                Map.Entry<Integer, Float> e = i.next();
                Integer key = e.getKey();
                Float value = e.getValue();
                sb.append('R');
                sb.append(key);
                sb.append('=');
                sb.append(value);
                sb.append('\n');
            }
            return sb.toString();
        }
    };

}
