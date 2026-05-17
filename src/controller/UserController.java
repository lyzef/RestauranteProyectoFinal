package controller;

import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;

import models.User;
import repository.UserRepository;
import tablemodels.UserTableModel;
import utilidades.PDFExporter;
import views.Admin.UserFormDialog;
import views.Admin.UsersView;

public class UserController {

    private UsersView view;
    private UserRepository repo;
    private UserTableModel model;
    
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
            openForm(model.getUserAt(row));
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
    }
    
    /**
     * Carga los usuarios del archivo JSON y actualiza la tabla.
     */
    public void loadUsers() {
        try {
            List<User> users = repo.getAllUsers();
            
            // Si el modelo no existe, se crea. Si existe, se actualiza la lista interna.
            if(model == null) {
                model = new UserTableModel(users);
                view.setTableModel(model);
            } else {
                // Asegúrate de que UserTableModel tenga este método para actualizar su lista
                model.setUsers(users);
                model.fireTableDataChanged(); 
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
        UserFormDialog dialog = new UserFormDialog(null, user);
        dialog.setVisible(true);
        
        if(dialog.isSaved()) {
            User savedUser = dialog.getUsuario();
            
            try {
                List<User> actuales = repo.getAllUsers();
                
                if(user == null) {
                    actuales.add(savedUser);
                } else {
                    int row = view.getSelectedRow();
                    if (row != -1) {
                        actuales.set(row, savedUser);
                    }
                }

                repo.saveAll(actuales);
                loadUsers(); 
                
            } catch(Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error al guardar: " + e.getMessage());
            }
        }
    }
    
    private void deleteUser(int row) {
        try {
            List<User> usuariosActuales = repo.getAllUsers();
            if (row >= 0 && row < usuariosActuales.size()) {
                usuariosActuales.remove(row);
                repo.saveAll(usuariosActuales);
                loadUsers();
            }
        } catch (Exception e) {
            if(view.getAdvertencias() != null) {
                view.getAdvertencias().setText("Error al eliminar: " + e.getMessage());
            }
        }
    }
    
public void generatePdf() {
		
	List<User> listaParaExportar = repo.getAllUsers(); 
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
}