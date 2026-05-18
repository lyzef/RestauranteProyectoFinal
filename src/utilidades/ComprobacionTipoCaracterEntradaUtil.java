package utilidades;

import java.awt.event.KeyEvent;

import excepciones.InvalidUser;
import excepciones.invalidInput;

public class ComprobacionTipoCaracterEntradaUtil {
	
	private ComprobacionTipoCaracterEntradaUtil() {
		
	}
	
	/**
     *Verifica que el caracter ingresado pertenesca a los caracteres permitidos segun el tipo de entrada
     *@throws invalidInput Ocurre cuando la clasificacion ingresada es desconocida para esta clasificacion
     */
	public static boolean validarCaracterSegunTipo(char c, String tipo) throws invalidInput {
		switch (tipo) {
		case "ALFANUMERICO": {
			return esAlfanumerico(c);
		}
		case "NUMERICO": {
			return esNumerico(c);
		}
		case "ALFABETICO":{
			return esAlfabetico(c);
		}
		case "CORREO":{
			return esTipoCorreo(c);
		}
		case "FECHA":{
			return esTipoFecha(c);
		}
		default:
			throw new IllegalArgumentException("Clasificacion de string desconocida: " + tipo);
		}
	}
	
	private static boolean esAlfabetico(char c) {
		if(Character.isLetter(c) || c == ' ' || c == KeyEvent.VK_BACK_SPACE) {
			return true;
		}
		return false;
    }
    
    private static boolean esNumerico(char c) {
		if(Character.isDigit(c) || c == ' ' || c == KeyEvent.VK_BACK_SPACE) {
			return true;
		}
		return false;
    }
    
    private static boolean esAlfanumerico(char c){
		if(Character.isLetterOrDigit(c) || c == ' ' || c == KeyEvent.VK_BACK_SPACE) {
			return true;
		}
		return false;
    }
    
    private static boolean esTipoCorreo(char c){
		if(c == '@' ||c == '.' ||c == '_' ||c == '-' || Character.isLetterOrDigit(c) || c == KeyEvent.VK_BACK_SPACE) {
			return true;
		}
		
		return false;
    }
    
    private static boolean esTipoFecha(char c){
		if(c == '/' || Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE) {
			return true;
		}
		
		return false;
    }
}
