package main;

import controller.LoginController;
import views.Login;

public class Main {
    public static void main(String[] args) {
        Login vista = new Login();
        new LoginController(vista);
    }
}