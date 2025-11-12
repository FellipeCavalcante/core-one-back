package com.coreone.back.modules.auth.util;

import com.coreone.back.modules.user.dto.CreateUserRequestDTO;

public class TestUserBuilder {

    private static long emailCounter = System.currentTimeMillis();

    public static CreateUserRequestDTO createValidUser() {
        return createValidUser("CLIENT");
    }

    public static CreateUserRequestDTO createValidUser(String type) {
        CreateUserRequestDTO user = new CreateUserRequestDTO();
        user.setName("Usuario Teste " + emailCounter);
        user.setEmail("teste" + emailCounter++ + "@email.com");
        user.setPassword("123456");
        user.setType(type);
        return user;
    }

    public static CreateUserRequestDTO createUserWithInvalidEmail() {
        CreateUserRequestDTO user = new CreateUserRequestDTO();
        user.setName("Usuario Teste");
        user.setEmail("email-invalido");
        user.setPassword("123456");
        user.setType("CLIENT");
        return user;
    }

    public static CreateUserRequestDTO createUserWithShortPassword() {
        CreateUserRequestDTO user = new CreateUserRequestDTO();
        user.setName("Usuario Teste");
        user.setEmail("teste" + emailCounter++ + "@email.com");
        user.setPassword("123");
        user.setType("CLIENT");
        return user;
    }

    public static CreateUserRequestDTO createUserWithMissingName() {
        CreateUserRequestDTO user = new CreateUserRequestDTO();
        user.setName("");
        user.setEmail("teste" + emailCounter++ + "@email.com");
        user.setPassword("123456");
        user.setType("CLIENT");
        return user;
    }

    public static CreateUserRequestDTO createUserWithNullFields() {
        CreateUserRequestDTO user = new CreateUserRequestDTO();
        user.setName(null);
        user.setEmail(null);
        user.setPassword(null);
        user.setType(null);
        return user;
    }

    public static CreateUserRequestDTO createUserWithSpecificEmail(String email) {
        CreateUserRequestDTO user = new CreateUserRequestDTO();
        user.setName("Usuario Especifico");
        user.setEmail(email);
        user.setPassword("123456");
        user.setType("CLIENT");
        return user;
    }
}
