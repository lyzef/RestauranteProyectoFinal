package controller;

import repository.UserRepository;
import tablemodels.UserTableModel;
import views.UserFormDialog;
import views.UsersView;
import views.formulario.FormularioRegistro;

public class UserController {
		private UsersView view;
		private UserRepository repo;
		private UserTableModel model;
		
		public UserController(UsersView view) {
			this.view = view;
			repo = new UserRepository();
			
			view.getBtnAdd().addActionListener(e -> {
				//Falta crear userFormDialog con elementos de este
				UserFormDialog form = new UserFormDialog(null, null);
				form.setVisible(true);
			});
		}
		
}



