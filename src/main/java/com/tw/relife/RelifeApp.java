package com.tw.relife;

import com.tw.relife.annotations.RelifeStatusCode;

public class RelifeApp implements RelifeAppHandler {
    private final RelifeAppHandler handler;

    public RelifeApp(RelifeAppHandler handler) {
        // TODO: You can start here
        if (handler == null) {
            throw new IllegalArgumentException();
        }
        this.handler = handler;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) {
        // TODO: You can start here

        try {
            return handler.process(request);
        } catch (Exception e) {
            if (e.getClass().isAnnotationPresent(RelifeStatusCode.class)) {
                int statusCode = e.getClass().getDeclaredAnnotation(RelifeStatusCode.class).statusCode();
                return new RelifeResponse(statusCode);
            } else {
                return new RelifeResponse(500);
            }
        }
    }
}
