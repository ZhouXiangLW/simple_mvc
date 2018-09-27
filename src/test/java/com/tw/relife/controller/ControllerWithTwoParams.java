package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

@RelifeController
public class ControllerWithTwoParams {
    @RelifeRequestMapping(value = "/test", method = RelifeMethod.GET)
    public RelifeResponse test(RelifeRequest request, RelifeRequest request2) {
        return new RelifeResponse(200, "test", "text/plain");
    }
}
