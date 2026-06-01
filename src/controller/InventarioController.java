package controller;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.ObservableElementList;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.DefaultEventComboBoxModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import controller.dialogs.InventarioFormController;
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
	}
	
	private void registrarListeners() {
		view.getBtnAdd().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				if(tablaInventarioDesplegada) {
					openFormComponente(null);
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
	            	openFormComponente(listaFiltradaComponentes.get(row)); //MUY IMPORTANTE, la lista filtrada controla la tabla
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
	            	new InventarioFormController(new InventarioDialog(null), listaFiltradaComponentes.get(row), false);
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
	
	
	private void openFormComponente(ComponenteIngredienteReceta componente) {
        InventarioFormController dialog = new InventarioFormController(new InventarioDialog(null), componente, true);
        
        if(dialog.saved) {
            ComponenteIngredienteReceta componenteSaved = dialog.getComponente();
            
            try {
				if(componente == null) {
					componenteService.saveComponente(componenteSaved);
				} else {
					componenteService.updateComponente(componenteSaved);
				}
			} catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
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
		
		listaFiltradaComponentes = new FilterList<>(componenteService.getListaSoloLectura(), editorFiltroComponentes);
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
		EventList<ComponenteIngredienteReceta> lista = new BasicEventList<>();
		lista.addAll(componenteService.getListaSoloLectura());
		
		//Quita elementos no inventariables
		for (int i = lista.size() - 1; i >= 0; i--) {
			
		    if (lista.get(i).isInventariable() == false) {
		        lista.remove(i);
		    }
		}
		NewMovementDialog i = new NewMovementDialog(null, new DefaultEventComboBoxModel<ComponenteIngredienteReceta>(lista));
		if(i.isMovimientoGuardado() == true) {
			try {
				inventarioService.guardarMovimientoInventario(i.getMovimientoInventario());
				JOptionPane.showMessageDialog(null, "Guardado");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ERROR: Movimiento no guardado, sin cambios");
			}	
			
		}
	}
	
	private void newProduccionMovement() {
		EventList<ComponenteIngredienteReceta> lista = new BasicEventList<>();
		lista.addAll(componenteService.getListaSoloLectura());
		
		//Mantiene recetas inventariables
		for (int i = lista.size() - 1; i >= 0; i--) {
		    System.out.println(lista.get(i).getNombre());
		    System.out.println(lista.get(i).isInventariable());
		    
		    if (!lista.get(i).isReceta() || lista.get(i).isInventariable() == false) {
		        lista.remove(i);
		    }
		}
		NewMovementDialog i = new NewMovementDialog(null, new DefaultEventComboBoxModel<ComponenteIngredienteReceta>(lista));
		if(i.isMovimientoGuardado() == true) {
			MovimientoInventario m = i.getMovimientoInventario();
			try {
				inventarioService.guardarProduccion(m.getComponente_id(), m.getCantidad(), m.getMotivo());
				JOptionPane.showMessageDialog(null, "Produccion exitosa");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Produccion no guardada: " + e.getMessage());
			}
		}
	}
	
	private void cambiarTabla() {
		tablaInventarioDesplegada = !tablaInventarioDesplegada;
		campoFiltroCompartido.setText("");
		actualizarListaFiltrosDisponibles();
		
		if(tablaInventarioDesplegada) {
			// Cambiar a tabla de componentes
			componenteService.cargarDatosDesdeBD();
			view.setTableModel(tableModelComponentes);
			view.setBtnCambiarTablaText("Ver movimientos");;
		} else {
			// Cambiar a tabla de movimientos
			loadMovimientoTable();
			view.setTableModel(tableModelMovimientos);
			view.setBtnCambiarTablaText("Ver Componentes");
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