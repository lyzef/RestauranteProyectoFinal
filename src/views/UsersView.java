package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import tablemodels.UserTableModel;

/**
 *Clase que genera una vista con tablas, opciones de modificacion y demas de los usuarios registrados
 *
 **/
public class UsersView extends JPanel{

	private JTable table;
	private JButton btnEdit;
	private JButton btnAdd;
	private JButton btnDelete;
	
	public UsersView() {
		setLayout(new BorderLayout());
		table = new JTable();
		
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnAdd = new JButton("Agregar");
        btnEdit = new JButton("Editar");
        btnDelete = new JButton("Eliminar");

        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        
        add(panelButtons, BorderLayout.NORTH);
	}
	
	public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }
	
    public int getSelectedRow() {
    	return table.getSelectedRow();
    }
    
	public void setTableModel(UserTableModel model) {
		table.setModel(model);
	}
	
	public JTable getTable() {
		return table;
	}
	
}