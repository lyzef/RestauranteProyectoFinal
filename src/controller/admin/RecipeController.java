package controller.admin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import controller.dialogs.RecetaFormController;
import models.ComponenteIngredienteReceta;
import services.CalculoRecetaService;
import services.ComponenteService;
import services.EstructuraRecetaService;
import tableFormat.ComponenteTableFormat;
import tableFormat.filtros.ComponenteTextFilterator;
import tableFormat.filtros.ComponenteTextFilterator.TipoFiltroComponente;
import views.Admin.RecipeView;
import views.Dialog.RecetaDialog;

public class RecipeController {
	RecipeView view;
	EstructuraRecetaService estructuraRecetaService;
	ComponenteService componenteService;
	CalculoRecetaService calculoRecetaService;
	
	// Componentes
    private AdvancedTableModel<ComponenteIngredienteReceta> tableModelComponentes; // getElementAt(row) no falla
    ComponenteTextFilterator textFilteratorComponentes;
    private FilterList<ComponenteIngredienteReceta> listaFiltradaComponentes;
    private MatcherEditor<ComponenteIngredienteReceta> editorFiltroComponentes;
	
	public RecipeController(RecipeView view,EstructuraRecetaService estructuraRecetaService,
			ComponenteService componenteService, CalculoRecetaService calculoRecetaService) {
		this.view = view;
		this.estructuraRecetaService = estructuraRecetaService;
		this.componenteService = componenteService;
		this.calculoRecetaService = calculoRecetaService;
		
		crearTableModelComponente();
		crearListeners();
	}
	
	private void crearListeners() {
		//Actualiza el filtro del textFilterator
		view.getListaFiltros().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		        	textFilteratorComponentes.setFiltroActivo(TipoFiltroComponente.fromString(view.getFiltroSeleccionado()));
		        }
		    }
		});
		
		view.getBtnEdit().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRow();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un elemento");
	                return;
	            }
            	openForm(tableModelComponentes.getElementAt(row)); //MUY IMPORTANTE, la lista filtrada y table model controla la tabla
	           
		    }
		});
		
		view.getBtnVer().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRow();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un elemento");
	                return;
	            }
            	verReceta(tableModelComponentes.getElementAt(row)); //MUY IMPORTANTE, la lista filtrada y table model controla la tabla
	           
		    }
		});
		
		view.getLblRefrescarTabla().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					estructuraRecetaService.cargarDatosDesdeBD();
					JOptionPane.showMessageDialog(view, "Tablas cargadas");
				} catch (Exception e1) {
					System.err.println(e1);
					JOptionPane.showMessageDialog(view, "Tablas no cargadas");
				}
			}
		});
	}
	
	private void crearTableModelComponente() {
		//Matcher fijo
		FilterList<ComponenteIngredienteReceta> listaSoloRecetas = new FilterList<>(
		    componenteService.getListaSoloLectura(),
		    new Matcher<ComponenteIngredienteReceta>() {
		        @Override
		        public boolean matches(ComponenteIngredienteReceta item) {
		            return item != null && item.isReceta();
		        }
		    }
		);
		textFilteratorComponentes = new ComponenteTextFilterator(); 
		
		editorFiltroComponentes = new TextComponentMatcherEditor<>(
		    view.getTextoBuscador(), 
		    textFilteratorComponentes
		);
		
		//Crear lista filtrada con el filtro de recetas y del JField
		listaFiltradaComponentes = new FilterList<>(listaSoloRecetas, editorFiltroComponentes);
		
		tableModelComponentes = GlazedListsSwing.eventTableModelWithThreadProxyList(
		    listaFiltradaComponentes, 
		    new ComponenteTableFormat()
		);     
		
		//Asignacion de filtros
		view.setListaFiltrosBusqueda(TipoFiltroComponente.getTodasLasColumnas());
		view.setTableModel(tableModelComponentes);
    	
	}
	
	private void openForm(ComponenteIngredienteReceta receta) {
		RecetaFormController form =  new RecetaFormController(componenteService.getListaSoloLectura(), 
				estructuraRecetaService.getListaSoloLectura(),
				new RecetaDialog(null),receta,false,calculoRecetaService);
		
		try {
			if(form.getHijos().isEmpty()) {
				estructuraRecetaService.deleteHijos(receta.getId());
			} else {
				estructuraRecetaService.saveHijos(form.getHijos());
			}
			componenteService.updateComponente(receta);
		} catch (Exception e) {
			new JOptionPane().showMessageDialog(null, "Erorr al guardar: " + e.getMessage());
		}
		
	}
	
	private void verReceta(ComponenteIngredienteReceta receta) {
		RecetaFormController form =  new RecetaFormController(componenteService.getListaSoloLectura(), 
				estructuraRecetaService.getListaSoloLectura(),
				new RecetaDialog(null),receta,true,calculoRecetaService);
	}
}	
