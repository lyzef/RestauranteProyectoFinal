package tableFormat.filtros;
import ca.odell.glazedlists.TextFilterator;
import models.ComponenteIngredienteReceta;
import java.util.List;

public class ComponenteTextFilterator implements TextFilterator<ComponenteIngredienteReceta> {
    
	public enum TipoFiltroComponente {
        SIN_FILTRO("Elegir"),      
        NOMBRE("Nombre"),
        TIPO_COMPONENTE("Tipo"),
        STOCK("Stock"),
        ID("ID");
        
        private final String nombre;

        TipoFiltroComponente(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre; 
        }
        
        public static TipoFiltroComponente fromString(String text) {
            for (TipoFiltroComponente t : TipoFiltroComponente.values()) {
                if (t.nombre.equalsIgnoreCase(text)) {
                    return t;
                }
            }
            return SIN_FILTRO; // O lanzar una excepción
        }
        
        public static String[] getTodasLasColumnas() {
            TipoFiltroComponente[] valores = values();
            String[] columnas = new String[valores.length];
            for (int i = 0; i < valores.length; i++) {
                columnas[i] = valores[i].nombre;
            }
            return columnas;
        }

    }
    
    private TipoFiltroComponente filtroActivo = TipoFiltroComponente.SIN_FILTRO;
    
    public ComponenteTextFilterator() {
        this.filtroActivo = TipoFiltroComponente.SIN_FILTRO;
    }
    
    @Override
    public void getFilterStrings(List<String> listaStrings, ComponenteIngredienteReceta componente) {
        switch (filtroActivo) {
            case SIN_FILTRO:
                break;
                
            case NOMBRE:
                if (componente.getNombre() != null) {
                    listaStrings.add(componente.getNombre());
                }
                break;
                
            case TIPO_COMPONENTE:
                if (componente.getTipoComponente() != null) {
                    listaStrings.add(componente.getTipoComponente());
                }
                break;
                
            case STOCK:
                listaStrings.add(String.valueOf(componente.getStockActual()));
                break;
                
            case ID:
                listaStrings.add(String.valueOf(componente.getId()));
                break;
        }
    }
    
    public void sinFiltro() {
        this.filtroActivo = TipoFiltroComponente.SIN_FILTRO;
    }
    
    public void setFiltroActivo(TipoFiltroComponente nuevoFiltro) {
        this.filtroActivo = nuevoFiltro;
    }
    
    public void filtrarPorNombre() {
        this.filtroActivo = TipoFiltroComponente.NOMBRE;
    }
    
    public void filtrarPorTipo() {
        this.filtroActivo = TipoFiltroComponente.TIPO_COMPONENTE;
    }
    
    public void filtrarPorStock() {
        this.filtroActivo = TipoFiltroComponente.STOCK;
    }
    
    public void filtrarPorId() {
        this.filtroActivo = TipoFiltroComponente.ID;
    }
    
    public TipoFiltroComponente getFiltroActivo() {
        return filtroActivo;
    }
}