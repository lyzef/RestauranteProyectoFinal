package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import models.User;
import repository.LoginRepository;
import repository.UserRepository;
import tableFormat.UserTableFormat;
import utilidades.Session;
import views.*;

public class HubController {
	Hub view;
	private UserController userController;
	private InventarioController inventarioController;
	
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
		
		view.getBotonInventario().addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showInventory();
		    }
		});
		
		view.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	if(Session.isLoggedIn()) {
			    	new LoginRepository().setSesionActiva(Session.getCurrentUser(), false);
		    	}
		    	view.dispose();
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
	
	private void showInventory() {
		if(inventarioController == null) {
			inventarioController = new InventarioController(view.getInventarioPanel());
		}
		
		//Cargar datos
		view.showView(Hub.INVENTORY);
		
	}

}