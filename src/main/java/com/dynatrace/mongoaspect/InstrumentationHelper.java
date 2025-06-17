package com.dynatrace.mongoaspect;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.util.Map;

public class InstrumentationHelper {

    public static Object[] wrapSingleResultCallback(Object[] args, int index) {
        // System.out.println(" --- Trace Id: " +
        // oneagentSdk.getTraceContextInfo().getTraceId() + " Object: " + args[index] +
        // " Thread ID: "
        // + Thread.currentThread().getName());
        // System.out.println("++In InstrumentationHelper.wrap() method wrapped: " +
        // args[index] + " Trace: "
        // + oneagentSdk.getTraceContextInfo().getTraceId());

        try {
            Object objToWrap = args[index];

            Object wrapper = SingleResultCallbackProxy.newInstance(objToWrap);
            args[index] = wrapper;
            // }else {

            // }
        } catch (Exception e) {
            // Print stacktrace and swallow exception
            e.printStackTrace();
        }
        return args;

    }

    @SuppressWarnings("rawtypes")
    public static ThreadLocal getThreadLocalForTag() {
        Thread currentThread = Thread.currentThread();
        Map<?, ?> threadLocals = getAllThreadLocals(currentThread);
        ThreadLocal threadLocal = null;

        if (threadLocals != null && threadLocals.containsKey("table")) {
            Object[] table = (Object[]) threadLocals.get("table");

            for (Object entry : table) {
                if (entry != null) {
                    try {
                        Field valueField = entry.getClass().getDeclaredField("value");
                        valueField.setAccessible(true);
                        Object value = valueField.get(entry);

                        if (value != null && value.getClass().getName().contains("TraceTag")) {
                            Field referentField = Reference.class.getDeclaredField("referent");
                            referentField.setAccessible(true);
                            threadLocal = (ThreadLocal) referentField.get(entry);
                            return threadLocal;
                        }

                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // If null send an empty wrapper
        if (threadLocal == null) {
            threadLocal = new ThreadLocal<>() {
                @Override
                public Object get() {
                    return null;
                }

                @Override
                public void set(Object o) {
                }
            };
        }
        return threadLocal;
    }

    public static Map<?, ?> getAllThreadLocals(Thread thread) {
        try {
            Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
            threadLocalsField.setAccessible(true);
            Object threadLocalMap = threadLocalsField.get(thread);

            if (threadLocalMap == null) {
                return null;
            }

            Field tableField = threadLocalMap.getClass().getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] table = (Object[]) tableField.get(threadLocalMap);

            if (table == null) {
                return null;
            }

            return Map.of("table", table);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
