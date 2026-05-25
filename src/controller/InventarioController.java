package controller;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import models.ComponenteIngredienteReceta;
import models.User;
import repository.InventarioRepository;
import tableFormat.ComponenteTableFormat;
import tableFormat.UserTableFormat;
import tableFormat.filtros.ComponenteTextFilterator;
import tableFormat.filtros.ComponenteTextFilterator.TipoFiltro;
import views.FormularioDialog;
import views.Admin.InventarioDialog;
import views.Admin.InventoryView;

public class InventarioController {
	private InventoryView view;
	private InventarioRepository repo;
	
	private EventList<ComponenteIngredienteReceta> eventListComponentes;
    private AdvancedTableModel<ComponenteIngredienteReceta> tableModel;
    ComponenteTextFilterator TextFilterator;
    
	public InventarioController(InventoryView view) {
		this.view = view;
		this.repo = new InventarioRepository();
		registrarListeners();
		
		loadComponenteTable();
	}
	
	private void registrarListeners() {
		view.getBtnAdd().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
		        openFormComponente(null);
		    }
		});
		
		view.getBtnEdit().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRow();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un usuario");
	                return;
	            }
		        openFormComponente(eventListComponentes.get(row));
		    }
		});
		
		view.getBtnDelete().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRow();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un usuario");
	                return;
	            }
		        try {
					repo.delete(eventListComponentes.get(row).getId());
					eventListComponentes.remove(row);
				} catch (Exception e1) {
					System.out.println("Objeto no eliminado ... " + e);
				}
		    }
		});
		
		view.getBtnSee().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				int row = view.getSelectedRow();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un usuario");
	                return;
	            }
	            new InventarioDialogController(new InventarioDialog(null),eventListComponentes.get(row) , false);
		    }
		});
		
		view.getListaFiltros().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		        	TextFilterator.setFiltroActivo(TipoFiltro.fromString(view.getFiltroSeleccionado()) );
		        }
		    }
		});
			
	}
	
	private void openFormComponente(ComponenteIngredienteReceta componente) {
		// Nota: Asegúrate que UserFormDialog reciba los parámetros correctos
        InventarioDialogController dialog = new InventarioDialogController(new InventarioDialog(null), componente, true);
        
        //Como es dialog modal aqui sigue el codigo una vez cerrada la clase dialog
        if(dialog.saved) {
            ComponenteIngredienteReceta componenteSaved = dialog.getComponente();
            
            try {
				//Añadir nuevo
				if(componente == null) {
					repo.save(componenteSaved);
					eventListComponentes.add(componenteSaved);
				}else {
					//Editar existente
					int row = view.getSelectedRow();
					boolean updated =  repo.update(componenteSaved);
					if(updated) {
						eventListComponentes.set(row, componenteSaved);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
        }
	}
	
	private void loadComponenteTable() {
		try {
            List<ComponenteIngredienteReceta> componentes = repo.getComponentes();
            
            // Si el modelo no existe, se crea. Si existe, se actualiza la lista interna.
            if(tableModel == null) {
                view.setTableModel(crearTablaModel());
                eventListComponentes.addAll(componentes);
            } else {
                // Actualizar lista
            	eventListComponentes.clear();
            	eventListComponentes.addAll(componentes);
            }
                       
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al cargar componentes: " + ex.getMessage());
        }
	}
	
	
	private AdvancedTableModel<ComponenteIngredienteReceta> crearTablaModel() {
		eventListComponentes = new BasicEventList<>();
		TextFilterator =  new ComponenteTextFilterator();
		MatcherEditor<ComponenteIngredienteReceta> editorFiltro = new TextComponentMatcherEditor<>(view.getTextFieldTabla(),TextFilterator );
		FilterList<ComponenteIngredienteReceta> listaFiltrada = new FilterList<>(eventListComponentes, editorFiltro);
    	tableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(listaFiltrada, new ComponenteTableFormat());    	
    	return tableModel;
	}
	
}
