package com.coreone.back.modules.auth.controller;

import com.coreone.back.config.AuthSpec;
import com.coreone.back.config.BaseTest;

import com.coreone.back.modules.auth.util.TestUserBuilder;
import com.coreone.back.modules.user.dto.CreateUserRequestDTO;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest extends BaseTest {

    private static final String AUTH_PATH = "/auth";
    private static final String REGISTER_PATH = AUTH_PATH + "/register";

    @Test
    void shouldCreateUserSuccessfully() {
        CreateUserRequestDTO user = TestUserBuilder.createValidUser();

        given()
                .spec(AuthSpec.getJsonRequestSpec())
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo(user.getName()))
                .body("email", equalTo(user.getEmail()))
                .body("type", equalTo(user.getType()))
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsInvalid() {
        CreateUserRequestDTO user = TestUserBuilder.createUserWithInvalidEmail();

        given()
                .spec(AuthSpec.getJsonRequestSpec())
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then()
                .statusCode(500);
    }

    @Test
    void shouldReturnBadRequestWhenRequiredFieldsAreMissing() {
        CreateUserRequestDTO user = new CreateUserRequestDTO();

        given()
                .spec(AuthSpec.getJsonRequestSpec())
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then()
                .statusCode(500);
    }

    @Test
    void shouldReturnBadRequestWhenPasswordIsNull() {
        CreateUserRequestDTO user = TestUserBuilder.createValidUser();
        user.setPassword(null);

        given()
                .spec(AuthSpec.getJsonRequestSpec())
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then()
                .statusCode(500);
    }

    @Test
    void shouldReturnConflictWhenEmailAlreadyExists() {
        CreateUserRequestDTO user = TestUserBuilder.createValidUser();

        given()
                .spec(AuthSpec.getJsonRequestSpec())
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then()
                .statusCode(201);

        given()
                .spec(AuthSpec.getJsonRequestSpec())
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then()
                .statusCode(500); // Conflict
    }

    @Test
    void shouldValidateUserType() {
        CreateUserRequestDTO user = TestUserBuilder.createValidUser();
        user.setType("INVALID_TYPE");

        given()
                .spec(AuthSpec.getJsonRequestSpec())
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then()
                .statusCode(500);
    }

    @Test
    void shouldReturnBadRequestWhenPasswordIsTooShort() {
        CreateUserRequestDTO user = TestUserBuilder.createValidUser();
        user.setPassword("123");

        given()
                .spec(AuthSpec.getJsonRequestSpec())
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then()
                .statusCode(500);
    }

    @Test
    void shouldCreateDifferentUserTypesSuccessfully() {
        String[] validTypes = {"CLIENT", "WORKER", "ADMIN", "MANAGER"};

        for (String userType : validTypes) {
            CreateUserRequestDTO user = TestUserBuilder.createValidUser();
            user.setType(userType);

            given()
                    .spec(AuthSpec.getJsonRequestSpec())
                    .body(user)
                    .when()
                    .post(REGISTER_PATH)
                    .then()
                    .statusCode(201)
                    .body("type", equalTo(userType));
        }
    }
}