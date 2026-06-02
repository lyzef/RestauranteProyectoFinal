package controller.admin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import ca.odell.glazedlists.swt.TableComparatorChooser;
import controller.dialogs.CategoriaFormController;
import controller.dialogs.InventarioFormController;
import controller.dialogs.PlatilloDialogControlador;
import controller.dialogs.tipoEdicionForm;
import models.Categoria;
import models.MovimientoInventario;
import models.Platillo;
import services.CategoriaService;
import services.ComponenteService;
import services.PlatilloService;
import tableFormat.CategoriaTableFormat;
import tableFormat.ComponenteTableFormat;
import tableFormat.MovimientoInventariotTableFormat;
import tableFormat.PlatillosTableFormat;
import tableFormat.filtros.ComponenteTextFilterator;
import tableFormat.filtros.PlatilloTextFilterator;
import tableFormat.filtros.ComponenteTextFilterator.TipoFiltroComponente;
import tableFormat.filtros.PlatilloTextFilterator.TipoFiltroPlatillo;
import views.Admin.MenuAdminView;
import views.Dialog.CategoriaDialog;
import views.Dialog.InventarioDialog;
import views.Dialog.PlatilloDialog;

public class MenuAdminController {
	MenuAdminView view;
	
	//Servicios
	ComponenteService componenteService;
	CategoriaService categoriaService;
	PlatilloService platilloService;
	
	//Tabla categoria
    private AdvancedTableModel<Categoria> tableModelCategorias;
    private SortedList<Categoria> listaOrdenadaCategorias;
	
	//Tabla platillos 
    private AdvancedTableModel<Platillo> tableModelPlatillos;
    PlatilloTextFilterator TextFilteratorPlatillos;
    private FilterList<Platillo> listaFiltradaPlatillos;
    private MatcherEditor<Platillo> editorFiltroPlatillos;
	
	public MenuAdminController(MenuAdminView view, CategoriaService categoriaService, PlatilloService platilloService, ComponenteService componenteService) {
		super();
		this.view = view;
		this.categoriaService = categoriaService;
		this.platilloService = platilloService;
		this.componenteService = componenteService;
		
		crearTablaCategoria();
		crearTablaPlatillo();
		addListeners();
	}
	
	private void addListeners() {
		view.getBtnAgregarCategoria().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				new CategoriaFormController(new CategoriaDialog(null),categoriaService,CategoriaFormController.CREAR,null);
			}
		});
		
		view.getBtnEditarCategoria().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRowCategoria();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un elemento");
	                return;
	            }
				new CategoriaFormController(new CategoriaDialog(null),categoriaService,CategoriaFormController.EDITAR,
						tableModelCategorias.getElementAt(row));
			}
		});
		
		view.getBtnVerCategoria().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRowCategoria();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un elemento");
	                return;
	            }
				new CategoriaFormController(new CategoriaDialog(null),categoriaService,CategoriaFormController.VER,
						tableModelCategorias.getElementAt(row));
			}
		});
		
		view.getBtnEliminarCategoria().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRowCategoria();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un elemento");
	                return;
	            }
				try {
					categoriaService.deleteCategoria(tableModelCategorias.getElementAt(row));
				} catch (Exception e1) {
					System.err.println(e1);
					JOptionPane.showMessageDialog(view, "Categoria no pudo ser borrada");
				}
			}
		});
		
		view.getBtnAgregarPlatillo().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				new PlatilloDialogControlador(platilloService, componenteService, categoriaService, new PlatilloDialog(null),null,tipoEdicionForm.CREAR);
			}
		});
		
		view.getBtnEditarPlatillo().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRowPlatillo();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un elemento");
	                return;
	            }
	            
	            new PlatilloDialogControlador(platilloService, componenteService, categoriaService, new PlatilloDialog(null),
	            		tableModelPlatillos.getElementAt(row),tipoEdicionForm.EDITAR);		    
			}
		});
		
		view.getBtnEliminarPlatillo().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRowPlatillo();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un elemento");
	                return;
	            }
	            
	            try {
					platilloService.deletePlatillo(tableModelPlatillos.getElementAt(row));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(view, "Platillo no eliminado");
					System.out.println("Objeto no eliminado ... " + e);
				}
			}
		});
		
		view.getBtnVerPlatillo().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRowPlatillo();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un elemento");
	                return;
	            }
	            
	            new PlatilloDialogControlador(platilloService, componenteService, categoriaService, new PlatilloDialog(null),
	            		tableModelPlatillos.getElementAt(row),tipoEdicionForm.VER);		    }
		});
		
		view.getBarraBusquedaConFiltroPlatillos().getListaFiltros().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		        	TipoFiltroPlatillo tipoFiltro = TipoFiltroPlatillo.fromString(
				        	view.getBarraBusquedaConFiltroPlatillos().getFiltroSeleccionado());
					TextFilteratorPlatillos.setFiltroActivo(tipoFiltro);
		        }
		    }
		});
		
		view.getLblRefrescarTabla().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					platilloService.loadPlatillosFromDatabase();
					categoriaService.loadCategoriasFromDataBase();
					JOptionPane.showMessageDialog(view, "Tablas cargadas");
				} catch (Exception e1) {
					System.err.println(e1);
					JOptionPane.showMessageDialog(view, "Tablas no cargadas");
				}
			}
		});
		
	}
		
	private void crearTablaCategoria() {
	    listaOrdenadaCategorias = new SortedList<>(categoriaService.getListaSoloLectura(), null);
	    tableModelCategorias =  GlazedListsSwing.eventTableModel(
	            listaOrdenadaCategorias, 
	            new CategoriaTableFormat());

        view.setTableModelCategorias(tableModelCategorias);
	}
	
	private void crearTablaPlatillo() {
		TextFilteratorPlatillos = new PlatilloTextFilterator();
		
		// Crear editor de filtro específico para componentes
		editorFiltroPlatillos = new TextComponentMatcherEditor<>(
			view.getBarraBusquedaConFiltroPlatillos().getTextFieldTabla(), 
			TextFilteratorPlatillos
		);
		EventList<Platillo> p = platilloService.getListaSoloLectura();
		
		listaFiltradaPlatillos = new FilterList<>(p, editorFiltroPlatillos);
    	tableModelPlatillos = GlazedListsSwing.eventTableModel(
    			listaFiltradaPlatillos, 
    		new PlatillosTableFormat()
    	);   
    	view.getBarraBusquedaConFiltroPlatillos().setListaFiltros(
    			TipoFiltroPlatillo.getTodasLasColumnas());
    	view.setTableModelPlatillos(tableModelPlatillos);
	}
	
}
