package application;

import controller.GuiController;
import gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        new MainFrame(new GuiController());
    }
}
