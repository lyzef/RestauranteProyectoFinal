package controller.dialogs;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.DefaultEventComboBoxModel;
import controller.PreguntaController;
import excepciones.invalidInput;
import models.Categoria;
import models.ComponenteIngredienteReceta;
import models.Platillo;
import models.Platillo.Emblema;
import services.CategoriaService;
import services.ComponenteService;
import services.PlatilloService;
import services.VentaProductoService;
import utilidades.GeneradorIconos;
import utilidades.ValidadorEntradasTexto;
import utilidades.views.PanelTipoPreguntaUtil;
import views.Dialog.PlatilloDialog;

public class PlatilloDialogControlador {
	//Servicios
	PlatilloService platilloService;
	ComponenteService componenteService;
	CategoriaService categoriaService;
	
	Point tamanoIconos = new Point(75, 75);
	
	//Propios del controlador
	PlatilloDialog view;
	EventList<ImageIcon> eventListImagenes;
	tipoEdicionForm tipoEdicion;
	Platillo platillo;

	public PlatilloDialogControlador(PlatilloService platilloService, ComponenteService componenteService,
			CategoriaService categoriaService, PlatilloDialog platilloDialog,Platillo platillo,tipoEdicionForm tipoEdicion ) {
		this.platilloService = platilloService;
		this.componenteService = componenteService;
		this.categoriaService = categoriaService;
		this.view = platilloDialog;
		this.tipoEdicion = tipoEdicion;
		this.platillo = platillo;


		crearComboBoxs();
		loadForm();
		
		if(tipoEdicion == tipoEdicionForm.CREAR ) {
			conectarPreguntasControlador();
		} else if (tipoEdicion == tipoEdicionForm.EDITAR && platillo != null) {
			conectarPreguntasControlador();
		} else {
			modoSoloVer();
		}

		addListeners();
		view.setVisible(true);
		
	}
	
	private void conectarPreguntasControlador() {
		for(PanelTipoPreguntaUtil p : view.getListaDePreguntas()) {
			PreguntaController.registrarPanel(p);
		}
		
		  
	}
	
	private void modoSoloVer() {
		view.getBotonFinalizar().setVisible(false);
		view.desactivarEntradas();
	}
	
	private void addListeners() {
		view.getBotonFinalizar().addActionListener(e -> {
			if(checkForm() && view.solicitarCierre("Terminar formulario?") == JOptionPane.YES_OPTION) {
				savePlatillo();
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
		
		view.getBotonSubirImagen().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				new GeneradorIconos().seleccionarYGuardarImagen();
				ImageIcon[] imagenes = new GeneradorIconos().obtenerTodasLasImagenes(tamanoIconos.x,tamanoIconos.y);
				eventListImagenes.clear();
				eventListImagenes.addAll(Arrays.asList(imagenes));
			}
		});
		
	}
	
	private void crearComboBoxs() {
		ImageIcon[] imagenes = new GeneradorIconos().obtenerTodasLasImagenes(tamanoIconos.x,tamanoIconos.y);
		eventListImagenes = new BasicEventList<ImageIcon>();
		eventListImagenes.addAll(Arrays.asList(imagenes));
		
		
		SortedList<Categoria> listaCategoria = new SortedList<>(
				 categoriaService.getListaSoloLectura(), 
			    Comparator.comparing(Categoria::getNombre) 
			);
		
		DefaultEventComboBoxModel<Categoria> categoriaModel = new DefaultEventComboBoxModel<Categoria>(listaCategoria); 
		
		SortedList<ComponenteIngredienteReceta> listaComponentes = new SortedList<>(
				componenteService.getAllRecetas(), 
			    Comparator.comparing(ComponenteIngredienteReceta::getNombre) 
			);
		DefaultEventComboBoxModel<ComponenteIngredienteReceta> recetaModel = new DefaultEventComboBoxModel<ComponenteIngredienteReceta>(
				listaComponentes); 
		
		view.asignarComboBoxs(new DefaultEventComboBoxModel<ImageIcon>(eventListImagenes), categoriaModel, recetaModel);
	}
	
	private void loadForm() {		
	    if(platillo != null) {
	        // Buscar la imagen en la lista existente en lugar de crear una nueva
	        ImageIcon imgEncontrada = null;
	        for (ImageIcon icon : eventListImagenes) {
	            if (icon.getDescription() != null && 
	                icon.getDescription().equals(platillo.getImagenUrl())) {
	                imgEncontrada = icon;
	                break;
	            }
	        }
	        
	        if (imgEncontrada != null) {
	            view.getImagenComboBox().setSelectedItem(imgEncontrada);
	        } else {
	            JOptionPane.showMessageDialog(view, 
	                "Imagen no existente en carpeta recursos, seleccionar nueva");
	        }
	        
	        view.getCategoriasComboBox().setSelectedItem(
	            categoriaService.getCategoriaById(platillo.getCategoriaId()));
	        view.getRecetaComboBox().setSelectedItem(
	            componenteService.getComponenteById(platillo.getComponenteId()));
	        view.setDescripcion(platillo.getDescripcion());
	        view.setPrecio(platillo.getPrecioVenta().toString());
	        view.getEmblemaComboBox().setSelectedItem(platillo.getEmblema());
	    }
	}
	
	private boolean checkForm() {
		boolean listo = true;
		
		//Checar combo box no estan vacios
		if(view.getImagenComboBox().getSelectedItem() == null || view.getCategoriasComboBox().getSelectedItem() == null ||
				view.getRecetaComboBox().getSelectedItem() == null) {
			view.setSubTitulo("Espacios sin seleccioanr");
			listo = false;
		}
		
		for(PanelTipoPreguntaUtil p: view.getListaDePreguntas()) {
			try {
				ValidadorEntradasTexto.validarContenido(p);
			} catch (invalidInput e) {
				listo = false;
				view.setSubTitulo("Espacios sin rellenar");
				p.setTextoError(e.getMessage());
			}
		}
		return listo;
	}
	
	private void savePlatillo() {
		if(platillo == null){
			platillo = new Platillo();
		}
		ImageIcon img = (ImageIcon)(view.getImagenComboBox().getSelectedItem());
		platillo.setImagenUrl(img.getDescription());
		platillo.setCategoriaId(((Categoria) view.getCategoriasComboBox().getSelectedItem()).getId());
		platillo.setCategoriaNombre(((Categoria) view.getCategoriasComboBox().getSelectedItem()).getNombre());
		platillo.setDescripcion(view.getDescripcion());
		platillo.setPrecioVenta(Double.parseDouble(view.getPrecio()));
		platillo.setComponenteId(((ComponenteIngredienteReceta) view.getRecetaComboBox().getSelectedItem()).getId());
		platillo.setComponenteNombre(((ComponenteIngredienteReceta) view.getRecetaComboBox().getSelectedItem()).getNombre());
		platillo.setEmblema((Emblema) view.getEmblemaComboBox().getSelectedItem());
		
		try {
			if(tipoEdicion == tipoEdicionForm.CREAR) {
				platilloService.savePlatillo(platillo);
			} else if(tipoEdicion == tipoEdicionForm.EDITAR) {
				platilloService.updatePlatillo(platillo);
			}
		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null, "platillo NO guardado");
		}
		
	}
	
}
