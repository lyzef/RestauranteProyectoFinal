package main;

import controller.HubController;
import controller.LoginController;
import views.Hub;
import views.Login;

public class Main {
    public static void main(String[] args) {
    	//Probando servicio
    	try {
            Class.forName("config.DatabaseConnection"); 
        } catch (ClassNotFoundException e) {
            System.out.println("No se pudo inicializar la clase de conexión");
        }
        new LoginController(new Login());
        
        //new HubController(new Hub());
    }
}