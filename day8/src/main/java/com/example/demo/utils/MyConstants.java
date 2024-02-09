package com.example.demo.utils;
import java.util.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
public class MyConstants {
    public static final String AUTH="/api/v1/auth";
    public static final String REGISTER="/register";
    public static final String LOGIN="/login";

    // Web Security
    public static final List<String> ORIGNS=Arrays.asList("https://localhost:4000","https://localhost:4000");
    public static final List<String> HEADERS=Arrays.asList(HttpHeaders.AUTHORIZATION,HttpHeaders.CONTENT_TYPE);
    public static final List<String> METHODS=Arrays.asList(HttpMethod.GET.name(),HttpMethod.POST.name(),HttpMethod.PUT.name(),HttpMethod.DELETE.name(),HttpMethod.HEAD.name());


    //JsonWebtoken
    public static final String SWAGGER_LOCALHOST_URL = "http://localhost:8080";
    public static final String SWAGGER_SECURITY_SCHEME_NAME = "bearerAuth";
    public static final String SWAGGER_SCHEME = "bearer";
    public static final String SWAGGER_BEARER_FORMAT = "JWT";
    public static final String SWAGGER_DESCRIPTION = "produce a json token";
}
