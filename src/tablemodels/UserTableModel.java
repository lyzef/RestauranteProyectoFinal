package tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import models.User;

public class UserTableModel extends AbstractTableModel{

	private List<User> users;
	
	private final String[] columns = {
			"nombre",
			"fechaNacimiento",
			"curp",
			"telefono",
			"correo",
			"estadoCivil",
			"genero",
			"puestoActual",
			"descripcionFunciones",
			"perfilPuesto",
			"condicionesLaborales",
			"ubicacionOrganizacional",
			"tipoContrato",
			"turno",
			"NSS",
			"alergiasConocidas",
			"contactoEmergencia",
			"tipoDeSangre",
			"banco",
			"numeroCuenta",
			"sueldo"
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
	            return user.getEstadoCivil();
	        case 6:
	            return user.getGenero();
	        case 7:
	            return user.getPuestoActual();
	        case 8:
	            return user.getDescripcionFunciones();
	        case 9:
	            return user.getPerfilPuesto();
	        case 10:
	            return user.getCondicionesLaborales();
	        case 11:
	            return user.getUbicacionOrganizacional();
	        case 12:
	            return user.getTipoContrato();
	        case 13:
	            return user.getTurno();
	        case 14:
	            return user.getNSS();
	        case 15:
	            return user.getAlergiasConocidas();
	        case 16:
	            return user.getContactoEmergencia();
	        case 17:
	            return user.getTipoDeSangre();
	        case 18:
	            return user.getBanco();
	        case 19:
	            return user.getNumeroCuenta();
	        case 20:
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
	
}