package services;

import java.util.ArrayList;
import java.util.List;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.matchers.Matcher;
import models.ComponenteIngredienteReceta;
import repository.InventarioRepository;
import views.Dialog.InventarioDialog;

public class ComponenteService {
    private final EventList<ComponenteIngredienteReceta> listaComponentes;
    InventarioRepository repo = new InventarioRepository();

    public ComponenteService() {
        this.listaComponentes = new BasicEventList<>();
        try {
			cargarDatosDesdeBD();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public EventList<ComponenteIngredienteReceta> getListaModificable() {
        return this.listaComponentes;
    }
    
    public EventList<ComponenteIngredienteReceta> getListaSoloLectura() {
        return GlazedLists.readOnlyList(this.listaComponentes);
    }
    
    //Borra componentes y actualiza
    public void deleteComponente(ComponenteIngredienteReceta c) throws Exception {
    	repo.deleteComponente(c.getId());
    	listaComponentes.remove(c);
    }
    
    //Guarda componentes, guarda id generada, actualiza tabla
    public void saveComponente(ComponenteIngredienteReceta c) throws Exception {
    	int i = repo.saveComponente(c);
    	c.setId(i);
    	listaComponentes.add(c);
    }
    
    //Encuentra el ID, actualiza la bd y actualiza la lista
    public void updateComponente(ComponenteIngredienteReceta modificado) throws Exception {
    	this.listaComponentes.getReadWriteLock().writeLock().lock();
        try {
            for (int i = 0; i < listaComponentes.size(); i++) {
                if (listaComponentes.get(i).getId() == modificado.getId()) {
                    repo.updateComponente(modificado);
                    listaComponentes.set(i, modificado); //No es 100 necesario pero sirve para avisar a la UI
                    break;
                }
            }
        } finally {
            this.listaComponentes.getReadWriteLock().writeLock().unlock();
        }
    }
    
    public ComponenteIngredienteReceta getComponenteById(int id) {
    	for (int i = 0; i < listaComponentes.size(); i++) {
            if (listaComponentes.get(i).getId() == id) {
                return listaComponentes.get(i);
            }
        }
    	return null;
    }
    
    public ComponenteIngredienteReceta getComponenteByIdFromDB(int id) throws Exception {
    	return repo.getComponenteById(id);
    }
    
    public FilterList<ComponenteIngredienteReceta> getAllRecetas() {
        Matcher<ComponenteIngredienteReceta> matcherRecetas = new Matcher<ComponenteIngredienteReceta>() {
            @Override
            public boolean matches(ComponenteIngredienteReceta item) {
                return item.isReceta();
            }
        };
        
        return new FilterList<>(this.listaComponentes, matcherRecetas);
    }
    
    public FilterList<ComponenteIngredienteReceta> getAllInventariables() {
        Matcher<ComponenteIngredienteReceta> matcherInventariables = item -> item.isInventariable();
        return new FilterList<>(this.listaComponentes, matcherInventariables);
    }
    
    public void cargarDatosDesdeBD() throws Exception {
	
		try {
			listaComponentes.clear();
			listaComponentes.addAll(repo.getComponentes());
		} catch (Exception e) {
			throw new Exception("Datos de componente no cargados");
		}
		
    }
    
    
}
