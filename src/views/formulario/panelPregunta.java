package views.formulario;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import excepciones.InvalidUser;

public class panelPregunta extends JPanel{
	private JLabel lblPregunta;
    private JTextField txtEntrada;
    private JLabel lblError;
    
    private String caracteresAceptados;
    
    /**
    * Constructor que genera un panel con pregunta, field y texto de error (jlabel)
    * que senaliza error en el texto ingresado
    * @param pregunta String contenido de pregunta
    * @param caracteresAceptados elegir entre ALFANUMERICO, NUMERICO, ALFABETICO,CORREO,FECHA
    */
    public panelPregunta(String pregunta, String caracteresAceptados) {
    	this.caracteresAceptados = caracteresAceptados;
    	
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	lblPregunta = new JLabel(pregunta);
    	txtEntrada = new JTextField();
    	lblError = new JLabel("");
    	lblError.setBackground(Color.RED);
    	
    	//Caracteres aceptados segun clasificacion
    	txtEntrada.addKeyListener(new KeyAdapter() {
    		@Override
		    public void keyTyped(KeyEvent e) {
    			//Valida el error anterior
    			lblError.setText(null);
    			//Evita entrada de enter a sistema de clasificacionj
    			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
    	            return;
    	        }
    			
				switch (caracteresAceptados) {
				case "ALFANUMERICO": {
					aceptarNumerosODigitos(e);
					break;
				}
				case "NUMERICO": {
					aceptarNumeros(e);
					break;
				}
				case "ALFABETICO":{
					aceptarAlfabeto(e);
					break;
				}
				case "CORREO":{
					aceptarCorreo(e);
					break;
				}
				case "FECHA":{
					aceptarFecha(e);
					break;
				}
				default:
					throw new IllegalArgumentException("Clasificacion de string desconocida: " + caracteresAceptados);
				}
		    }
    		
    		
		});
    	
    	//Contenido de textField aceptado segun clasificacion
    	txtEntrada.addFocusListener(new FocusAdapter() {
    		@Override
    	    public void focusLost(FocusEvent e) {
    	        validarContenido();
    	    }
		});
    	
    	add(lblPregunta);
    	add(txtEntrada);
    	add(lblError);
    	
    }
    /**
     * El texto de error dentro de pregunta se sobreescribe
     * para llamar la atencion del usuario y que rellene el hueco
     */
    public void senalarEntradaVacia() {
    	lblError.setText("Entrada vacia");
    }
    
    /**
     * Regresa verdadero si el text field de la pregunta esta vacio
     */
    public boolean estaVacio() {
    	if(txtEntrada.getText().isBlank()) {
			return true;
		}
    	return false;
    }
    
    private void aceptarAlfabeto(KeyEvent e) {
    	char c = e.getKeyChar();
		if(Character.isLetter(c) || c == ' ' || c == KeyEvent.VK_BACK_SPACE) {
			return;
		}
		lblError.setText("Solo caracteres alfabeticos");
		e.consume();
    }
    
    private void aceptarNumeros(KeyEvent e) {
    	char c = e.getKeyChar();
		if(Character.isDigit(c) || c == ' ' || c == KeyEvent.VK_BACK_SPACE) {
			return;
		}
		lblError.setText("Solo caracteres numericos");
		e.consume();
    }
    
    private void aceptarNumerosODigitos(KeyEvent e) {
    	char c = e.getKeyChar();
		if(Character.isLetterOrDigit(c) || c == ' ' || c == KeyEvent.VK_BACK_SPACE) {
			return;
		}
		lblError.setText("Solo caracteres alfanumericos");
		e.consume();
    }
    
    private void aceptarCorreo(KeyEvent e) {
    	char c = e.getKeyChar();
		if(c == '@' ||c == '.' ||c == '_' ||c == '-' || Character.isLetterOrDigit(c) || c == KeyEvent.VK_BACK_SPACE) {
			return;
		}
		
		lblError.setText("Caracter no admitido");
		e.consume();
    }
    
    private void aceptarFecha(KeyEvent e) {
    	char c = e.getKeyChar();
		if(c == '/' || Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE) {
			return;
		}
		
		lblError.setText("En forma DD/MM/AAAA");
		e.consume();
    }
    /**
     *Valida el contenido dentro del textfield de la pregunta
     *segun la clasificacion de la pregunta
     */
    public boolean validarContenido() {
    	String texto = txtEntrada.getText();
        
        switch(caracteresAceptados) { 
        case "CORREO":{
        	return validarCorreo(texto);
        } 
        case "FECHA":{
        	return validarFecha(texto);
        } 
        case "TELEFONO":{
        	// TODO terminar verificacion telefono y consiguientes clasificaciones
        	return true;
        } 
        default:
			return true;
        }
    }
    
    /**
     * Regresa verdadero si el contenido es un correo
     * @throws InvalidUser 
     */
    private boolean validarCorreo(String texto) throws InvalidUser {
    	int totalArrobas = 0;
    	String parteLocal = "";
    	String dominio = "";
    	String[] dominiosAdmitidos = {"gmail.com","outlook.com","outlook.es"};
    	for(char c : texto.toCharArray()) {
    		//Verifica total arrobas
    		if(c == '@') {
    			totalArrobas++;
    			if(totalArrobas > 1) {
    				lblError.setText("Correo invalido");
    				return false;
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
    		lblError.setText("Correo invalido");
    		return false;
    	}
    	//Comprobar dominio
    	if(dominio.length() < 3) {
    		lblError.setText("Dominio invalido");
    		return false;
    	}
    	dominio = dominio.toLowerCase();
    	for(String dominioAdmitido : dominiosAdmitidos) { //Compara el dominio con los DNS
    		if(dominio.equals(dominioAdmitido)) {
    			return true;
    		}
    	}
    	System.out.println(dominio);
    	lblError.setText("Dominio invalido");
    	throw new InvalidUser("Dominio invalido");
    	
    	return false;
    }
    
    /**
     * Regresa verdadero si el contenido es una fecha
     * de tipo DD/MM/AAAA
     */
    private boolean validarFecha(String texto) {
        //Validar longitud 
        if (texto.length() != 10) {
            lblError.setText("Longitud incorrecta (DD/MM/AAAA)");
            return false;
        }

        //Comprobar separadores '/'
        if (texto.charAt(2) != '/' || texto.charAt(5) != '/') {
            lblError.setText("Usar el formato DD/MM/AAAA");
            return false;
        }

        try {
            //recortar valores de texto con substring
            //substring(inicio, fin) -> el fin no se toma en cuenta
        	//[1,2,3] - > [1,2] when substring(0,2) :o
            int dia = Integer.parseInt(texto.substring(0, 2));
            int mes = Integer.parseInt(texto.substring(3, 5));
            int anio = Integer.parseInt(texto.substring(6, 10));

            // 4. Validar Día 
            if (dia < 1 || dia > 31) {
                lblError.setText("Día inválido (1-31)");
                return false;
            }

            // 5. Validar Mes
            if (mes < 1 || mes > 12) {
                lblError.setText("Mes inválido (1-12)");
                return false;
            }

            // 6. Validar Año (rango razonable)
            if (anio < 1969 || anio > 2067) {
                lblError.setText("Año invalido");
                return false;
            }

        } catch (NumberFormatException e) {
            lblError.setText("Usar el formato DD/MM/AAAA");
            return false;
        }

        lblError.setText(""); 
        return true;
    }
    
}
