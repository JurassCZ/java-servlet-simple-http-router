package com.archservlet;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class Response implements HttpServletResponseHolder{
    private final HttpServletResponse servletRes;

    public HttpServletResponse raw() {
        return servletRes;
    }
}
