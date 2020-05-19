package application;

public class Main {
    public static void main(String[] args) {
        new MainFrame(new GuiController(new BudgetPlannerService(), new CategoryController()));
    }
}
