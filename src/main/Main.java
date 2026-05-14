package main;

import controller.HubController;
import controller.LoginController;
import views.Hub;
import views.Login;

public class Main {
    public static void main(String[] args) {
        
        //new LoginController(new Login());
        
        new HubController(new Hub());
    }
}