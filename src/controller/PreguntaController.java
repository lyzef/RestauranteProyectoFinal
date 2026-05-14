package controller;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import excepciones.invalidInput;
import utilidades.ValidadorCadena;
import utilidades.comprobacionTipoCaracterEntradaUtil;
import utilidades.views.PanelTipoPreguntaUtil;

public class PreguntaController {
	/**
     *Registra el panel de preguntas para que valide su contenido
     */
    public static void registrarPanel(PanelTipoPreguntaUtil panel) {
    	//Caracteres aceptados segun clasificacion de entrada
    	
    	panel.getTxtEntrada().addKeyListener(new KeyAdapter() {
    		@Override
		    public void keyTyped(KeyEvent e) {
    			//Valida el error anterior
    			panel.limpiarError();
    			//Evita entrada de enter al sistema
    			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
    	            return;
    	        }
    			
    			char c = e.getKeyChar();
    			
    			try {
    				if(!comprobacionTipoCaracterEntradaUtil.validarCaracterSegunTipo(c,panel.getTipoClasificacion())) {
    					e.consume();
    				}
    						
				} catch (invalidInput error) {
					System.out.println(error.getMessage());
				}
    			
		    }
    		
    		
		});
    	
    	//Contenido de textField aceptado una vez perdido el foco segun su clasificacion de entrada
    	panel.getTxtEntrada().addFocusListener(new FocusAdapter() {
    		@Override
    	    public void focusLost(FocusEvent e) {
    			//Limpia el error anterior
    			panel.limpiarError();
    	        try {
					ValidadorCadena.validarContenido(panel.getTxtEntrada().getText(), panel.getTipoClasificacion());
				} catch (invalidInput e1) {
					//Mostrando a usuario el error en la entrada
					panel.modificarLabelError(e1.getMessage());
				}
    	    }
		});
    	
    }
}