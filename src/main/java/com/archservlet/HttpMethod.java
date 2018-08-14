package com.archservlet;

public enum HttpMethod {
    ANY,
    GET, 
    POST, 
    PUT, 
    DELETE;
    
    public static HttpMethod create(String s) {
        for(HttpMethod m : HttpMethod.values()) {
            if(m.toString().equals(s))
                return m;
        }
        return null;
    }
    
   
}
