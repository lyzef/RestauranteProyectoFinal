package tableFormat;

import java.util.Comparator;

import ca.odell.glazedlists.gui.AdvancedTableFormat;
import ca.odell.glazedlists.gui.TableFormat;
import models.ComponenteIngredienteReceta;
import models.MovimientoInventario;

public class MovimientoInventariotTableFormat implements TableFormat<MovimientoInventario> {
	
	private final String[] columns = {
	        "Componente",
	        "Tipo",
	        "Cantidad ",
	        "Costo",
	        "Motivo",
	        "Fecha",
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
	public Object getColumnValue(MovimientoInventario c, int column) {
        switch(column) {
            case 0: return c.getComponente_nombre();
            case 1: return c.getTipo_movimiento().toString();
            case 2: return c.getCantidad();
            case 3: return "$" + c.getCosto_movimiento();
            case 4: return c.getMotivo();
            case 5: return c.getFecha();
            default: return null;
        }
    }


}
