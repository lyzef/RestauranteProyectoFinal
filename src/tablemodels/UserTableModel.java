package tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.User;

public class UserTableModel extends AbstractTableModel {

    private List<User> users;
    
    // CORRECCIÓN: Se eliminaron las 3 columnas y se formatearon los nombres para la vista (18 columnas en total)
    private final String[] columns = {
        "Nombre",
        "Fecha de nacimiento",
        "CURP",
        "Teléfono",
        "Correo",
        "NSS",
        "Estado civil",
        "Género",
        "Puesto actual",
        "Descripción de funciones",
        "Tipo de contrato",
        "Turno",
        "Alergias conocidas",
        "Contacto de emergencia",
        "Tipo de sangre",
        "Banco",
        "Número de cuenta",
        "Sueldo"
    };
    
    public UserTableModel(List<User> users) {
        this.users = users;
    }
    
    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        
        // CORRECCIÓN: Casos reestructurados para coincidir exactamente con el orden del array 'columns'
        switch(columnIndex) {
            case 0:
                return user.getNombre();
            case 1:
                return user.getFechaNacimiento();
            case 2:
                return user.getCurp();
            case 3:
                return user.getTelefono();
            case 4:
                return user.getCorreo();
            case 5:
                return user.getNSS();
            case 6:
                return user.getEstadoCivil();
            case 7:
                return user.getGenero();
            case 8:
                return user.getRol();
            case 9:
                return user.getDescripcionFunciones();
            case 10:
                return user.getTipoContrato();
            case 11:
                return user.getTurno();
            case 12:
                return user.getAlergiasConocidas();
            case 13:
                return user.getContactoEmergencia();
            case 14:
                return user.getTipoDeSangre();
            case 15:
                return user.getBanco();
            case 16:
                return user.getNumeroCuenta();
            case 17:
                return user.getSueldo();
            default:
                return null;
        }
    }

    public User getUserAt(int row) {
        return users.get(row);
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
        fireTableDataChanged();
    }
    
    /* Estos métodos permiten modificar una sola fila al momento de añadir, editar o eliminar un usuario.
	 * Deberán llamarlos en el constructor cuando hacen cada operación. Ya les puse el ejemplo con editar y
	 * eliminar.
	 */
	public void removeRow(int row) {
		users.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
	public void addRow(User user) {
		int row = users.size();
		users.add(user);
		fireTableRowsInserted(row, row);
	}
	
	public void updateRow(int row, User user) {
		users.set(row, user);
		fireTableRowsUpdated(row, row);
	}
}