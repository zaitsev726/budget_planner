package controller;

import service.BudgetPlannerService;

public class GuiController {
    private BudgetPlannerService service;

    public GuiController(BudgetPlannerService budgetPlannerService) {
        service = budgetPlannerService;
    }
}
