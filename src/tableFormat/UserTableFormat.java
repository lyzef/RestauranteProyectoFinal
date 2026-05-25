package tableFormat;

import ca.odell.glazedlists.gui.TableFormat;
import models.User;

public class UserTableFormat implements TableFormat<User> {

    private final String[] columns = {
        "Nombre",
        "Rol",
        "Estado",
        "Correo",
        "Última sesión",
    };

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

   
    @Override
    public Object getColumnValue(User user, int column) {
        switch(column) {
            case 0: return user.getNombre();
            case 1: return user.getRol();
            case 2: return user.isActivo() ? "Activo" : "Inactivo";
            case 3: return user.getCorreo();
            case 4: return user.getUltimaSesion();
            default: return null;
        }
    }
}