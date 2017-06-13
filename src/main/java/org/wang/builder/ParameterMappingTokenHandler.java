package org.wang.builder;


import org.wang.mapping.Configuration;
import org.wang.parse.TokenHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wy on 2017/4/27.
 */
public class ParameterMappingTokenHandler implements TokenHandler {
    Configuration configuration;
    List<String> paraNames=new ArrayList<String>();

    public ParameterMappingTokenHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    public String handleToken(String content) {
        paraNames.add(content);
        System.out.println("old "+content);
        System.out.println("new "+"?");
        return "?";
    }
}
