package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
		showDashboard();
	}
	
	public void addListeners() {
		view.getBotonUsuarios().addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showUsers();
		    }
		});
		
		view.getBotonDashboard().addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showDashboard();
		    }
		});
	}
	
	private void showDashboard() {
		view.showView(Hub.DASHBOARD);
	}
	
	private void showUsers() {
		//Evita crear otro user controller
		if(userController == null) {
			userController = new UserController(view.getUserPanel());
		}
			
		userController.loadUsers();
		view.showView(Hub.USERS);
		
	}

}