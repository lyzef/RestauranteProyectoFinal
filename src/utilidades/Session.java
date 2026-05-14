package utilidades;

import models.User;

//Clase con atributo estatica que permite conocer el TIPO de usuario ACTUAL
public class Session {
	private static User CurrentUser;
	
	public static User get_CurrentUser() {
		return CurrentUser;
	}
	
	public static void login (User u) {
		CurrentUser = u;
	}
	
	public static void logOut() {
		CurrentUser = null;
	}
	
	public static boolean isLogged(){
		return CurrentUser != null ? true : false;
	}
	
}
