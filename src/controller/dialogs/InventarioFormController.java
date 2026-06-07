package controller.dialogs;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import controller.PreguntaController;
import excepciones.invalidInput;
import models.ComponenteIngredienteReceta;
import services.ComponenteService;
import services.InventarioService;
import utilidades.ValidadorEntradasTexto;
import utilidades.views.PanelTipoPreguntaUtil;
import views.Dialog.InventarioDialog;


public class InventarioFormController {
	private InventarioDialog view;
	private ComponenteIngredienteReceta componente;
	private ComponenteService componenteService;
	private tipoEdicionForm tipoEdicion;
	
	public InventarioFormController(InventarioDialog view , ComponenteIngredienteReceta componente,ComponenteService componenteService ,tipoEdicionForm tipoEdicion) {
		this.view = view;
		this.componente = componente;
		this.tipoEdicion = tipoEdicion;
		this.componenteService = componenteService;
		
		// Configuración de títulos según el tipo de edición
		if(tipoEdicion == tipoEdicionForm.EDITAR) {
			view.setTitulo("Modificar componente");
		} else if(tipoEdicion == tipoEdicionForm.CREAR) {
			view.setTitulo("Crear componente");
		} else if(tipoEdicion == tipoEdicionForm.CREARRECETA) {
			//Siempre sera receta
			view.getEsReceta().setSelected(true);
			view.getEsReceta().setEnabled(false);
			view.setTitulo("Crear receta");
		} else if(tipoEdicion == tipoEdicionForm.VER) {
			view.setTitulo("Ver componente");
		}
	
		InitializeListeners();
		loadForm();
		
		// Añadido de pregunta controller a pregunta
		boolean esEditable = (tipoEdicion != tipoEdicionForm.VER);
		
		for(PanelTipoPreguntaUtil p: view.getListaDePreguntas()) {
			if(esEditable) {
				PreguntaController.registrarPanel(p);
			} else {
				p.setEditable(esEditable); 
			}
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
		// Si solo es visualización, cerramos el formulario directamente
		if(tipoEdicion == tipoEdicionForm.VER) {
			view.dispose();
			return;
		}
		
		view.setSubTitulo("");
		
		if (!checkForm()) {
			view.setSubTitulo("Faltan elementos por contestar");
			return;
		}
		
		String tipoComponente = "ingrediente";
		if(view.getEsRecetaBoolean()) {
			tipoComponente = "receta";
		}
		
		if(view.solicitarCierre("Guardar " + tipoComponente) == JOptionPane.YES_OPTION) {
			saveComponente();
			view.dispose();
		}
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
				p.setTextoError(e.getMessage());
			}
		}
		return listo;
	}
	
	private void saveComponente() {
		if(componente == null) {
			componente = new ComponenteIngredienteReceta();
			
			componente.setNombre(view.getNombre());
			componente.setEsReceta(view.getEsRecetaBoolean());
			componente.setTipoComponente(view.getTipoComponente());
			componente.setUnidadMedida(view.getUnidadMedida());
			componente.setCostoUnitario(Double.parseDouble(view.getCostoUnitario()));
			componente.setCaloriasPorUnidad(Double.parseDouble(view.getCaloriasPorUnidad()));
			//No creamos stock ni ID (Autogenerada) ni categoria ID (Se establece en menu)
			componente.setStockMinimoBloqueo(Double.parseDouble(view.getStockMinimoBloqueo()));
			componente.setStockMinimoAlerta(Double.parseDouble(view.getStockMinimoAlerta()));
			componente.setDisponibilidadManual(view.getDisponibilidadManual());
			componente.setEsInventariable(view.getEsInventariable());
			
			if(tipoEdicion == tipoEdicionForm.CREAR || tipoEdicion == tipoEdicionForm.CREARRECETA) {
				try {
					componenteService.saveComponente(componente);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(view, "Componente no pudo ser guardado ..." + e.getMessage());
				}
			} else {
				JOptionPane.showMessageDialog(view, "Error inesperado, no guardado");
			}
			
			return;
		}
		//Existente
		componente.setNombre(view.getNombre());
		componente.setEsReceta(view.getEsRecetaBoolean());
		componente.setTipoComponente(view.getTipoComponente());
		componente.setUnidadMedida(view.getUnidadMedida());
		componente.setCostoUnitario(Double.parseDouble(view.getCostoUnitario()));
		componente.setCaloriasPorUnidad(Double.parseDouble(view.getCaloriasPorUnidad()));
		//No creamos stock ni ID (Autogenerada) ni categoria ID (Se establece en menu)
		componente.setStockMinimoBloqueo(Double.parseDouble(view.getStockMinimoBloqueo()));
		componente.setStockMinimoAlerta(Double.parseDouble(view.getStockMinimoAlerta()));
		componente.setDisponibilidadManual(view.getDisponibilidadManual());
		componente.setEsInventariable(view.getEsInventariable());
		
		if(tipoEdicion == tipoEdicionForm.EDITAR) {
			try {
				componenteService.updateComponente(componente);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(view, "Componente no pudo ser actualizado ..." + e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(view, "Error inesperado, no actualizado");
		}
	}

	public ComponenteIngredienteReceta getComponente() {
		return componente;
	}

	public void setComponente(ComponenteIngredienteReceta componente) {
		this.componente = componente;
	}
	
}