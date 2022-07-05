package com.example.demo.aspects;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Future;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.example.demo.annotations.TrackExecutionForRequestEvent;
import com.example.demo.events.RequestEvent;


@Component
@Aspect
public class TimingAdvice {
    public static Logger log = LoggerFactory.getLogger(TimingAdvice.class);

    @Around("@annotation(com.example.demo.annotations.TrackExecutionForRequestEvent)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        Instant start = Instant.now();

        Object object = point.proceed();
        // log.info(String.valueOf(Future.class.isInstance(object)) );
        if (!Future.class.isInstance(object) && (!RequestEvent.class.isInstance(object))) {
            return new RuntimeException("TrackExecutionForRequestEvent annotation configured incorrectly. Ensure methods on which this annotation is applied return either RequestEvent or ListenableFuture<RequestEvent>");
        }
        // if (ListenableFuture.class.isAssignableFrom(object.getClass())){
        //     ((ListenableFuture)object).addCallback());
        // }
        if (Future.class.isInstance(object)){
            logForRequestEvent(((Future<RequestEvent>)object).get(), start);
        }
        else if (RequestEvent.class.isInstance(object)){
            logForRequestEvent((RequestEvent)object, start);
        }
        /*
        if(!RequestEvent.class.isInstance(object)) {
            return new RuntimeException("TrackExecutionForRequestEvent configured incorrectly");
        }
        RequestEvent event = (RequestEvent)object;
        Instant finish = Instant.now();
        Duration timeDuration = Duration.between(start, finish);
        log.info("[PROCESSED]:{}\t\t{}\t\t\t{} ms\t\t{}", event.getUserAgent(),event.getRoute(), timeDuration.toMillis(),event.getMessage());
        */return object;
    }

    private void logForRequestEvent(RequestEvent event, Instant start) {
        Instant finish = Instant.now();
        Duration timeDuration = Duration.between(start, finish);
        log.info("[PROCESSED]:{}\t\t{}\t\t\t{} ms\t\t{}", event.getUserAgent(),event.getRoute(), timeDuration.toMillis(),event.getMessage());
    }
}
