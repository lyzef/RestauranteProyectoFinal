package excepciones;

public class InvalidUser extends Exception{

	public InvalidUser() {
		super("El usuario no es valido");
	}
	
	public InvalidUser(char caracter) {
		super("El campo no puede contener"+ caracter);
	}
	public InvalidUser(String mensaje) {
		super(mensaje);
	}
}
