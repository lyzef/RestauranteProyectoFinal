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
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.DefaultEventComboBoxModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import models.ComponenteIngredienteReceta;
import models.MovimientoInventario;
import models.MovimientoInventario.tipoMovimiento;
import models.User;
import repository.InventarioRepository;
import tableFormat.ComponenteTableFormat;
import tableFormat.MovimientoInventariotTableFormat;
import tableFormat.UserTableFormat;
import tableFormat.filtros.ComponenteTextFilterator;
import tableFormat.filtros.MovimientoTextFilterator;
import tableFormat.filtros.MovimientoTextFilterator.TipoFiltroMovimiento;
import tableFormat.filtros.ComponenteTextFilterator.TipoFiltroComponente;
import views.Admin.InventoryView;
import views.Dialog.FormularioDialog;
import views.Dialog.InventarioDialog;
import views.Dialog.NewMovementDialog;

public class InventarioController {
	private InventoryView view;
	private InventarioRepository repo;
		
	boolean tablaInventarioDesplegada = true;
	
	// Componentes
	private EventList<ComponenteIngredienteReceta> eventListComponentes;
    private AdvancedTableModel<ComponenteIngredienteReceta> tableModelComponentes;
    ComponenteTextFilterator textFilteratorComponentes;
    private FilterList<ComponenteIngredienteReceta> listaFiltradaComponentes;
    private MatcherEditor<ComponenteIngredienteReceta> editorFiltroComponentes;
    
    //Movimiento de inventario
    private EventList<MovimientoInventario> eventListMovimientos;
    private AdvancedTableModel<MovimientoInventario> tableModelMovimientos;
    MovimientoTextFilterator TextFilteratorMovimientos;
    private FilterList<MovimientoInventario> listaFiltradaMovimientos;
    private MatcherEditor<MovimientoInventario> editorFiltroMovimientos;
    
    private JTextField campoFiltroCompartido;
    
	public InventarioController(InventoryView view) {
		this.view = view;
		this.repo = new InventarioRepository();
		
		campoFiltroCompartido = view.getTextFieldTabla();
		
		// Inicializar ambos modelos de tabla con sus propios filtros
		inicializarModelos();
		
		registrarListeners();
		
		loadComponenteTable();
		
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
						repo.deleteComponente(listaFiltradaComponentes.get(row).getId());
						eventListComponentes.remove(row);
					} catch (Exception e1) {
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
	            	new InventarioDialogController(new InventarioDialog(null), listaFiltradaComponentes.get(row), false);
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
        InventarioDialogController dialog = new InventarioDialogController(new InventarioDialog(null), componente, true);
        
        if(dialog.saved) {
            ComponenteIngredienteReceta componenteSaved = dialog.getComponente();
            
            try {
				if(componente == null) {
					repo.saveComponente(componenteSaved);
					eventListComponentes.add(componenteSaved);
				} else {
					int row = view.getSelectedRow();
					boolean updated = repo.updateComponente(componenteSaved);
					if(updated) {
						eventListComponentes.set(row, componenteSaved);
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
        }
	}
	
	private void loadComponenteTable() {
		try {
            List<ComponenteIngredienteReceta> componentes = repo.getComponentes();
            System.out.println("Componente cargado");
            eventListComponentes.clear();
            eventListComponentes.addAll(componentes);
            System.out.println("Componentes agregados");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al cargar componentes: " + ex.getMessage());
        }
	}
	
	private void loadMovimientoTable() {
		try {
            List<MovimientoInventario> movimientos = repo.getMovimientosInventario();
            
            eventListMovimientos.clear();
            eventListMovimientos.addAll(movimientos);
                       
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al cargar movimientos: " + ex.getMessage());
        }
	}
	
	private AdvancedTableModel<ComponenteIngredienteReceta> crearTableModelComponente() {
		eventListComponentes = new BasicEventList<>();
		textFilteratorComponentes = new ComponenteTextFilterator();
		
		// Crear editor de filtro específico para componentes
		editorFiltroComponentes = new TextComponentMatcherEditor<>(
			campoFiltroCompartido, 
			textFilteratorComponentes
		);
		
		listaFiltradaComponentes = new FilterList<>(eventListComponentes, editorFiltroComponentes);
    	tableModelComponentes = GlazedListsSwing.eventTableModelWithThreadProxyList(
    		listaFiltradaComponentes, 
    		new ComponenteTableFormat()
    	);    	
    	return tableModelComponentes;
	}
	
	private AdvancedTableModel<MovimientoInventario> crearTableModelMovimientoInventario() {
		eventListMovimientos = new BasicEventList<>();
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
		//Guarda solo el componente 
		EventList<ComponenteIngredienteReceta> lista = new BasicEventList<ComponenteIngredienteReceta>();
		lista.addAll(eventListComponentes);
		NewMovementDialog i = new NewMovementDialog(null, new DefaultEventComboBoxModel<ComponenteIngredienteReceta>(lista));
		if(i.isMovimientoGuardado() == true) {
			eventListMovimientos.add(i.getMovimientoInventario());
		}
	}
	
	private void cambiarTabla() {
		tablaInventarioDesplegada = !tablaInventarioDesplegada;
		campoFiltroCompartido.setText("");
		actualizarListaFiltrosDisponibles();
		
		if(tablaInventarioDesplegada) {
			// Cambiar a tabla de componentes
			loadComponenteTable();
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