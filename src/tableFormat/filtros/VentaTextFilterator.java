package tableFormat.filtros;

import java.util.List;

import ca.odell.glazedlists.TextFilterator;
import models.Platillo;
import models.Venta;
import tableFormat.filtros.PlatilloTextFilterator.TipoFiltroPlatillo;

public class VentaTextFilterator implements TextFilterator<Venta> {
	public enum TipoFiltroVenta {
        SIN_FILTRO("Elegir"),      
        CAJERO("Cajero"),
        FECHA_HORA("Fecha:Hora"),
        TOTAL("Total venta"),
        METODO_PAGO("Metodo de pago"),
        ESTADO("Estado");
        
        private final String nombre;

        TipoFiltroVenta(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre; 
        }
        
        public static TipoFiltroVenta fromString(String text) {
            for (TipoFiltroVenta t : TipoFiltroVenta.values()) {
                if (t.nombre.equalsIgnoreCase(text)) {
                    return t;
                }
            }
            return SIN_FILTRO; 
        }
        
        public static String[] getTodasLasColumnas() {
        	TipoFiltroVenta[] valores = values();
            String[] columnas = new String[valores.length];
            for (int i = 0; i < valores.length; i++) {
                columnas[i] = valores[i].nombre;
            }
            return columnas;
        }

    }
	
	private TipoFiltroVenta filtroActivo;
	
    public VentaTextFilterator() {
        this.filtroActivo = TipoFiltroVenta.SIN_FILTRO;
    }
	
	@Override
	public void getFilterStrings(List<String> listaStrings, Venta venta) {
		switch (filtroActivo) {
        case SIN_FILTRO:
        	
            break;
            
        case CAJERO:
            if (venta.getNombreUsuario() != null) {
            	listaStrings.add(venta.getNombreUsuario());
            }
            break;
            
        case FECHA_HORA:
            listaStrings.add(venta.getFechaHoraFormateada());
            break;
            
        case TOTAL:
            listaStrings.add(String.valueOf(venta.getTotalVenta()));
            break;
            
        case METODO_PAGO:
            listaStrings.add(venta.getMetodoPago().toString());
            break;
        case ESTADO:
        	listaStrings.add(venta.getEstado());
        	break;
		}
	}
	
	public void setFiltroActivo(TipoFiltroVenta nuevoFiltro) {
        this.filtroActivo = nuevoFiltro;
    }
	

}
