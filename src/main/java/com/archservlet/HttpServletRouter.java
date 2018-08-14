package com.archservlet;

import java.util.Map;

import static com.archservlet.HttpMethod.*;

import static com.archservlet.UriUtil.parse;



@FunctionalInterface
public interface HttpServletRouter<Request extends HttpServletRequestHolder, Response extends HttpServletResponseHolder> {

    String PARSED_URI_PARAM_ATTRIBUTE_NAME = "URI_PARAMS";
    
    void handle(Request req, Response res) throws Exception;

    static <Request extends HttpServletRequestHolder, Response extends HttpServletResponseHolder> 
    boolean any(String template, HttpServletRouter<Request,Response> route, Request req, Response res) throws Exception {
        return route(ANY, template, route, req, res);
    }

    static <Request extends HttpServletRequestHolder,Response extends HttpServletResponseHolder> 
    boolean get(String template, HttpServletRouter<Request,Response> route, Request req, Response res) throws Exception {
        return route(GET, template, route, req, res);
    }

    static <Request extends HttpServletRequestHolder,Response extends HttpServletResponseHolder> 
    boolean post(String template, HttpServletRouter<Request,Response> route, Request req, Response res) throws Exception {
        return route(POST, template, route, req, res);
    }

    static <Request extends HttpServletRequestHolder,Response extends HttpServletResponseHolder> 
    boolean put(String template, HttpServletRouter<Request,Response> route, Request req, Response res) throws Exception {
        return route(PUT, template, route, req, res);
    }

    static <Request extends HttpServletRequestHolder,Response extends HttpServletResponseHolder> 
    boolean delete(String template, HttpServletRouter<Request,Response> route, Request req, Response res) throws Exception {
        return route(DELETE, template, route, req, res);
    }
    
    static <Request extends HttpServletRequestHolder,Response extends HttpServletResponseHolder>
    boolean route(HttpMethod method, String template, HttpServletRouter<Request,Response> route, Request req, Response res) throws Exception 
    {
        final String reqMethod =    req.raw().getMethod().toUpperCase();
        final String reqPath =      req.raw().getPathInfo();
        
        boolean isMethodMatching =  reqMethod.equals( method.toString() );
        boolean isAny =             method.equals(ANY);
        
        if (isMethodMatching || isAny) {
            Map<String,String> params = parse(template, reqPath);
            boolean isTemplateMatchingPath = params != null;
            
            if (isTemplateMatchingPath) {
                req.raw().setAttribute(PARSED_URI_PARAM_ATTRIBUTE_NAME, params);
                route.handle(req, res); // Route to target method
                
                return true;
            }
        }
        return false;
    }
}