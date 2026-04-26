package repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import models.User;

public class UserRepository {

	private final String FILE = "src/assets/files/users.csv";
	
	public void save(User user) throws IOException {
		
		try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE, true), StandardCharsets.UTF_8))) {
			writer.write(user.toCSV());
			writer.newLine();
		}
		
	}
	
	
	public List<User> getUsers() throws IOException {
		
		List<User> users = new ArrayList<User>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
			String line;
			
			while((line = reader.readLine()) != null) {
				User user = User.fromCSV(line);
				users.add(user);
			}
		}
		
		System.out.println("Usuarios obtenidos");
		return users;
		
	}
	

	public void updateAll(List<User> users) throws IOException {
	    try (BufferedWriter writer = new BufferedWriter(
	            new OutputStreamWriter(new FileOutputStream(FILE), StandardCharsets.UTF_8))) {

	        for (User user : users) {
	            writer.write(user.toCSV());
	            writer.newLine();
	        }
	    }
	}
	
	public void delete(int index) throws IOException {
		List<User> users = getUsers();
		users.remove(index);
		updateAll(users);
	}
	
	public void update(int index, User updatedUser) throws IOException {
		List<User> users = getUsers();
		users.set(index, updatedUser);
		updateAll(users);
	}
	
	
			
}