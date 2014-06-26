package com.perfect.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by vbzer_000 on 2014/6/27.
 */
@Aspect
public class LogAop {

    @AfterReturning(pointcut = "execution(int com.perfect.*.insert*(..))", returning = "o")
    public void logSuccess(Object o){


    }
}
