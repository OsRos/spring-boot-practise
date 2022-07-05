package com.example.demo.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.events.RequestEvent;
import com.example.demo.services.HelloWorldService;

@RestController
public class HelloWorldController{
    @Autowired
    public HelloWorldService service;

    @GetMapping("/hello")
    public String helloWorld() throws InterruptedException{
        return HelloWorldService.hello();
    }

    @GetMapping("/command")
    public void command(@RequestHeader("User-Agent") String userAgent) throws InterruptedException{
         RequestEvent  event = new RequestEvent(userAgent, "/command");
         service.process(event.message("Finished processing /command"));
    }

    @GetMapping("/command-async")
    public void commandAsync(@RequestHeader("User-Agent") String userAgent) throws InterruptedException{
         ExecutorService workerPool = Executors.newCachedThreadPool();
         workerPool.submit(() -> {
            try {
                RequestEvent  event = new RequestEvent(userAgent, "/command-async");
                service.process(event.message("Finished processing /command-async"));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    @GetMapping("/command-async-annotation")
    public void commandAsyncAnnotation(@RequestHeader("User-Agent") String userAgent) throws InterruptedException{
        RequestEvent  event = new RequestEvent(userAgent, "/command-async-annotation");
        service.processAsync(event.message("Finished processing /command-async-annotation"));
    }
    
}