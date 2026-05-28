package services;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import models.ComponenteIngredienteReceta;
import repository.InventarioRepository;
import views.Dialog.InventarioDialog;

public class ComponenteService {
    private final EventList<ComponenteIngredienteReceta> listaComponentes;
    InventarioRepository repo = new InventarioRepository();

    public ComponenteService() {
        this.listaComponentes = new BasicEventList<>();
        cargarDatosDesdeBD();
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
    
    //Guarda componentes y actualiza la tabla
    public int saveComponente(ComponenteIngredienteReceta c) throws Exception {
    	int i = repo.saveComponente(c);
    	c.setId(i);
    	listaComponentes.add(c);
    	return i;
    }
    
    //Encuentra el ID, actualiza la bd y actualiza la lista
    public void updateComponente(ComponenteIngredienteReceta modificado) throws Exception {
    	this.listaComponentes.getReadWriteLock().writeLock().lock();
        try {
            for (int i = 0; i < listaComponentes.size(); i++) {
                if (listaComponentes.get(i).getId() == modificado.getId()) {
                    repo.updateComponente(modificado);
                    listaComponentes.set(i, modificado);
                    break;
                }
            }
        } finally {
            this.listaComponentes.getReadWriteLock().writeLock().unlock();
        }
    }
    
    public void cargarDatosDesdeBD() {
    	try {
    		listaComponentes.clear();
			listaComponentes.addAll(repo.getComponentes());
		} catch (Exception e) {
			System.out.println("Error : Lista componente no cargada , RAZON " + e.getMessage());
		}
    }
    
    
}
