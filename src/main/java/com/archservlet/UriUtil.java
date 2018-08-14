package com.archservlet;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UriUtil {
    
    private static Map<String, Container> cachedPatterns = new HashMap<>();

    /**
     * 
     * @param template
     * @param path
     * @return null if path no matching template, else list o params
     */
    public static Map<String, String> parse(String template, String path) {
        Container u = cachedPatterns.get(template);
        if(u == null) {
            u = toRegexPattern(template);
            cachedPatterns.put(template, u);
        }

        Map<String, String> params = null;
        Matcher m = u.pattern.matcher(path);
        if(m.find()) {
            params = new HashMap<>();
            for (int i=1; i<=m.groupCount(); i++) {
                params.put(u.parameterNames.get(i-1),m.group(i));
            }
        }
        
        return params;
    }
    
    /**
     * Converts path template to regex with named groups. This groups can be then used as named parameters.
     * @param template
     * @return example:
     *
     * For template:  /  *  / user /    {username}    / sex /     {sex}   / *
     * Return:        / .*  / user / (?<username>\w*) / sex / (?<sex>\w*) / .*
     * Regex matches: /rest / user /     John         / sex /     male    / anything / anything 
     */
    static Container toRegexPattern(String template) {
        String[] parts = template.split("/", -1);
        StringBuilder regex = new StringBuilder();
        Container ret = new Container();

        regex.append("^");
        
        for(int i = 1; i < parts.length; i++) { // 1 because of avoid first ""
            String p = parts[i];

            regex.append("/");

            if(p.matches("\\*")) { // "**" -> ".*"
                regex.append(".*");
            }
            else if(p.matches("\\{\\w*\\}")) {  // "{param}" -> "(?<param>\w*>)"
                String inner = p.replaceAll("[{}]", "");
                regex.append("(?<").append(inner).append(">\\w*)");
                
                // Add @inner as parameter name
                ret.parameterNames.add(inner);
            } else {
                regex.append(p);
            }
        }

        regex.append("$");
        
        ret.pattern = Pattern.compile(regex.toString());

        return ret;
    }
    
    private static class Container {
        @Getter
        Pattern pattern;
        @Getter
        List<String> parameterNames = new ArrayList<>(); // param names obtained from template
    }
}
