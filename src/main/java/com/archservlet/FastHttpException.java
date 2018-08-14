package com.archservlet;

import javax.xml.ws.http.HTTPException;

public class FastHttpException extends Exception {

    private int statusCode;
    
    public FastHttpException(int statusCode) {
        this.statusCode = statusCode;
    }
    
    @Override
    public synchronized Throwable fillInStackTrace() {
        return null; // no stack trace make it fast
    }

    public int getStatusCode() {
        return statusCode;
    }
}
