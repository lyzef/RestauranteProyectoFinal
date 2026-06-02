package main;

import javax.swing.JFrame;

import controller.LoginController;
import controller.admin.HubAdminController;
import views.Login;
import views.Admin.HubFrame;
import views.AutoVenta.HubVentaFrame;
import views.Dialog.PlatilloDialog;

public class Main {
    public static void main(String[] args) {
    	//Probando servicio
    	try {
            Class.forName("config.DatabaseConnection"); 
        } catch (ClassNotFoundException e) {
            System.out.println("No se pudo inicializar la clase de conexión");
        }
    	new LoginController(new Login());
    	
        
    }
}