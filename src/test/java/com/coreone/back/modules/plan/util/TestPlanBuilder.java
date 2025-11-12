package com.coreone.back.modules.plan.util;

import com.coreone.back.modules.plan.domain.Plan;

import java.math.BigDecimal;

public class TestPlanBuilder {

    public static Plan createBasicPlan() {
        Plan plan = new Plan();
        plan.setName("Plano Básico");
        plan.setValue(new BigDecimal("29.90"));
        plan.setWorkstationQtd(1);
        plan.setDescription("Plano básico para pequenos negócios");
        return plan;
    }

    public static Plan createProPlan() {
        Plan plan = new Plan();
        plan.setName("Plano Profissional");
        plan.setValue(new BigDecimal("99.90"));
        plan.setWorkstationQtd(5);
        plan.setDescription("Plano profissional para empresas");
        return plan;
    }

    public static Plan createEnterprisePlan() {
        Plan plan = new Plan();
        plan.setName("Plano Enterprise");
        plan.setValue(new BigDecimal("199.90"));
        plan.setWorkstationQtd(20);
        plan.setDescription("Plano enterprise para grandes corporações");
        return plan;
    }

    public static Plan createCustomPlan(String name, BigDecimal value, Integer workstationQtd, String description) {
        Plan plan = new Plan();
        plan.setName(name);
        plan.setValue(value);
        plan.setWorkstationQtd(workstationQtd);
        plan.setDescription(description);
        return plan;
    }

    public static java.util.List<Plan> createAllDefaultPlans() {
        return java.util.List.of(
                createBasicPlan(),
                createProPlan(),
                createEnterprisePlan()
        );
    }
}