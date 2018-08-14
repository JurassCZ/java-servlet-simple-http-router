package com.archservlet;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UriUtilTest {

    @Test
    public void parse() {
        String template = "/*/user/{userName}/sex/{sex}/*/";
        String path = "/anything/user/John/sex/male/anything//////]34^€¤³³";
        
        Map<String,String> params = UriUtil.parse(template, path);
        assertNull(params);

        params = UriUtil.parse(template, path + "/");

        assertNotNull(params);
        assertEquals("John", params.get("userName"));
        assertEquals("male", params.get("sex"));
    }
}