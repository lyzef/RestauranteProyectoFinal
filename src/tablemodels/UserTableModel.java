package tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.User;

public class UserTableModel extends AbstractTableModel {

    private List<User> users;
    
    // CORRECCIÓN: Se agregaron todas las columnas correspondientes al modelo User
    private final String[] columns = {
        "ID",
        "Nombre",
        "Rol",
        "Estado",
        "Correo",
        "Última sesión",
        "Fecha de nacimiento",
        "CURP",
        "Teléfono",
        "NSS",
        "Estado Civil",
        "Género",
        "Descripción Funciones",
        "Tipo Contrato",
        "Turno",
        "Alergias",
        "Contacto Emergencia",
        "Tipo de Sangre",
        "Banco",
        "Número Cuenta",
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
        
        switch(columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getNombre();
            case 2:
                return user.getRol();
            case 3:
                return user.isActivo() ? "Activo" : "Inactivo"; // Retorna texto 
            case 4:
                return user.getCorreo();
            case 5:
                return user.getUltimaSesion();
            case 6:
                return user.getFechaNacimiento();
            case 7:
                return user.getCurp();
            case 8:
                return user.getTelefono();
            case 9:
                return user.getNSS();
            case 10:
                return user.getEstadoCivil();
            case 11:
                return user.getGenero();
            case 12:
                return user.getDescripcionFunciones();
            case 13:
                return user.getTipoContrato();
            case 14:
                return user.getTurno();
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