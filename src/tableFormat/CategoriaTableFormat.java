package tableFormat;

import ca.odell.glazedlists.gui.TableFormat;
import models.Categoria;
import models.MovimientoInventario;

public class CategoriaTableFormat implements TableFormat<Categoria> {
	
	private final String[] columns = {
	        "ID",
	        "Nombre",
	        "Descripcion",
	        "Activo"
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
	public Object getColumnValue(Categoria c, int columna) {
		switch(columna) {
		    case 0: return c.getId();
		    case 1: return c.getNombre();
		    case 2: return c.getDescripcion();
		    case 3: return c.getActivo() ? "Verdadero" : "Falso";
        default: return null;
		}
	}

}
