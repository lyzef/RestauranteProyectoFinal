package services;

import java.util.ArrayList;
import java.util.List;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import models.ComponenteIngredienteReceta;
import models.Estructura_receta;
import repository.InventarioRepository;

public class EstructuraRecetaService {
	InventarioRepository repo;
	EventList<Estructura_receta> lista;
	
	public EstructuraRecetaService() {
		repo = new InventarioRepository();
		lista = new BasicEventList<Estructura_receta>();
		try {
			cargarDatosDesdeBD();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public EventList<Estructura_receta> getListaModificable() {
        return this.lista;
    }
    
    public EventList<Estructura_receta> getListaSoloLectura() {
        return GlazedLists.readOnlyList(this.lista);
    }
    
    //Borra componentes y actualiza
    public void deleteHijos(int parent_id) throws Exception {
    	repo.eliminarEstructuraReceta(parent_id);
    	cargarDatosDesdeBD();
    }
    
    //Guarda componentes y actualiza la tabla
    public void saveHijos(List<Estructura_receta> ingredientes) throws Exception {
    	if(ingredientes.isEmpty()) {
    		throw new Exception("Error : Lista vacia");
    	}
    	
    	int parent_id = ingredientes.get(0).getParent_id();
    	for(Estructura_receta e: ingredientes) {
    		if(e.getParent_id() != parent_id) {
    			throw new Exception("Error : parents id diferentes");
    		}
    	}
    	
    	repo.saveEstructuraReceta(parent_id, ingredientes);
    	cargarDatosDesdeBD();
    }
    
    public List<Estructura_receta> getHijosByID(int ParentId) {
    	List<Estructura_receta> hijos = new ArrayList<Estructura_receta>();
    	
    	for(Estructura_receta receta : lista) {
    		if(receta.getParent_id() == ParentId) {
    			hijos.add(receta);
    		}
    	}
    	
    	return hijos; 
    }
    
    public void cargarDatosDesdeBD() throws Exception {
    	try {
    		lista.clear();
			lista.addAll(repo.getTodasLasEstructuras());
		} catch (Exception e) {
			throw new Exception("Error : Lista estructura recetas no cargada , RAZON " + e.getMessage());
		}
    }
    
}
