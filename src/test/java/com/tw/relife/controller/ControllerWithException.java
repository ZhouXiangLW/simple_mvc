package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;
import com.tw.relife.exception.SimpleConfiltException;

@RelifeController
public class ControllerWithException {
    @RelifeRequestMapping(value = "/test", method = RelifeMethod.GET)
    public RelifeResponse test(RelifeRequest request) {
        throw new SimpleConfiltException();
    }
}
