package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import excepciones.invalidInput;
import models.ComponenteIngredienteReceta;
import utilidades.ValidadorEntradasTexto;
import utilidades.views.PanelTipoPreguntaUtil;
import views.Admin.InventarioDialog;

public class InventarioDialogController {
	private InventarioDialog view;
	private ComponenteIngredienteReceta componente;
	private boolean editable;
	protected boolean saved;
	
	public InventarioDialogController(InventarioDialog view , ComponenteIngredienteReceta componente, boolean editable) {
		this.view = view;
		this.componente = componente;
		this.editable = editable;
		
		if(componente != null) {
			view.setTitulo("Modificar componente");
		} else {
			view.setTitulo("Crear componente");
		}
	
		InitializeListeners();
		loadForm();
		
		//Anadido de pregunta controller a pregunta
		for(PanelTipoPreguntaUtil p: view.getListaDePreguntas()) {
			if(editable) {
				PreguntaController.registrarPanel(p);
			} else {
				p.setEditable(editable); 
			}
		}
		
		if(editable == false) {
			view.setTitulo("Ver componente");
		}
		
		view.setVisible(true);
	}
	
	private void InitializeListeners() {
		view.getBotonCerrar().addActionListener(e -> {
			if(view.solicitarCierre("Cerrar formulario?") == JOptionPane.YES_OPTION) {
				view.dispose();
			}
		});
		
		view.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	
		    	if(view.solicitarCierre("Cerrar formulario?") == JOptionPane.YES_OPTION) {
					view.dispose();
				}
		    }
		});		
		
		view.getBotonFinalizar().addActionListener(e -> {
			endFormulario();
		});
		
	}
	
	
	private void endFormulario() {
		if(editable == false) {
			view.dispose();
			return;
		}
		
		
		view.setSubTitulo("");
		
		if (!checkForm()) {
			view.setSubTitulo("Faltan elementos por contestar");
			return;
		}
		
		String tipoComponente = "ingrediente";
		
		if(view.getEsReceta()) {
			tipoComponente = "receta";
		}
		
		if(view.solicitarCierre("Guardar " + tipoComponente) == JOptionPane.YES_OPTION) {
			view.dispose();
		}
		
		saveUser();
		saved = true;
	}
	
	private void loadForm() {
		if(componente != null) {
			view.setNombre(componente.getNombre());
			view.setEsReceta(componente.isReceta());
			view.setTipoComponente(componente.getTipoComponente());
			view.setUnidadMedida(componente.getUnidadMedida());
			view.setCostoUnitario(String.valueOf(componente.getCostoUnitario()));
			view.setCaloriasPorUnidad(String.valueOf(componente.getCaloriasPorUnidad()));
			view.setStockMinimoAlerta(String.valueOf(componente.getStockMinimoAlerta()));
			view.setStockMinimoBloqueo(String.valueOf(componente.getStockMinimoBloqueo()));
			view.setDisponibilidadManual(componente.isDisponibilidadManual());
			view.setEsInventariable(componente.isInventariable());
		}
	}
	
	private boolean checkForm() {
		boolean listo = true;
		for(PanelTipoPreguntaUtil p: view.getListaDePreguntas()) {
			try {
				ValidadorEntradasTexto.validarContenido(p);
			} catch (invalidInput e) {
				listo = false;
				p.modificarLabelError(e.getMessage());
			}
		}
		return listo;
	}
	
	private void saveUser() {
		if(componente == null) {
			componente = new ComponenteIngredienteReceta();
			
			componente.setNombre(view.getNombre());
			componente.setEsReceta(view.getEsReceta());
			componente.setTipoComponente(view.getTipoComponente());
			componente.setUnidadMedida(view.getUnidadMedida());
			componente.setCostoUnitario(Double.parseDouble(view.getCostoUnitario()));
			componente.setCaloriasPorUnidad(Double.parseDouble(view.getCaloriasPorUnidad()));
			//No creamos stock ni ID (Autogenerada) ni categoria ID (Se establece en menu)
			componente.setStockMinimoBloqueo(Double.parseDouble(view.getStockMinimoBloqueo()));
			componente.setStockMinimoAlerta(Double.parseDouble(view.getStockMinimoAlerta()));
			componente.setDisponibilidadManual(view.getDisponibilidadManual());
			componente.setEsInventariable(view.getEsInventariable());
			return;
		}
		//Existente
		componente.setNombre(view.getNombre());
		componente.setEsReceta(view.getEsReceta());
		componente.setTipoComponente(view.getTipoComponente());
		componente.setUnidadMedida(view.getUnidadMedida());
		componente.setCostoUnitario(Double.parseDouble(view.getCostoUnitario()));
		componente.setCaloriasPorUnidad(Double.parseDouble(view.getCaloriasPorUnidad()));
		//No creamos stock ni ID (Autogenerada) ni categoria ID (Se establece en menu)
		componente.setStockMinimoBloqueo(Double.parseDouble(view.getStockMinimoBloqueo()));
		componente.setStockMinimoAlerta(Double.parseDouble(view.getStockMinimoAlerta()));
		componente.setDisponibilidadManual(view.getDisponibilidadManual());
		componente.setEsInventariable(view.getEsInventariable());
	}

	public ComponenteIngredienteReceta getComponente() {
		return componente;
	}

	public void setComponente(ComponenteIngredienteReceta componente) {
		this.componente = componente;
	}
	
	
}
