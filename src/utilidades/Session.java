package utilidades;

import models.User;

//Clase con atributo estatica que permite conocer el TIPO de usuario ACTUAL
public class Session {
	
	private static User currentUser;
	
	public static void login(User user) {
		currentUser = user;
	}
	
	public static User getCurrentUser() {
		return currentUser;
	}
	
	public static void logout() {
		currentUser = null;
	}
	
	public static boolean isLoggedIn() {
		return currentUser != null;
	}
	
	public static String getRol( ) {
		return currentUser.getRol();
	}
	

}
