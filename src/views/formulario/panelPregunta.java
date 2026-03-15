package views.formulario;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

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
		if(c == '@' || Character.isLetterOrDigit(c) || c == KeyEvent.VK_BACK_SPACE) {
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
    
    
    
}
