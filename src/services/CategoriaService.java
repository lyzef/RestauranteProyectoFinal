package services;

import java.util.Objects;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import models.Categoria;
import repository.CategoriaRepository;

public class CategoriaService {
    private final CategoriaRepository repo;
    private final EventList<Categoria> listaCategoria;
    
    public CategoriaService() {
        this.repo = new CategoriaRepository();
        this.listaCategoria = new BasicEventList<>();
        try {
            loadCategoriasFromDataBase();
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar CategoriaService", e);
        }
    }
    
    public void saveCategoria(Categoria categoria) throws Exception {
        categoria.setId(repo.saveCategoria(categoria));
        listaCategoria.getReadWriteLock().writeLock().lock();
        try {
            listaCategoria.add(categoria);
        } finally {
            listaCategoria.getReadWriteLock().writeLock().unlock();
        }
    }
    
    public void updateCategoria(Categoria categoria) throws Exception {
        repo.updateCategoria(categoria);
        
        listaCategoria.getReadWriteLock().writeLock().lock();
        try {
            for (int i = 0; i < listaCategoria.size(); i++) {
                if (Objects.equals(listaCategoria.get(i).getId(), categoria.getId())) {
                    listaCategoria.set(i, categoria);
                    break;
                }
            }
        } finally {
            listaCategoria.getReadWriteLock().writeLock().unlock();
        }
    }
    
    public void deleteCategoria(Categoria categoria) throws Exception {
        repo.deleteCategoria(categoria.getId());
        listaCategoria.getReadWriteLock().writeLock().lock();
        try {
            listaCategoria.remove(categoria);
        } finally {
            listaCategoria.getReadWriteLock().writeLock().unlock();
        }
    }
    
    public Categoria getCategoriaById(Integer id) {
        listaCategoria.getReadWriteLock().readLock().lock();
        try {
            for (Categoria categoria : listaCategoria) {
                if (Objects.equals(categoria.getId(), id)) {
                    return categoria;
                }
            }
            return null; 
        } finally {
            listaCategoria.getReadWriteLock().readLock().unlock();
        }
    }

    
    public void loadCategoriasFromDataBase() throws Exception {
        listaCategoria.getReadWriteLock().writeLock().lock();
        try {
            listaCategoria.clear();
            listaCategoria.addAll(repo.getCategorias());
        } finally {
            listaCategoria.getReadWriteLock().writeLock().unlock();
        }
    }
    
    public EventList<Categoria> getListaModificable() {
        return listaCategoria;
    }
    
    public EventList<Categoria> getListaSoloLectura() {
        return GlazedLists.readOnlyList(this.listaCategoria);
    }
}