package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

@RelifeController
public class TestController {

    @RelifeRequestMapping(value = "/test", method = RelifeMethod.GET)
    public RelifeResponse test(RelifeRequest request) {
        return new RelifeResponse(200, "test", "text/plain");
    }

    @RelifeRequestMapping(value = "/null", method = RelifeMethod.GET)
    public RelifeResponse returnNull(RelifeRequest request) {
        return null;
    }

    @RelifeRequestMapping(value = "/common_exception", method = RelifeMethod.GET)
    public RelifeResponse commonException(RelifeRequest request) {
        throw new RuntimeException();
    }

    @RelifeRequestMapping(value = "/private", method = RelifeMethod.GET)
    private RelifeResponse privateMethod(RelifeRequest request) {
        return new RelifeResponse(200, "private", "text/plain");
    }
}
