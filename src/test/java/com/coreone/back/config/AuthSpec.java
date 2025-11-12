package com.coreone.back.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class AuthSpec {

    public static RequestSpecification getJsonRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType("application/json")
                .setAccept("application/json")
                .build();
    }
}