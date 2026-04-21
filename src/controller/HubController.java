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
	
	public HubController(Hub hub) {
		this.view = hub;
		addListeners();
	}
	
	public void addListeners() {
		view.btnUsers.addActionListener(e -> {
			showUsers();
		});
	}
	
	/**
	 *Obtiene los usuarios del repositorio
	 *Establece la tabla para el panel del usuario
	 *
	 */
	private void showUsers() {
		UserController userControl = new UserController(view.userPanel); //Controlador de view userView
		UserRepository repository = new UserRepository();
		
		try {
			List<User> users = repository.getUsers();
			
			UserTableModel model = new UserTableModel(users);
			
			view.userPanel.setTableModel(model);
			
			view.showView(Hub.USERS);
			
		}catch (IOException ex) {
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
		
	}
}
