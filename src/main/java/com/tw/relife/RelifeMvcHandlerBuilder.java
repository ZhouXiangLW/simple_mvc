package com.tw.relife;

import java.util.HashMap;
import java.util.Map;

public class RelifeMvcHandlerBuilder implements RelifeAppHandler {

    private Map<RelifeHttpPath, RelifeAppHandler> handlerMap = new HashMap<>();

    @Override
    public RelifeResponse process(RelifeRequest request) {
        RelifeAppHandler handler = getHandlerFromMap(request);
        if (handler == null) {
            return new RelifeResponse(404);
        }
        RelifeResponse response = handler.process(request);
        return response == null ? new RelifeResponse(200) : response;
    }

    public RelifeAppHandler build() {
        return this;
    }

    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod method, RelifeAppHandler handler) {
        if (path == null || method == null || handler == null) {
            throw new IllegalArgumentException();
        }
        if (handlerMap.containsKey(new RelifeHttpPath(path, method))) {
            return this;
        }
        handlerMap.put(new RelifeHttpPath(path, method), handler);
        return this;
    }

    private RelifeAppHandler getHandlerFromMap(RelifeRequest request) {
        RelifeHttpPath httpPath = new RelifeHttpPath(request.getPath(), request.getMethod());
        if (handlerMap.containsKey(httpPath)) {
            return handlerMap.get(httpPath);
        }
        return null;
    }
}
