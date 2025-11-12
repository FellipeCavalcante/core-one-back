package com.coreone.back.modules.plan.controller;

import com.coreone.back.config.BaseTest;
import com.coreone.back.modules.plan.repository.PlanRepository;
import com.coreone.back.modules.plan.util.TestPlanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class PlanControllerTest extends BaseTest {

    private static final String PLAN_PATH = "/plan";

    @Autowired
    private PlanRepository planRepository;

    @BeforeEach
    void cleanDatabase() {
        planRepository.deleteAll();
    }

    @Test
    void shouldListAllPlansSuccessfully() {
        given()
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get(PLAN_PATH)
                .then()
                .statusCode(200)
                .body("$", isA(List.class))
                .contentType("application/json");
    }

    @Test
    void shouldReturnValidJSONStructure() {
        given()
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get(PLAN_PATH)
                .then()
                .statusCode(200)
                .body("$", isA(List.class));
    }

    @Test
    void shouldListAllPlansWithData() {
        planRepository.saveAll(TestPlanBuilder.createAllDefaultPlans());

        given()
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get(PLAN_PATH)
                .then()
                .statusCode(200)
                .body("$", hasSize(3))
                .body("name", hasItems("Plano Básico", "Plano Profissional", "Plano Enterprise"));
    }
}