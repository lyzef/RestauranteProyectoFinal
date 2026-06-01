package tableFormat;

import ca.odell.glazedlists.gui.TableFormat;
import models.Platillo;
import models.User;

public class PlatillosTableFormat implements TableFormat<Platillo> {

	private final String[] columns = {
	       "ID",
	       "Nombre receta",
	       "Categoria",
	       "Descripcion",
	       "Precio de venta"
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
	    public Object getColumnValue(Platillo p, int column) {
	        switch(column) {
	        case 0: return p.getId();
	        case 1: return p.getComponenteNombre();
	        case 2: return p.getCategoriaNombre();
	        case 3: return p.getDescripcion();
	        case 4: return p.getPrecioVenta();
	            default: return null;
	        }
	    }

}
