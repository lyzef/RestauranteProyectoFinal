package services;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import models.Categoria;
import models.Platillo;
import repository.PlatillosRepository;

public class PlatilloService {
    private final CategoriaService categoriaService;
    private final PlatillosRepository platilloRepo;
    private final EventList<Platillo> listaPlatillos;
    
    public PlatilloService(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
        this.platilloRepo = new PlatillosRepository();
        this.listaPlatillos = new BasicEventList<>();
        try {
            loadPlatillosFromDatabase();
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar MenuService", e);
        }
    }
    
    public void savePlatillo(Platillo platillo) throws Exception {
        platillo.setId(platilloRepo.savePlatillo(platillo));
        listaPlatillos.getReadWriteLock().writeLock().lock();
        try {
            listaPlatillos.add(platillo);
        } finally {
            listaPlatillos.getReadWriteLock().writeLock().unlock();
        }
    }
    
    public void updatePlatillo(Platillo platillo) throws Exception {
        listaPlatillos.getReadWriteLock().writeLock().lock();
        try {
            platilloRepo.updatePlatillo(platillo);
            for (int i = 0; i < listaPlatillos.size(); i++) {
                if (Objects.equals(listaPlatillos.get(i).getId(), platillo.getId())) {
                    listaPlatillos.set(i, platillo);
                    break;
                }
            }
        } finally {
            listaPlatillos.getReadWriteLock().writeLock().unlock();
        }
    }
    
    public void deletePlatillo(Platillo platillo) throws Exception {
        platilloRepo.deletePlatillo(platillo.getId());
        listaPlatillos.getReadWriteLock().writeLock().lock();
        try {
            listaPlatillos.remove(platillo);
        } finally {
            listaPlatillos.getReadWriteLock().writeLock().unlock();
        }
    }
    
    public void loadPlatillosFromDatabase() throws Exception {
        listaPlatillos.getReadWriteLock().writeLock().lock();
        try {
            listaPlatillos.clear();
            listaPlatillos.addAll(platilloRepo.getPlatillos());
        } finally {
            listaPlatillos.getReadWriteLock().writeLock().unlock();
        }
    }
    
    public Platillo getPlatilloById(int id) throws Exception {
        listaPlatillos.getReadWriteLock().readLock().lock();
        try {
            for (Platillo p : listaPlatillos) {
                if (p.getId() == id) {
                    return p;
                }
            }
        } finally {
            listaPlatillos.getReadWriteLock().readLock().unlock();
        }
        return null;
    }
    
    public EventList<Platillo> getListaModificable() {
        return listaPlatillos;
    }
    
    public EventList<Platillo> getListaSoloLectura() {
        return GlazedLists.readOnlyList(this.listaPlatillos);
    }
    
    
    
    public EventList<Platillo> getPlatillosByCategoriaList(int categoriaId) {
        EventList<Platillo> platillosFiltrados = new BasicEventList<>();
        
        listaPlatillos.getReadWriteLock().readLock().lock();
        try {
            for (Platillo p : listaPlatillos) {
                if (p.getCategoriaId() == categoriaId) {
                    platillosFiltrados.add(p);
                }
            }
        } finally {
            listaPlatillos.getReadWriteLock().readLock().unlock();
        }
        
        return platillosFiltrados;
    }
    
    public EventList<Platillo> getPlatillosByComponenteId(int componenteId) {
        EventList<Platillo> platillosFiltrados = new BasicEventList<>();
        
        listaPlatillos.getReadWriteLock().readLock().lock();
        try {
            for (Platillo p : listaPlatillos) {
                if (p.getComponenteId() == componenteId) {
                    platillosFiltrados.add(p);
                }
            }
        } finally {
            listaPlatillos.getReadWriteLock().readLock().unlock();
        }
        
        return platillosFiltrados;
    }
    
    public int getCantidadPlatillos() {
        return listaPlatillos.size();
    }
    
    public int getCantidadPlatillosByCategoria(int categoriaId) {
        int count = 0;
        listaPlatillos.getReadWriteLock().readLock().lock();
        try {
            for (Platillo p : listaPlatillos) {
                if (p.getCategoriaId() == categoriaId) {
                    count++;
                }
            }
        } finally {
            listaPlatillos.getReadWriteLock().readLock().unlock();
        }
        return count;
    }
}