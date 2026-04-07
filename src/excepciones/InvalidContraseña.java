package excepciones;

public class InvalidContraseña extends Exception{

	public InvalidContraseña() {
		super("la contraseña es tan invalida como el usuario");
	}
	public InvalidContraseña(String mensaje) {
		super(mensaje);
	}
}
