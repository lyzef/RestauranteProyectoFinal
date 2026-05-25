package utilidades.views;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import excepciones.InvalidUser;

public class PanelTipoPreguntaUtil extends JPanel{
	private JLabel lblPregunta;
    private JTextField JfieldEntrada;
    private JLabel lblError;
    private String tipoClasificacion;
    
    public JLabel getLblPregunta() {
		return lblPregunta;
	}

	public void setLblPregunta(JLabel lblPregunta) {
		this.lblPregunta = lblPregunta;
	}

	public JLabel getLblError() {
		return lblError;
	}
	
	public JTextField getJfieldEntrada() {
		return JfieldEntrada;
	}

	public void setJfieldEntrada(JTextField txtEntrada) {
		this.JfieldEntrada = txtEntrada;
	}

	public void setLblError(JLabel lblError) {
		this.lblError = lblError;
	}

	public String getTipoClasificacion() {
		return tipoClasificacion;
	}
	
	public void setTipoClasificacion(String tipoClasificacion) {
		this.tipoClasificacion = tipoClasificacion;
	}

    
    /**
    * Constructor que genera un panel con pregunta, field y texto de error (jlabel)
    * Se suele usar con los siguientes tipo de datos ALFANUMERICO, NUMERICO,DECIMAL, ALFABETICO,CORREO,FECHA,CONTRASENA
    * @param pregunta String contenido de pregunta
    * 
    */
    public PanelTipoPreguntaUtil(String pregunta, String caracteresAceptados) {  	
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	lblPregunta = new JLabel(pregunta);
    	JfieldEntrada = new JTextField();
    	lblError = new JLabel("");
    	lblError.setForeground(Color.RED);
    	tipoClasificacion = caracteresAceptados;
    	
    	add(lblPregunta);
    	add(JfieldEntrada);
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
    	if(JfieldEntrada.getText().isBlank()) {
			return true;
		}
    	return false;
    }
    
    public void modificarLabelError(String t) {
    	lblError.setText(t);
    }
    
    public void limpiarError() {
    	lblError.setText(null);
    }
    
    public String getTextoEntrada() {
    	return JfieldEntrada.getText();
    }
    
    public void setTextoEntrada(String texto) {
    	JfieldEntrada.setText(texto);
    }
    
    
    public void setEditable(boolean eleccion) {
    	JfieldEntrada.setEditable(eleccion);
    }
}