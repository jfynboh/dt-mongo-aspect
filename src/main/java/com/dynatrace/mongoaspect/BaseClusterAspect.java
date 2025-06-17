package com.dynatrace.mongoaspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.dynatrace.oneagent.sdk.OneAgentSDKFactory;
import com.dynatrace.oneagent.sdk.api.OneAgentSDK;

@Aspect
public class BaseClusterAspect {

    // prior to 4.10
    @Around("execution( * com.mongodb.internal.connection.BaseCluster.selectServerAsync(*,*))")
    public void selectServerAsyncAdvice(ProceedingJoinPoint pjp) throws Throwable {
        //System.out.println("-- In BaseClusterAspect.selectServerAsyncAdvice()");
        Object[] args = pjp.getArgs();
        try {
            args = InstrumentationHelper.wrapSingleResultCallback(args, 1);
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }

        pjp.proceed(args);
    }

    // since 4.10
    @Around("execution( * com.mongodb.internal.connection.BaseCluster.selectServerAsync(*,*,*))")
    public void selectServerAsync410Advice(ProceedingJoinPoint pjp) throws Throwable {
        //System.out.println("-- In BaseClusterAspect.selectServerAsync410Advice()");
        Object[] args = pjp.getArgs();
        try {
            args = InstrumentationHelper.wrapSingleResultCallback(args, 2);
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }

        pjp.proceed(args);
    }
}
