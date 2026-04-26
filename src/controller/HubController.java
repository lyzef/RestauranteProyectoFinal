package controller;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import models.User;
import repository.UserRepository;
import tablemodels.UserTableModel;
import views.*;

public class HubController {
	Hub view;
	private UserController userController;
	
	public HubController(Hub hub) {
		this.view = hub;
		addListeners();
		showHub();
	}
	
	public void addListeners() {
		view.btnUsers.addActionListener(e -> {
			showUsers();
		});
		
		view.btnHub.addActionListener(e -> {
			showHub();
		});
	}
	
	/**
	 *Muestra el menu en HUB
	 */
	private void showHub() {
		view.showView(Hub.MENU);
		updateMenuState(Hub.MENU);
	}
	
	/**
	 *Obtiene los usuarios del repositorio
	 *Establece la tabla para el panel del usuario
	 */
	private void showUsers() {
		if(userController == null) {
			userController = new UserController(view.userPanel);
		}
			
		userController.loadUsers();
		view.showView(Hub.USERS);
		updateMenuState(Hub.USERS);
		
	}
	
	//Activa o desactiva el boton del panel actual
	private void updateMenuState(String viewName) {
		view.btnUsers.setEnabled(!viewName.equals(Hub.USERS));
		view.btnHub.setEnabled(!viewName.equals(Hub.MENU));
	}
}
