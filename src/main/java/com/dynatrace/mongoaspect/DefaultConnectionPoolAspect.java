package com.dynatrace.mongoaspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class DefaultConnectionPoolAspect {


    // since 4.10
    @Around("execution( * com.mongodb.internal.connection.DefaultConnectionPool.getAsync(*,*))")
    public void getAsync410Advice(ProceedingJoinPoint pjp) throws Throwable {
        //System.out.println("--- In  DefaultConnectionPoolAspect.getAsync410Advice()");
        Object[] args = pjp.getArgs();
                try {
            args = InstrumentationHelper.wrapSingleResultCallback(args, 1);
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }

        pjp.proceed(args);
    }

    // before 4.10
    @Around("execution( * com.mongodb.internal.connection.DefaultConnectionPool.getAsync(*))")
    public void getAsyncAdvice(ProceedingJoinPoint pjp) throws Throwable {
        //System.out.println("-- In DefaultConnectionPool.getAsyncAdvice()");
        Object[] args = pjp.getArgs();
        try {
            args = InstrumentationHelper.wrapSingleResultCallback(args, 0);
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }

        pjp.proceed(args);
    }
}
