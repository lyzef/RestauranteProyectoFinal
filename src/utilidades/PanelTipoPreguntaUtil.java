package utilidades;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import excepciones.InvalidUser;

public class PanelTipoPreguntaUtil extends JPanel{
	private JLabel lblPregunta;
    private JTextField txtEntrada;
    private JLabel lblError;
    private String tipoClasificacion;
    
    public JLabel getLblPregunta() {
		return lblPregunta;
	}

	public void setLblPregunta(JLabel lblPregunta) {
		this.lblPregunta = lblPregunta;
	}

	public JTextField getTxtEntrada() {
		return txtEntrada;
	}

	public void setTxtEntrada(JTextField txtEntrada) {
		this.txtEntrada = txtEntrada;
	}

	public JLabel getLblError() {
		return lblError;
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
    * Se suele usar con los siguientes tipo de datos ALFANUMERICO, NUMERICO, ALFABETICO,CORREO,FECHA
    * @param pregunta String contenido de pregunta
    * 
    */
    public PanelTipoPreguntaUtil(String pregunta, String caracteresAceptados) {  	
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	lblPregunta = new JLabel(pregunta);
    	txtEntrada = new JTextField();
    	lblError = new JLabel("");
    	lblError.setForeground(Color.RED);
    	tipoClasificacion = caracteresAceptados;
    	
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
    
    public void modificarLabelError(String t) {
    	lblError.setText(t);
    }
    
    public void limpiarError() {
    	lblError.setText(null);
    }
    
    public String obtenerTextoEntrada() {
    	return txtEntrada.getText();
    }
}