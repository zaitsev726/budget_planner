package application;

public class Main {
    public static void main(String[] args) {
        new MainFrame(new Controller(new BudgetPlannerService(new InMemoryRepository())));
    }
}
