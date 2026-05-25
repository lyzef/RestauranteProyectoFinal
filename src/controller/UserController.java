package controller;

import java.util.List;

import javax.swing.JOptionPane;
import ca.odell.glazedlists.*;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import models.ComponenteIngredienteReceta;
import models.User;
import repository.UserRepository;
import services.PDFExporter;
import tableFormat.UserTableFormat;
import views.FormularioDialog;
import views.Admin.UsersView;

public class UserController {

    private UsersView view;
    private UserRepository repo;
    private AdvancedTableModel<User> tableModel;
    EventList<User> eventListUsers;
    
    public UserController(UsersView view) {
        this.view = view;
        this.repo = new UserRepository(); 
        
        // Listeners de botones
        this.view.getBtnAdd().addActionListener(e -> openForm(null));
        
        this.view.getBtnEdit().addActionListener(e -> {
            int row = view.getSelectedRow();
            if(row == -1) {
                JOptionPane.showMessageDialog(view, "Selecciona un usuario");
                return;
            }
            openForm(eventListUsers.get(view.getSelectedRow()));
        });
    
        this.view.getBtnDelete().addActionListener(e -> {
            int row = view.getSelectedRow();
            if(row == -1) {
                JOptionPane.showMessageDialog(view, "Selecciona un usuario");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(view, "¿Estás seguro de eliminar este registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deleteUser(row);
            }
        });

        this.view.getBtnExportPDF().addActionListener(e -> {
            generatePdf();
        });
        
        this.view.getBtnRefresh().addActionListener(e -> {
        	loadUsers();
        });
        
        this.view.getBtnSee().addActionListener(e -> {
        	 int row = view.getSelectedRow();
             if(row == -1) {
                 JOptionPane.showMessageDialog(view, "Selecciona un usuario");
                 return;
             }
             resetAdvertencias();
             FormularioDialog dialog = new FormularioDialog(null, eventListUsers.get(row),true); //Constructor para solo lectura
             dialog.setVisible(true);
        });
        
        //Cargar estadisticas
        view.setTotalUsuarios(Integer.toString(repo.countAllUsers()));
        view.setTotalUsuariosActivos(Integer.toString(repo.countUsersForColumn("activo",true)));
        view.setTotalCajeros(Integer.toString(repo.countUsersForColumn("rol","cajero")));
        view.setTotalCocineros(Integer.toString(repo.countUsersForColumn("rol","cocinero")));
    }
    
    /**
     * Carga los usuarios 
     */
    public void loadUsers() {
        try {
            List<User> users = repo.getUsers();
            
            // Si el modelo no existe, se crea. Si existe, se actualiza la lista interna.
            if(tableModel == null) {
                view.setTableModel(crearTablaModel());
                eventListUsers.addAll(users);
            } else {
                // Actualizar lista
                eventListUsers.clear();
                eventListUsers.addAll(users);
            }
            
            if(view.getAdvertencias() != null) {
                view.getAdvertencias().setText("Tabla cargada con " + users.size() + " usuarios.");
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al cargar usuarios: " + ex.getMessage());
        }
    }
    
    private void openForm(User user) {
        resetAdvertencias();
        // Nota: Asegúrate que UserFormDialog reciba los parámetros correctos
        FormularioDialog dialog = new FormularioDialog(null, user);
        dialog.setVisible(true);
        
        //Como es dialog modal aqui sigue el codigo una vez cerrada la clase dialog
        if(dialog.isSaved()) {
            User savedUser = dialog.getController().getUsuario();
            
            try {
				//Añadir nuevo
				if(user == null) {
					repo.save(savedUser);
					eventListUsers.add(savedUser);
				}else {
					//Editar existente
					int row = view.getSelectedRow();
					boolean updated = repo.update( savedUser);
					if(updated) {
						eventListUsers.set(row, savedUser);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
        }
    }
    
    
    private void deleteUser(int row) {
        try {
        	if(repo.delete(eventListUsers.get(row).getId())) {
        		eventListUsers.remove(row);
        	}
            
        } catch (Exception e) {
            if(view.getAdvertencias() != null) {
                view.getAdvertencias().setText("Error al eliminar: " + e.getMessage());
            }
        }
    }
    
	private void generatePdf() {
			
		List<User> listaParaExportar = repo.getUsers(); 
	    if (listaParaExportar.isEmpty()) {
	        JOptionPane.showMessageDialog(view, "No hay datos para exportar");
	    return;
	    }
	new PDFExporter().exportUsers(null, listaParaExportar); // Exportar PDF, Crear PDF, Elegir ruta
		
		
	}
	
	private void resetAdvertencias() {
	    if(view.getAdvertencias() != null) {
	        view.getAdvertencias().setText("");
	    }
	}
	
	private AdvancedTableModel<User> crearTablaModel() {
		eventListUsers = new BasicEventList<>();
    	tableModel = GlazedListsSwing.eventTableModel(eventListUsers, new UserTableFormat());
    	return tableModel;
	}
	

}

