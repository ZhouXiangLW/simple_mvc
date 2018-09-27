package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.annotations.RelifeController;
import com.tw.relife.annotations.RelifeRequestMapping;
import com.tw.relife.entity.Student;

@RelifeController
public class RestController {
    @RelifeRequestMapping(value = "/json", method = RelifeMethod.GET)
    public Student returnJson(RelifeRequest request) {
        return new Student("ZhouXiang", 181);
    }
}
