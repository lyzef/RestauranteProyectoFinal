package utilidades;

import excepciones.InvalidUser;
import excepciones.invalidInput;
import utilidades.views.PanelTipoPreguntaUtil;

public class ValidadorEntradasTexto {
	 /**
     *Valida el contenido de una cadena segun su clasifiacion
     *@throws invalidInput Tira en caso que el contenido de la cadena no sea el esperado segun su clasificacion
     */
	
    public static void validarContenido(String texto, String tipo) throws invalidInput {
        if(texto.isBlank()) {
        	throw new invalidInput("Entrada vacia");
        }
    	
        switch(tipo) { 
        case "CORREO":{
        	validarCorreo(texto);
        	break;
        } 
        case "FECHA":{
        	validarFecha(texto);
        	break;
        } 
        case "TELEFONO":{
        	// TODO terminar verificacion telefono y consiguientes clasificaciones
        	break;
        }
        case "PRECIO":
        case "DECIMAL":{
        	validarDouble(texto);
        }
        default:
			break;
        }
    }
    
    public static void validarContenido(PanelTipoPreguntaUtil panel) throws invalidInput {
        validarContenido(panel.getTextoEntrada(), panel.getTipoClasificacion());
    }
    
    /**
     * Tira error en caso de cadena invalida para un correo
     * @throws invalidInput Error por correo invalido
     */
    private static void validarCorreo(String texto) throws invalidInput {
    	int totalArrobas = 0;
    	String parteLocal = "";
    	String dominio = "";
    	String[] dominiosAdmitidos = {"gmail.com","outlook.com","outlook.es","example.com"};
    	for(char c : texto.toCharArray()) {
    		//Verifica total arrobas
    		if(c == '@') {
    			totalArrobas++;
    			if(totalArrobas > 1) {
    				throw new invalidInput("Formato correo invalido");
    			}
    			continue;
    		}
    		
    		//Obtener dominio
    		if(totalArrobas != 0) {
    			dominio = dominio + c;
    		} else {
    			parteLocal = parteLocal + c;
    		}
    	}
    	//Comprobar parte local
    	if(parteLocal.length() < 3) {
    		throw new invalidInput("Correo invalido");
    	}
    	//Comprobar dominio
    	if(dominio.length() < 3) {
    		throw new invalidInput("Dominio invalido");
    	}
    	dominio = dominio.toLowerCase();
    	for(String dominioAdmitido : dominiosAdmitidos) { //Compara el dominio con los DNS
    		if(dominio.equals(dominioAdmitido)) {
    			return;
    		}
    	}
    	System.out.println(dominio);
    	throw new invalidInput("Dominio invalido");
    }
    
    /**
     * Tira error en caso de cadena invalida para fecha
     * de tipo DD/MM/AAAA
     * @throws invalidInput 
     */
    private static void validarFecha(String texto) throws invalidInput {
        //Validar longitud 
        if (texto.length() != 10) {
        	throw new invalidInput("Longitud incorrecta (DD/MM/AAAA)");
        }

        //Comprobar separadores '/'
        if (texto.charAt(2) != '/' || texto.charAt(5) != '/') {
        	throw new invalidInput("Usar el formato DD/MM/AAAA");
        }


        //recortar valores de texto con substring
        //substring(inicio, fin) -> el fin no se toma en cuenta
    	//[1,2,3] - > [1,2] when substring(0,2) :o
        int dia = Integer.parseInt(texto.substring(0, 2));
        int mes = Integer.parseInt(texto.substring(3, 5));
        int anio = Integer.parseInt(texto.substring(6, 10));

        // 4. Validar Día 
        if (dia < 1 || dia > 31) {
        	throw new invalidInput("Día inválido (1-31)");
        }

        // 5. Validar Mes
        if (mes < 1 || mes > 12) {
        	throw new invalidInput("Mes inválido (1-12)");
        }

        // 6. Validar Año (rango razonable)
        if (anio < 1910 || anio > 2067) {
        	throw new invalidInput("Año invalido nmms");
        }

   
    }
    
    private static void validarDouble(String texto) throws invalidInput {
    	try {
			Double.parseDouble(texto);
		} catch (NumberFormatException e) {
			throw new invalidInput("Texto no valido como numero");
		}
    	
    }
    

}
