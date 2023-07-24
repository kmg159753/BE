package com.example.newnique.global;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Slf4j(topic = "UseTimeAop")
@Aspect
@Component
@RequiredArgsConstructor
public class TimeAop {

    @Pointcut("execution(* com.example.newnique.news.controller.NewsController.*(..))")
    private void news() {}


    @Around("news() ")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 측정 시작 시간
        long startTime = System.currentTimeMillis();

        try {
            // 핵심기능 수행
            Object output = joinPoint.proceed();
            return output;
        } finally {
                // 측정 종료 시간
                long endTime = System.currentTimeMillis();
                // 수행시간 = 종료 시간 - 시작 시간
                long runTime = endTime - startTime;

                log.info("runtime : " + runTime);
        }
    }
}