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
import models.EstructuraReceta;
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
	
	
	//Comprueba que existe suficiente inventario en stock asi como que este disponible segun el admin
	private boolean comprobarPlatilloDisponible(int idReceta) {
    	ComponenteIngredienteReceta receta = componenteService.getComponenteById(idReceta);
    	
    	// Esta activa (Por admin)
    	if(receta.isDisponibilidadManual() == false) {
    		System.out.println("Receta " + receta.getNombre() + " bloqueada por admin");
			return false;
		}
    	
    	// Si cuenta checar existencias
    	if(receta.isInventariable()) {
    		return receta.getStockActual() >= receta.getStockMinimoBloqueo() ? true : false;
    	}
    	
    	// Si no esta bloqueda, no es inventariable y no es una receta es un INSUMO NO INVENTARIABLE
    	if(!receta.isReceta()) {
    		return true;
    	}
    	
    	//Si no es inventariable entonces obtenemos sus hijos y quitamos su stock
        List<EstructuraReceta> hijos = estructuraService.getHijosByID(idReceta);
        
        // Comprueba que TENGA minimo UN hijo 
        if(hijos == null || hijos.isEmpty()) {
            System.out.println("Receta " + receta.getNombre() + " no tiene ingredientes asignados");
            return false;
        }
        
        // Verificamos chamacos 
        for (EstructuraReceta hijo : hijos) {
        	if(!comprobarPlatilloDisponible(hijo.getChild_id())) {
        		return false;
        		
        	}
         
        }
   
        // Si todos los hijos tiene stock suficiente en caso que sean inventariables
        // y tiene min un hijo 
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
