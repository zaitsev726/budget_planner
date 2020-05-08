package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import service.BudgetPlannerService;

@RestController
public class Controller {
    @Autowired
    private BudgetPlannerService service;
}
