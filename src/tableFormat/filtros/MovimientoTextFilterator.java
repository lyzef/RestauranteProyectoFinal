package tableFormat.filtros;

import java.util.List;

import ca.odell.glazedlists.TextFilterator;
import models.MovimientoInventario;
import tableFormat.filtros.ComponenteTextFilterator.TipoFiltroComponente;

public class MovimientoTextFilterator implements TextFilterator<MovimientoInventario>{
	public enum TipoFiltroMovimiento {
        SIN_FILTRO("Elegir"),      
        NOMBRE("Nombre"),
        CANTIDAD("Cantidad"),
        COSTO("Costo"),
        FECHA("Fecha");
        
        private final String nombre;

        TipoFiltroMovimiento(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre; 
        }
        
        public static TipoFiltroMovimiento fromString(String text) {
            for (TipoFiltroMovimiento t : TipoFiltroMovimiento.values()) {
                if (t.nombre.equalsIgnoreCase(text)) {
                    return t;
                }
            }
            return SIN_FILTRO; 
        }
        
        public static String[] getTodasLasColumnas() {
            TipoFiltroMovimiento[] valores = values();
            String[] columnas = new String[valores.length];
            for (int i = 0; i < valores.length; i++) {
                columnas[i] = valores[i].nombre;
            }
            return columnas;
        }

    }

	private TipoFiltroMovimiento filtroActivo;
	
    public MovimientoTextFilterator() {
        this.filtroActivo = TipoFiltroMovimiento.SIN_FILTRO;
    }
    
	@Override
	public void getFilterStrings(List<String> listaStrings, MovimientoInventario movimiento) {
		switch (filtroActivo) {
        case SIN_FILTRO:
            break;
            
        case NOMBRE:
            if (movimiento.getComponente_nombre() != null) {
            	listaStrings.add(movimiento.getComponente_nombre());
            }
            break;
            
        case CANTIDAD:
            listaStrings.add(String.valueOf(movimiento.getCantidad())	);
            break;
            
        case COSTO:
            listaStrings.add(String.valueOf(movimiento.getCosto_movimiento()));
            break;
            
        case FECHA:
            listaStrings.add(String.valueOf(movimiento.getFecha()));
            break;
		}
	}
	
	public void setFiltroActivo(TipoFiltroMovimiento nuevoFiltro) {
        this.filtroActivo = nuevoFiltro;
    }
}
