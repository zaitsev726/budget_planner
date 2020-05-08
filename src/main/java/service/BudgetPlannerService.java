package service;

import repository.BudgetPlannerRepository;

public class BudgetPlannerService {
    private final BudgetPlannerRepository repository;

    public BudgetPlannerService(BudgetPlannerRepository repository) {
        this.repository = repository;
    }
}
