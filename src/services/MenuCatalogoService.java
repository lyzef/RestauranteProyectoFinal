package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import models.Categoria;
import models.ComponenteIngredienteReceta;
import models.Estructura_receta;
import models.MovimientoInventario;
import models.Platillo;
import models.MovimientoInventario.tipoMovimiento;

public class MenuCatalogoService {
	ComponenteService componenteService;
	PlatilloService platilloService;
	CategoriaService categoriaService;
	EstructuraRecetaService estructuraService;
	
	List<Platillo> platillosDisponibles = new ArrayList<>();
	
	
	
	public MenuCatalogoService(ComponenteService componenteService, PlatilloService platilloService,
			CategoriaService categoriaService, EstructuraRecetaService estructuraService) {
		super();
		this.componenteService = componenteService;
		this.platilloService = platilloService;
		this.categoriaService = categoriaService;
		this.estructuraService = estructuraService;
	}

	private boolean comprobarPlatilloDisponible(int idReceta) {
    	ComponenteIngredienteReceta receta = componenteService.getComponenteById(idReceta);
    	
    	if(receta.isInventariable()) {
    		return receta.getStockActual() >= receta.getStockMinimoBloqueo() ? true : false;
    	}
    	
    	//Si no es inventariable entonces obtenemos sus hijos y quitamos su stock
        List<Estructura_receta> hijos = estructuraService.getHijosByID(idReceta);
        
        for (Estructura_receta hijo : hijos) {
        	if(!comprobarPlatilloDisponible(hijo.getChild_id())) {
        		return false;
        		
        	}
         
        }
        return true;
    }
	
	public void getPlatillosDisponibles(){	
		for(Platillo platillo : platilloService.getListaSoloLectura()) {
			if(comprobarPlatilloDisponible(platillo.getComponenteId())) {
				platillosDisponibles.add(platillo);
			}
		}
	}
	
	/**
     * Versión que incluye los nombres de las categorías
     * @return Map clave es el nombre de la categoría y valor es un EventList de platillos
     */
    public Map<String, EventList<Platillo>> getPlatillosAgrupadosPorNombreCategoria() {
    	//Cargar listas
    	platillosDisponibles.clear();
    	getPlatillosDisponibles();
  
        Map<String, EventList<Platillo>> platillosPorCategoria = new HashMap<>();
        EventList<Categoria> categorias = categoriaService.getListaModificable();
        
        for (Platillo platillo : platillosDisponibles) {
            String nombreCategoria = "Sin categoría";
            
            // Buscar el nombre de la categoría
            for (Categoria categoria : categorias) {
                if (categoria.getId() == platillo.getCategoriaId()) {
                    nombreCategoria = categoria.getNombre();
                    break;
                }
            }
            
            // Si no existe la lista para esta categoría, crear una nueva
            platillosPorCategoria.putIfAbsent(nombreCategoria, new BasicEventList<>());
            
            // Agregar el platillo a la lista correspondiente
            platillosPorCategoria.get(nombreCategoria).add(platillo);
        }
        
        return platillosPorCategoria;
    }
}
