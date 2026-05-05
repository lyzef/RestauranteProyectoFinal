package controller;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import models.User;
import repository.UserRepository;
import tablemodels.UserTableModel;
import utilidades.PDFExporter; 
import views.UserFormDialog;
import views.UsersView;

public class UserController {

	private UsersView view;
	private UserRepository repo;
	private UserTableModel model;
	
	public UserController(UsersView view) {
		this.view = view;
		this.repo = new UserRepository(); 
		
		this.view.getBtnAdd().addActionListener(e -> {
			openForm(null);
		});
		
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
			List<User> listaParaExportar = repo.getAllUsers(); 
			if (listaParaExportar.isEmpty()) {
				JOptionPane.showMessageDialog(view, "No hay datos para exportar");
				return;
			}
			new PDFExporter().exportUsers(view, listaParaExportar);
		});
	}
	
	/**
	 * Carga los usuarios del archivo y actualiza la tabla
	 */
	public void loadUsers() {
		if(view.getAdvertencias() != null) {
			view.getAdvertencias().setText("Tabla cargada");
		}
		
		try {
			List<User> users = repo.getAllUsers();
			
			if(model == null) {
				model = new UserTableModel(users);
				view.setTableModel(model);
			} else {
				model.setUsers(users);
				model.fireTableDataChanged(); 
			}
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(view, "Error al cargar usuarios: " + ex.getMessage());
		}
	}
	
	private void openForm(User user) {
		resetAdvertencias();
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
	
	private void resetAdvertencias() {
		if(view.getAdvertencias() != null) {
			view.getAdvertencias().setText("");
		}
	}
	public void loadUsers() {
	    try {
	        List<User> users = repo.getAllUsers();
	        
	        if(model == null) {
	            model = new UserTableModel(users);
	            view.setTableModel(model);
	        } else {
	            model.setUsers(users);
	        }
	        

	        model.fireTableDataChanged(); 
	        view.getTabla().repaint();
	        view.getTabla().revalidate();

	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
	    }
	}
}
