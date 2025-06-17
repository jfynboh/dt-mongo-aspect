package com.dynatrace.mongoaspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class InternalStreamConnectionAspect {

    // before 5.2.0
    @Around("execution( * com.mongodb.internal.connection.InternalStreamConnection.openAsync(*))")
    public void openAsyncAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // System.out.println("-- In InternalStreamConnectionAspect.openAsyncAdvice()");
        Object[] args = pjp.getArgs();
        try {
            args = InstrumentationHelper.wrapSingleResultCallback(args, 0);
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }

        pjp.proceed(args);
    }

    // since 5.2.0
    @Around("execution( * com.mongodb.internal.connection.InternalStreamConnection.openAsync(*,*))")
    public void openAsync520Advice(ProceedingJoinPoint pjp) throws Throwable {
        // System.out.println("-- In
        // InternalStreamConnectionAspect.openAsync520Advice()");
        Object[] args = pjp.getArgs();
        try {
            args = InstrumentationHelper.wrapSingleResultCallback(args, 1);
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }

        pjp.proceed(args);
    }

    // before 5.2.0
    @Around("execution( * com.mongodb.internal.connection.InternalStreamConnection.readAsync(*,*))")
    public void readAsyncAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // System.out.println("-- In InternalStreamConnectionAspect.readAsyncAdvice()");
        Object[] args = pjp.getArgs();
        try {
            args = InstrumentationHelper.wrapSingleResultCallback(args, 1);
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }

        pjp.proceed(args);
    }

    // since 5.2.0
    @Around("execution( * com.mongodb.internal.connection.InternalStreamConnection.readAsync(*,*,*))")
    public void readAsync520Advice(ProceedingJoinPoint pjp) throws Throwable {
        // System.out.println("-- In
        // InternalStreamConnectionAspect.readAsync520Advice()");
        Object[] args = pjp.getArgs();
        try {
            args = InstrumentationHelper.wrapSingleResultCallback(args, 2);
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }

        pjp.proceed(args);
    }

    // removed in 5.2.0
    @Around("execution( * com.mongodb.internal.connection.InternalStreamConnection.writeAsync(*,*))")
    public void writeAsyncAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // System.out.println("-- In
        // InternalStreamConnectionAspect.writeAsyncAdvice()");
        Object[] args = pjp.getArgs();
        try {
            args = InstrumentationHelper.wrapSingleResultCallback(args, 1);
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }

        pjp.proceed(args);
    }

    // since 5.2.0
    @Around("execution( * com.mongodb.internal.connection.InternalStreamConnection.sendMessageAsync(*,*,*,*))")
    public void sendMessageAsyncAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // System.out.println("-- In
        // InternalStreamConnectionAspect.sendMessageAsyncAdvice()");
        Object[] args = pjp.getArgs();
        try {
            args = InstrumentationHelper.wrapSingleResultCallback(args, 3);
        } catch (Exception e) {
            e.printStackTrace();
            // throw e;
        }

        pjp.proceed(args);
    }
}
