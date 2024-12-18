package com.iot.server;

import javax.servlet.http.HttpServletRequest;

public class RequestContextHolder {
    private static final ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<>();

    public static void setCurrentRequest(HttpServletRequest request) {
        currentRequest.set(request);
    }

    public static HttpServletRequest getCurrentRequest() {
        return currentRequest.get();
    }

    public static void clear() {
        currentRequest.remove(); // Clean up to avoid memory leaks
    }
}