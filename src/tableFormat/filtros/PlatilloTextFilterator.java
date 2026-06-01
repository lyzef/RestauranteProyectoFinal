package tableFormat.filtros;

import java.util.List;

import ca.odell.glazedlists.TextFilterator;
import models.Platillo;

public class PlatilloTextFilterator implements TextFilterator<Platillo>{
	public enum TipoFiltroPlatillo {
        SIN_FILTRO("Elegir"),      
        NOMBRE("Nombre"),
        CATEGORIA("Categoria"),
        PRECIO("Precio"),
        DESCRIPCION("Descripcion");
        
        private final String nombre;

        TipoFiltroPlatillo(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre; 
        }
        
        public static TipoFiltroPlatillo fromString(String text) {
            for (TipoFiltroPlatillo t : TipoFiltroPlatillo.values()) {
                if (t.nombre.equalsIgnoreCase(text)) {
                    return t;
                }
            }
            return SIN_FILTRO; 
        }
        
        public static String[] getTodasLasColumnas() {
        	TipoFiltroPlatillo[] valores = values();
            String[] columnas = new String[valores.length];
            for (int i = 0; i < valores.length; i++) {
                columnas[i] = valores[i].nombre;
            }
            return columnas;
        }

    }

	private TipoFiltroPlatillo filtroActivo;
	
    public PlatilloTextFilterator() {
        this.filtroActivo = TipoFiltroPlatillo.SIN_FILTRO;
    }
    
	@Override
	public void getFilterStrings(List<String> listaStrings, Platillo platillo) {
		switch (filtroActivo) {
        case SIN_FILTRO:
        	
            break;
            
        case NOMBRE:
            if (platillo.getComponenteNombre() != null) {
            	listaStrings.add(platillo.getComponenteNombre());
            }
            break;
            
        case CATEGORIA:
            listaStrings.add(platillo.getCategoriaNombre());
            break;
            
        case PRECIO:
            listaStrings.add(String.valueOf(platillo.getPrecioVenta()));
            break;
            
        case DESCRIPCION:
            listaStrings.add(platillo.getDescripcion());
            break;
		}
	}
	
	public void setFiltroActivo(TipoFiltroPlatillo nuevoFiltro) {
        this.filtroActivo = nuevoFiltro;
    }

}
