package com.example.demo.events;

public class RequestEvent {
    private String userAgent;
    private String route;
    private String message;

    public RequestEvent(String userAgent, String route) {
        this.userAgent=userAgent;
        this.route=route;
    }

    public RequestEvent message(String message){
        this.message=message;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }
    public String getRoute() {
        return route;
    }
    public String getMessage() {
        return message;
    }

}
