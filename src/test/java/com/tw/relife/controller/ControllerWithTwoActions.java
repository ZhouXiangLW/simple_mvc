package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

@RelifeController
public class ControllerWithTwoActions {

    @RelifeRequestMapping(value = "/test", method = RelifeMethod.GET)
    public RelifeResponse test(RelifeRequest request) {
        return new RelifeResponse(200, "test", "text/plain");
    }

    @RelifeRequestMapping(value = "/test_403", method = RelifeMethod.GET)
    public RelifeResponse test403(RelifeRequest request) {
        return new RelifeResponse(403, "403", "text/plain");
    }
}
