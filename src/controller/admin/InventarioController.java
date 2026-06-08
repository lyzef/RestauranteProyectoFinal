package controller.admin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.DefaultEventComboBoxModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import controller.dialogs.InventarioFormController;
import controller.dialogs.NewMovementDialogController;
import controller.dialogs.tipoEdicionForm;
import models.ComponenteIngredienteReceta;
import models.MovimientoInventario;
import models.MovimientoInventario.tipoMovimiento;
import models.User;
import repository.InventarioRepository;
import services.ComponenteService;
import services.InventarioService;
import tableFormat.ComponenteTableFormat;
import tableFormat.MovimientoInventariotTableFormat;
import tableFormat.UserTableFormat;
import tableFormat.filtros.ComponenteTextFilterator;
import tableFormat.filtros.MovimientoTextFilterator;
import tableFormat.filtros.MovimientoTextFilterator.TipoFiltroMovimiento;
import tableFormat.filtros.ComponenteTextFilterator.TipoFiltroComponente;
import views.Admin.InventoryView;
import views.Dialog.UserFormDialog;
import views.Dialog.InventarioDialog;
import views.Dialog.NewMovementDialog;

public class InventarioController {
	private InventoryView view;
	private InventarioRepository repo;
		
	boolean tablaInventarioDesplegada = true;
	
	// Componentes
    private AdvancedTableModel<ComponenteIngredienteReceta> tableModelComponentes; // getElementAt(row) no falla
    ComponenteTextFilterator textFilteratorComponentes;
    private FilterList<ComponenteIngredienteReceta> listaFiltradaComponentes;
    private MatcherEditor<ComponenteIngredienteReceta> editorFiltroComponentes;
    
    //Movimiento de inventario
    private AdvancedTableModel<MovimientoInventario> tableModelMovimientos;
    MovimientoTextFilterator TextFilteratorMovimientos;
    private FilterList<MovimientoInventario> listaFiltradaMovimientos;
    private MatcherEditor<MovimientoInventario> editorFiltroMovimientos;
    
    private JTextField campoFiltroCompartido;
    
    //Servicios 
	private ComponenteService componenteService;
	private InventarioService inventarioService;
    
	public InventarioController(InventoryView view, ComponenteService componenteService,InventarioService inventarioService) {
		this.view = view;
		this.repo = new InventarioRepository();
		this.inventarioService = inventarioService;
		this.componenteService = componenteService;
		campoFiltroCompartido = view.getTextFieldTabla();
		
		// Inicializar ambos modelos de tabla con sus propios filtros
		inicializarModelos();
		
		registrarListeners();
		
		inicializarEstadisticas();
	}
	
	private void inicializarEstadisticas() {
		try {
			view.moduloItemsBajoStock.setValor(Integer.toString(repo.getItemsConBajoStock()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void inicializarModelos() {
		tableModelComponentes = crearTableModelComponente();
		tableModelMovimientos = crearTableModelMovimientoInventario();
		
		// Mostrar tabla inicial (componentes)
		view.setTableModel(tableModelComponentes);
		actualizarListaFiltrosDisponibles();
	}
	
	private void registrarListeners() {
		view.getBtnAdd().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				if(tablaInventarioDesplegada) {
					new InventarioFormController(new InventarioDialog(null), null,componenteService,tipoEdicionForm.CREAR);
				} else {
					JOptionPane.showMessageDialog(view, "Use el botón de nuevo movimiento para agregar movimientos");
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
	            
	            if(tablaInventarioDesplegada) {
	            	new InventarioFormController(new InventarioDialog(null), listaFiltradaComponentes.get(row),componenteService,tipoEdicionForm.EDITAR); //MUY IMPORTANTE, la lista filtrada controla la tabla
	            } else {
	            	JOptionPane.showMessageDialog(view, "Los movimientos no se pueden editar");
	            }
		    }
		});
		
		view.getBtnDelete().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRow();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un elemento");
	                return;
	            }
	            
	            if(tablaInventarioDesplegada) {
	            	try {
						componenteService.deleteComponente(listaFiltradaComponentes.get(row));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(view, "Objeto no eliminado");
						System.out.println("Objeto no eliminado ... " + e);
					}
	            } else {
	            	JOptionPane.showMessageDialog(view, "Los movimientos no se pueden eliminar");
	            }
		    }
		});
		
		view.getBtnSee().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRow();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un elemento");
	                return;
	            }
	            
	            if(tablaInventarioDesplegada) {
	            	new InventarioFormController(new InventarioDialog(null), listaFiltradaComponentes.get(row),componenteService,tipoEdicionForm.VER);
	            }
		    }
		});
		
		// Listener para Jpop de filtros
		view.getListaFiltros().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		        	actualizarFiltroActivo();
		        }
		    }
		});
		
		// Listener para el campo de texto compartido
		campoFiltroCompartido.getDocument().addDocumentListener(new DocumentListener() {
		    public void changedUpdate(DocumentEvent e) { filtrar(); }
		    public void removeUpdate(DocumentEvent e) { filtrar(); }
		    public void insertUpdate(DocumentEvent e) { filtrar(); }
		    
		    private void filtrar() {
		        view.actualizarTabla();
		    }
		});
		
		view.getBtnMovimientoInventario().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
		        newInventoryMovement();
		    }
		});
		
		view.getBtnRegistrarProduccion().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				newProduccionMovement();
		    }
		});
		
		view.getBtnCambiarTabla().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
		        cambiarTabla();
		        inicializarEstadisticas();
		    }
		});
	}
	
	private void actualizarFiltroActivo() {
		String filtroSeleccionado = view.getFiltroSeleccionado();
		
		if(tablaInventarioDesplegada) {
			TipoFiltroComponente tipoFiltro = TipoFiltroComponente.fromString(filtroSeleccionado);
			textFilteratorComponentes.setFiltroActivo(tipoFiltro);
		} else {
			TipoFiltroMovimiento tipoFiltro = TipoFiltroMovimiento.fromString(filtroSeleccionado);
			TextFilteratorMovimientos.setFiltroActivo(tipoFiltro); 
		}

	}
	
	private void loadComponentesTable() {
		try {
			componenteService.cargarDatosDesdeBD();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "Error al cargar componentes: " + e.getMessage());
		}
	}
	
	private void loadMovimientoTable() {
		try {
			inventarioService.cargarMovimientos();
                       
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al cargar movimientos: " + ex.getMessage());
        }
	}
	
	private AdvancedTableModel<ComponenteIngredienteReceta> crearTableModelComponente() {
		textFilteratorComponentes = new ComponenteTextFilterator();
		
		// Crear editor de filtro específico para componentes
		editorFiltroComponentes = new TextComponentMatcherEditor<>(
			campoFiltroCompartido, 
			textFilteratorComponentes
		);
		
		FilterList<ComponenteIngredienteReceta> listaSoloIngredientes = new FilterList<>(
			    componenteService.getListaSoloLectura(),
			    new Matcher<ComponenteIngredienteReceta>() {
			        @Override
			        public boolean matches(ComponenteIngredienteReceta item) {
			            return item != null && !item.isReceta();
			        }
			    }
		);
		
		listaFiltradaComponentes = new FilterList<>(listaSoloIngredientes, editorFiltroComponentes);
    	tableModelComponentes = GlazedListsSwing.eventTableModelWithThreadProxyList(
    		listaFiltradaComponentes, 
    		new ComponenteTableFormat()
    	);    	
    	return tableModelComponentes;
	}
	
	private AdvancedTableModel<MovimientoInventario> crearTableModelMovimientoInventario() {
		EventList<MovimientoInventario> eventListMovimientos = inventarioService.getListaSoloLectura();
		TextFilteratorMovimientos = new MovimientoTextFilterator();
		
		// Crear editor de filtro específico para movimientos
		editorFiltroMovimientos = new TextComponentMatcherEditor<>(
			campoFiltroCompartido, 
			TextFilteratorMovimientos
		);
		
		listaFiltradaMovimientos = new FilterList<>(eventListMovimientos, editorFiltroMovimientos);
		
    	tableModelMovimientos = GlazedListsSwing.eventTableModelWithThreadProxyList(
    			listaFiltradaMovimientos, 
    		new MovimientoInventariotTableFormat()
    	);    	
    	
    	return tableModelMovimientos;
	}
	  
	private void newInventoryMovement() {
		new NewMovementDialogController(inventarioService, componenteService, new NewMovementDialog(null),false);
		
	}
	
	private void newProduccionMovement() {
		new NewMovementDialogController(inventarioService, componenteService, new NewMovementDialog(null),true);
	}
	
	private void cambiarTabla() {
		tablaInventarioDesplegada = !tablaInventarioDesplegada;
		campoFiltroCompartido.setText("");
		actualizarListaFiltrosDisponibles();
		
		if(tablaInventarioDesplegada) {
			// Cambiar a tabla de componentes
			loadComponentesTable();
			view.setTableModel(tableModelComponentes);
			view.setBtnCambiarTablaText("Ver movimientos");;
			view.setTituloTabla("Tabla de ingrediente");
		} else {
			// Cambiar a tabla de movimientos
			loadMovimientoTable();
			view.setTableModel(tableModelMovimientos);
			view.setBtnCambiarTablaText("Ver Componentes");
			view.setTituloTabla("Tabla de movimientos");
		}
		
	}
	
	private void actualizarListaFiltrosDisponibles() {
		if(tablaInventarioDesplegada) {
			view.setFiltrosBusqueda(TipoFiltroComponente.getTodasLasColumnas());
		} else {
			view.setFiltrosBusqueda(TipoFiltroMovimiento.getTodasLasColumnas()); 
		}
	}
}