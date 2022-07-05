package com.example.demo.services;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.example.demo.annotations.TrackExecutionForRequestEvent;
import com.example.demo.events.RequestEvent;

@Service
public class HelloWorldService {

    public static String hello() throws InterruptedException{
        Thread.sleep(1000);
        String message = "hello";
        return message;
    }

    @TrackExecutionForRequestEvent
    public RequestEvent process(RequestEvent event) throws InterruptedException{
        Thread.sleep(1000);
        return  event;
    }

    @Async
    @TrackExecutionForRequestEvent
    public Future<RequestEvent> processAsync(RequestEvent event) throws InterruptedException {
        return new AsyncResult<RequestEvent>(process(event));
    }

}
