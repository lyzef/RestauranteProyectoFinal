package controller;

import utilidades.PanelTipoPreguntaUtil;
import utilidades.ValidadorCadena;
import views.formulario.FormularioRegistro;
import views.formulario.FormularioRegistroDatosExtras;
import views.formulario.FormularioRegistroInformacionPuesto;

public class FormularioController {
	private FormularioRegistro formularioEntrada;
	private FormularioRegistroDatosExtras formularioDatos;
	private FormularioRegistroInformacionPuesto formularioInformacion;
	
	public FormularioController(FormularioRegistro formulario) {
		this.formularioEntrada = formulario;
		
		//Comprobacion datos primera parte
		formularioEntrada.getLblBotonRegistro().addActionListener(e -> {
			System.out.println("ESTA VACIO");
			validarFormularioEntrada();
		});
		
		
		
	}

	public void validarFormularioEntrada() {
		//Comprueba preguntas sin responder
		boolean faltaRellenar = false;
		for(PanelTipoPreguntaUtil pregunta: formularioEntrada.getListaPreguntas()) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				faltaRellenar = true;
			}
		}
		if(faltaRellenar) {return;}
		//Comprueba checkbox
		if(formularioEntrada.getEstadoCivil().getSelectedItem() == "Seleccionar" || formularioEntrada.getGeneros().getSelectedItem() == "Seleccionar" ) {
			return;
		}
		
		//Comprueba contenidos invalidos
		for(PanelTipoPreguntaUtil pregunta: formularioEntrada.getListaPreguntas()) {
			try {
				ValidadorCadena.validarContenido(pregunta);
				formularioInformacion = new FormularioRegistroInformacionPuesto();
		    	formularioEntrada.dispose();
			} catch (Exception e) {
				// TODO Falta algo para indicar que falta llenar
			}	
		}
		
		
		
	}
}
