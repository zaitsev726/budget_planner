package service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BudgetPlannerRepository;

@Service
public class BudgetPlannerService {
    private final BudgetPlannerRepository repository;

    @Autowired
    public BudgetPlannerService(BudgetPlannerRepository repository) {
        this.repository = repository;
    }
}
