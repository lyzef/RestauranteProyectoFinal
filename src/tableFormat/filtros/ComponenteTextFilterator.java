package tableFormat.filtros;
import ca.odell.glazedlists.TextFilterator;
import models.ComponenteIngredienteReceta;
import java.util.List;

public class ComponenteTextFilterator implements TextFilterator<ComponenteIngredienteReceta> {
    
	public enum TipoFiltro {
        SIN_FILTRO("Elegir"),      
        NOMBRE("Nombre"),
        TIPO_COMPONENTE("Tipo"),
        STOCK("Stock"),
        ID("ID");
        
        private final String nombre;

        TipoFiltro(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre; 
        }
        
        public static TipoFiltro fromString(String text) {
            for (TipoFiltro t : TipoFiltro.values()) {
                if (t.nombre.equalsIgnoreCase(text)) {
                    return t;
                }
            }
            return SIN_FILTRO; // O lanzar una excepción
        }
    }
    
    private TipoFiltro filtroActivo = TipoFiltro.SIN_FILTRO;
    
    public ComponenteTextFilterator() {
        this.filtroActivo = TipoFiltro.SIN_FILTRO;
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
        this.filtroActivo = TipoFiltro.SIN_FILTRO;
    }
    
    public void setFiltroActivo(TipoFiltro nuevoFiltro) {
        this.filtroActivo = nuevoFiltro;
    }
    
    public void filtrarPorNombre() {
        this.filtroActivo = TipoFiltro.NOMBRE;
    }
    
    public void filtrarPorTipo() {
        this.filtroActivo = TipoFiltro.TIPO_COMPONENTE;
    }
    
    public void filtrarPorStock() {
        this.filtroActivo = TipoFiltro.STOCK;
    }
    
    public void filtrarPorId() {
        this.filtroActivo = TipoFiltro.ID;
    }
    
    public TipoFiltro getFiltroActivo() {
        return filtroActivo;
    }
}