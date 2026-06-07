package tableFormat;

import ca.odell.glazedlists.gui.TableFormat;
import models.Venta;

public class VentaTableFormat implements TableFormat<Venta>{

	private final String[] columns = {
        "ID",
        "Cajero",
        "Estado",
        "Fecha Hora",
        "Total",
        "Metodo de pago",
        "Productos",
        "Unidades"
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
	public Object getColumnValue(Venta c, int column) {
        switch(column) {
            case 0: return c.getId();
            case 1: return c.getNombreUsuario();
            case 2: return c.getEstado();
            case 3: return c.getFechaHoraFormateada();
            case 4: return c.getTotalVenta();
            case 5: return c.getMetodoPago().toString();
            case 6: return c.getCantidadProductos();
            case 7: return c.getCantidadUnidades();
            default: return null;
        }
    }
	
	

}
