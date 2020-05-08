package application;

import controller.Controller;
import gui.MainFrame;
import repository.InMemoryRepository;
import service.BudgetPlannerService;

public class Main {
    public static void main(String[] args) {
        new MainFrame(new Controller(new BudgetPlannerService(new InMemoryRepository())));
    }
}
