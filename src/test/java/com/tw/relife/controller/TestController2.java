package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;

@RelifeController
public class TestController2 {
    @RelifeRequestMapping(value = "/test_form_2", method = RelifeMethod.GET)
    public RelifeResponse test(RelifeRequest request) {
        return new RelifeResponse(201, "from test controller 2", "text/plain");
    }

    @RelifeRequestMapping(value = "/test", method = RelifeMethod.GET)
    public RelifeResponse test2(RelifeRequest request) {
        return new RelifeResponse(201, "test from controller 2", "text/plain");
    }
}
