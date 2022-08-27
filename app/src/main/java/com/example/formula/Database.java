package com.example.formula;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class Database {

    public static SortedMap<Integer, Float> backupValues = new TreeMap<Integer, Float>(){
        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }
    };

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

    public static void addValue(int id, float inputValue) {
        if (stackValues.size() != 0) {
            while (stackValues.containsKey(id)) {
                id++;
                if (!stackValues.containsKey(id)) {
                    break;
                }
            }
        }
        stackValues.put(id, inputValue);
    }

    public static String calcValue(int mapSize) {
        String result;
        if (mapSize == 1) {
            if (stackValues.get(1) == null) {
                result = "0.0";
            } else
                result = stackValues.get(1).toString();
        } else {
            float tempResult = 0;
            float equation;
            for (int i = 0; i < mapSize; i++) {
                int id = i + 1;
                float temp = 0;
                if (stackValues.get(id) != null) {
                    temp = 1 / stackValues.get(id);
                }
                tempResult = tempResult + temp;
            }
            if (tempResult == 0.0) {
                return Float.toString(tempResult);
            }
            equation = 1 / tempResult;
            result = Float.toString(equation);
        }
        return result;
    }
}
