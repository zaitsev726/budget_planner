package controller;

import service.BudgetPlannerService;

public class Controller {
    private BudgetPlannerService service;

    public Controller(BudgetPlannerService budgetPlannerService) {
        service = budgetPlannerService;
    }
}
