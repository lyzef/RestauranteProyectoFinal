package controller.dialogs;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JOptionPane;

import controller.PreguntaController;
import excepciones.invalidInput;
import models.Categoria;
import services.CategoriaService;
import utilidades.ValidadorEntradasTexto;
import utilidades.views.PanelTipoPreguntaUtil;
import views.Dialog.CategoriaDialog;

public class CategoriaFormController {
	public static String EDITAR = "EDITAR";
	public static String CREAR = "CREAR";
	public static String VER = "VER";
	
	private String modo;
	private CategoriaDialog view;
	private Categoria categoria;
	CategoriaService categoriaService;
	
	public CategoriaFormController(CategoriaDialog categoriaDialog,CategoriaService categoriaService,String modo,Categoria categoria ) {
		this.view = categoriaDialog;
		this.categoria = categoria;
		this.categoriaService = categoriaService;
		this.modo = modo;
	
		cargarCategoria();
		
		if(modo == CREAR) {
			conectarPreguntasAsuControlador(view.getListaPreguntas());
		} else if (modo == EDITAR && categoria != null) {
			conectarPreguntasAsuControlador(view.getListaPreguntas());
		} else {
			modoSoloVer();
		}
		
		addListeners();
		
		view.setVisible(true);
	}
	
	private void addListeners() {
		view.getBotonFinalizar().addActionListener(e -> {
			if(comprobarForm() && view.solicitarCierre("Terminar formulario?") == JOptionPane.YES_OPTION) {
				guardarCategoria();
				view.dispose();
			}
			
		});
		
		view.getBotonCerrar().addActionListener(e -> {
			view.dispose();
		});
		
		view.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	if(view.solicitarCierre("Cerrar formulario?") == JOptionPane.YES_OPTION) {
					view.dispose();
				}
		    }
		});	
		
	}
	
	
	/**
	 * Conecta las clases preguntas con su controlador que se encarga de moderar los caracteres ingresados segun el contexto de la pregunta
	 */
	private void conectarPreguntasAsuControlador(List <PanelTipoPreguntaUtil> preguntas ) {
		for(PanelTipoPreguntaUtil p : preguntas) {
			PreguntaController.registrarPanel(p);
		}
	}
	
	/**
	 * Modo solo ver
	 */
	private void modoSoloVer() {
		view.getBotonFinalizar().setVisible(false);
		view.desactivarEntradas();
	}
	
	private void cargarCategoria() {
		if(categoria != null) {
			view.setNombre(categoria.getNombre());
			view.setDescripcion(categoria.getDescripcion());
			view.setCaracteristicaActiva(categoria.getActivo());
		}
	}
	
	private boolean comprobarForm() {
		boolean listo = true;
		
		for(PanelTipoPreguntaUtil pr : view.getListaPreguntas()) {
			try {
				ValidadorEntradasTexto.validarContenido(pr);
			} catch (invalidInput e) {
				view.setSubTituloText("Faltan elementos por contestar");
				listo = false;
			}
		}
		
		return listo;
	}
	
	private void guardarCategoria() {
		if(categoria == null) {
			categoria = new Categoria();
			
		} 
		categoria.setNombre(view.getNombreText());
		categoria.setDescripcion(view.getDescripcionText());
		categoria.setActivo(view.getCaracteristicaActiva());
		
		
		
		
		//Guardando en bd
		try {
			if(modo == CREAR) {
				categoriaService.saveCategoria(categoria);
			} else if(modo == EDITAR) {
				categoriaService.updateCategoria(categoria);
			}
		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null, "Categoria NO guardada");
		}
		
	}
	
	
}
