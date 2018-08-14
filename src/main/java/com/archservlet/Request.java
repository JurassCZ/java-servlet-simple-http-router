package com.archservlet;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
public class Request implements HttpServletRequestHolder {
    private final HttpServletRequest servletReq;
    
    public HttpServletRequest raw() {
        return servletReq;
    }
    
    public Map<String,String> uriParams() {
        return (Map<String,String>) servletReq.getAttribute(HttpServletRouter.PARSED_URI_PARAM_ATTRIBUTE_NAME);
    }
    
}
