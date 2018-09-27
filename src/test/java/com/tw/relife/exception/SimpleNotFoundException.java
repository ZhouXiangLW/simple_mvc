package com.tw.relife.exception;

import com.tw.relife.annotations.RelifeStatusCode;

@RelifeStatusCode(statusCode = 404)
public class SimpleNotFoundException extends RuntimeException {
}
