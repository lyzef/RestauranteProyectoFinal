package controller;

import java.io.IOException;
import java.util.List;

import models.User;
import repository.UserRepository;
import views.*;

public class HubController {
	Hub hub;
	public HubController(Hub hub) {
		this.hub = hub;
		addListeners();
	}
	
	public void addListeners() {
		hub.getTocame().addActionListener( e -> {
			UserRepository repository = new UserRepository();
			
			try {
				List <User> usuarios = repository.getUsers();
				
				for(User user : usuarios) {
					System.out.println(user);
					System.out.println("---------------");
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("No se pudo generar la lista de usuarios");
			}
		});
	}
}
