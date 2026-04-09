package utilidades;

import java.awt.event.KeyEvent;

import excepciones.InvalidUser;
import excepciones.invalidInput;

public class comprobacionTipoCaracterEntradaUtil {
	
	private comprobacionTipoCaracterEntradaUtil() {
		
	}
	
	/**
     *Verifica que el caracter ingresado pertenesca a los caracteres permitidos segun el tipo de entrada
     *@throws invalidInput Tira en caso que el caracter no pertenesca al tipo definido
     */
	public static void validarCaracterSegunTipo(char c, String tipo) throws invalidInput {
		switch (tipo) {
		case "ALFANUMERICO": {
			esAlfanumerico(c);
			break;
		}
		case "NUMERICO": {
			esNumerico(c);
			break;
		}
		case "ALFABETICO":{
			esAlfabetico(c);
			break;
		}
		case "CORREO":{
			esTipoCorreo(c);
			break;
		}
		case "FECHA":{
			esTipoFecha(c);
			break;
		}
		default:
			throw new IllegalArgumentException("Clasificacion de string desconocida: " + tipo);
		}
	}
	
	private static void esAlfabetico(char c) throws invalidInput {
		if(Character.isLetter(c) || c == ' ' || c == KeyEvent.VK_BACK_SPACE) {
			return;
		}
		throw new invalidInput("Solo caracteres alfabeticos");
    }
    
    private static void esNumerico(char c) throws invalidInput {
		if(Character.isDigit(c) || c == ' ' || c == KeyEvent.VK_BACK_SPACE) {
			return;
		}
		throw new invalidInput("Solo caracteres numericos");
    }
    
    private static void esAlfanumerico(char c) throws invalidInput {
		if(Character.isLetterOrDigit(c) || c == ' ' || c == KeyEvent.VK_BACK_SPACE) {
			return;
		}
		throw new invalidInput("Solo caracteres alfanumericos");
    }
    
    private static void esTipoCorreo(char c) throws invalidInput {
		if(c == '@' ||c == '.' ||c == '_' ||c == '-' || Character.isLetterOrDigit(c) || c == KeyEvent.VK_BACK_SPACE) {
			return;
		}
		
		throw new invalidInput("Caracter no admitido");
    }
    
    private static void esTipoFecha(char c) throws invalidInput {
		if(c == '/' || Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE) {
			return;
		}
		
		throw new invalidInput("En forma DD/MM/AAAA");
    }
}
