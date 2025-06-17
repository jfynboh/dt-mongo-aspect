package com.dynatrace.mongoaspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.dynatrace.oneagent.sdk.OneAgentSDKFactory;
import com.dynatrace.oneagent.sdk.api.InProcessLink;
import com.dynatrace.oneagent.sdk.api.InProcessLinkTracer;
import com.dynatrace.oneagent.sdk.api.OneAgentSDK;

public class SingleResultCallbackProxy implements java.lang.reflect.InvocationHandler {
    private Object obj;
    private InProcessLink link;
    private static final OneAgentSDK oneagentSdk = OneAgentSDKFactory.createInstance();

    public static Object newInstance(Object obj) {

        // if (obj.getClass().getName().contains("ErrorHandlingResultCallback")) {
        // new Exception("Dump out Call Stack for: " + obj).printStackTrace();;
        // }

        InProcessLink link = oneagentSdk.createInProcessLink();
        return java.lang.reflect.Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new SingleResultCallbackProxy(link, obj));
    }

    private SingleResultCallbackProxy(InProcessLink link, Object obj) {
        this.obj = obj;
        this.link = link;
    }

    @SuppressWarnings("unchecked")
    public Object invoke(Object proxy, Method m, Object[] args)
            throws Throwable {
        Object result;

        try {
            // System.out.println("++In SingeResultCallBackProxy method " + m.getName() + "
            // Wrapped: " + obj + " Trace: "
            // + oneagentSdk.getTraceContextInfo().getTraceId());

            if ("onResult".equals(m.getName())) {
                @SuppressWarnings("rawtypes")
                ThreadLocal threadLocal = InstrumentationHelper.getThreadLocalForTag();
                Object tag = threadLocal.get();
                threadLocal.remove();
                ;

                InProcessLinkTracer tracer = oneagentSdk.traceInProcessLink(link);
                tracer.start();
                try {
                    result = m.invoke(obj, args);
                } finally {
                    tracer.end();
                    threadLocal.set(tag);
                }
            } else {
                result = m.invoke(obj, args);
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw e.getTargetException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("unexpected invocation exception: " +
                    e.getMessage());
        } finally {
            // System.out.println("--after method " + m.getName() + " Wrapped: " + obj);
        }
        return result;
    }

}
