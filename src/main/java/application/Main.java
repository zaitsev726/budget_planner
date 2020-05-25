package application;

import controller.GuiController;
import gui.ApplicationFrame;

public class Main {
    public static void main(String[] args) {
        new ApplicationFrame(new GuiController());
    }
}
