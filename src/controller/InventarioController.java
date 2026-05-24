package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import views.InventarioDialog;
import views.Admin.InventoryView;

public class InventarioController {
	InventoryView view;
	
	public InventarioController(InventoryView view) {
		this.view = view;
		registrarListeners();
	}
	
	private void registrarListeners() {
		view.getBtnAdd().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
		        new InventarioDialog(null);
		    }
		});
	}
}
