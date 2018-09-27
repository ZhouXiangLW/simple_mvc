package com.tw.relife.exception;

import com.tw.relife.annotations.RelifeStatusCode;

@RelifeStatusCode(statusCode = 409)
public class SimpleConfiltException extends RuntimeException {
}
