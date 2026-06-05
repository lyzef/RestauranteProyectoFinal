package services;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import models.DetalleVenta;
import models.Venta;
import repository.DetalleVentaRepository;
import repository.VentasRepository;

public class VentaService {
    
    private final VentasRepository ventaRepo;
    private final DetalleVentaRepository detalleVentaRepo;
    private final EventList<Venta> listaVentas;
    
    public VentaService() {
        this.ventaRepo = new VentasRepository();
        this.detalleVentaRepo = new DetalleVentaRepository();
        this.listaVentas = new BasicEventList<>();
        try {
            loadVentasFromDatabase();
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar VentaService", e);
        }
    }
    
    /**
     * Registrar una nueva venta
     */
    public Venta registrarVenta(Venta venta) throws Exception {
        // Establecer fecha actual si no tiene
        if (venta.getFechaHora() == null) {
            venta.setFechaHora(Timestamp.valueOf(LocalDateTime.now()));
        }
        
        // Estado por defecto
        if (venta.getEstado() == null || venta.getEstado().isEmpty()) {
            venta.setEstado("PAGADO");
        }
        
        int id = ventaRepo.saveVenta(venta);
        venta.setId(id);
        
        // Actualizar caché
        listaVentas.getReadWriteLock().writeLock().lock();
        try {
            listaVentas.add(venta);
        } finally {
            listaVentas.getReadWriteLock().writeLock().unlock();
        }
        
        return venta;
    }
    
    /**
     * Registrar venta completa con sus detalles SOLO PARA VENTA EN AUTOVENTA
     */
    public Venta registrarVentaCompleta(Venta venta, List<DetalleVenta> detalles,Connection conexion) throws Exception {
        // Establecer fecha actual si no tiene
        if (venta.getFechaHora() == null) {
            venta.setFechaHora(Timestamp.valueOf(LocalDateTime.now()));
        }
        
        // Calcular total de la venta si no viene calculado
        if (venta.getTotalVenta() == 0 && detalles != null) {
            double total = detalles.stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum();
            venta.setTotalVenta(total);
        }
        
        // Guardar venta
        int ventaId = ventaRepo.saveVenta(venta,conexion);
        venta.setId(ventaId);
        
        // Guardar detalles
        if (detalles != null && !detalles.isEmpty()) {
            for (DetalleVenta detalle : detalles) {
                detalle.setVentaId(ventaId);
                detalleVentaRepo.saveDetalleVenta(detalle,conexion);
            }
        }
        
        venta.setDetalles(detalles);
        
        // Actualizar memoria
        listaVentas.getReadWriteLock().writeLock().lock();
        try {
            listaVentas.add(venta);
        } finally {
            listaVentas.getReadWriteLock().writeLock().unlock();
        }
        
        return venta;
    }
  
    /**
     * Obtener venta por ID
     */
    public Venta getVentaById(int id) throws Exception {
        listaVentas.getReadWriteLock().readLock().lock();
        try {
            for (Venta v : listaVentas) {
                if (v.getId() == id) {
                    return v;
                }
            }
        } finally {
            listaVentas.getReadWriteLock().readLock().unlock();
        }
        return null;
    }
    
    /**
     * Obtener venta por ID con sus detalles incluidos
     */
    public Venta getVentaConDetalles(int id) throws Exception {
        Venta venta = getVentaById(id);
        if (venta != null) {
            List<DetalleVenta> detalles = detalleVentaRepo.getDetallesByVentaId(id);
            venta.setDetalles(detalles);
        }
        return venta;
    }
    
    /**
     * Obtener lista de ventas 
     */
    public EventList<Venta> getListaModificable() {
        return listaVentas;
    }
    
    /**
     * Obtener lista de solo lectura
     */
    public EventList<Venta> getListaSoloLectura() {
        return GlazedLists.readOnlyList(listaVentas);
    }
    
    /**
     * Obtener ventas por rango de fechas
     */
    public List<Venta> getVentasByFecha(Timestamp fechaInicio, Timestamp fechaFin) throws Exception {
        return ventaRepo.getVentasByFecha(fechaInicio, fechaFin);
    }
    
    /**
     * Obtener ventas por fecha 
     */
    public List<Venta> getVentasByFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws Exception {
        return ventaRepo.getVentasByFecha(
            Timestamp.valueOf(fechaInicio),
            Timestamp.valueOf(fechaFin)
        );
    }
    
    /**
     * Obtener ventas del día actual
     */
    public List<Venta> getVentasDelDia() throws Exception {
        LocalDateTime inicio = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fin = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return getVentasByFecha(inicio, fin);
    }
    
    /**
     * Obtener ventas por usuario
     */
    public List<Venta> getVentasByUsuario(int usuarioId) throws Exception {
        return ventaRepo.getVentasByUsuario(usuarioId);
    }
    
    /**
     * Obtener ventas por estado
     */
    public List<Venta> getVentasByEstado(String estado) throws Exception {
        return ventaRepo.getVentasByEstado(estado);
    }
    
    /**
     * Obtener ventas pagadas
     */
    public List<Venta> getVentasPagadas() throws Exception {
        return getVentasByEstado("PAGADO");
    }
    
    /**
     * Obtener ventas canceladas
     */
    public List<Venta> getVentasCanceladas() throws Exception {
        return getVentasByEstado("CANCELADO");
    }
    
    /**
     * Obtener detalles de una venta
     */
    public List<DetalleVenta> getDetallesByVentaId(int ventaId) throws Exception {
        return detalleVentaRepo.getDetallesByVentaId(ventaId);
    }
    
    /**
     * Actualizar venta
     */
    public void updateVenta(Venta venta) throws Exception {
        // Verificar si la venta existe
        Venta existing = getVentaById(venta.getId());
        if (existing == null) {
            throw new Exception("Venta no encontrada con ID: " + venta.getId());
        }
        
        // No permitir modificar ventas pagadas (solo estado)
        if (existing.isPagado() && venta.getTotalVenta() != existing.getTotalVenta()) {
            throw new Exception("No se puede modificar el total de una venta ya pagada");
        }
        
        ventaRepo.updateEstadoVenta(venta.getId(), venta.getEstado());
        
        listaVentas.getReadWriteLock().writeLock().lock();
        try {
            for (int i = 0; i < listaVentas.size(); i++) {
                if (listaVentas.get(i).getId() == venta.getId()) {
                    listaVentas.set(i, venta);
                    break;
                }
            }
        } finally {
            listaVentas.getReadWriteLock().writeLock().unlock();
        }
    }
    
    /**
     * Cancelar una venta
     */
    public void cancelarVenta(int ventaId) throws Exception {
        Venta venta = getVentaById(ventaId);
        if (venta == null) {
            throw new Exception("Venta no encontrada con ID: " + ventaId);
        }
        
        if (venta.isCancelado()) {
            throw new Exception("La venta ya está cancelada");
        }
        
        ventaRepo.cancelarVenta(ventaId);
        venta.setEstado("CANCELADO");
        
        listaVentas.getReadWriteLock().writeLock().lock();
        try {
            for (int i = 0; i < listaVentas.size(); i++) {
                if (listaVentas.get(i).getId() == ventaId) {
                    listaVentas.set(i, venta);
                    break;
                }
            }
        } finally {
            listaVentas.getReadWriteLock().writeLock().unlock();
        }
    }
    
    /**
     * Eliminar venta (solo si está cancelada)
     */
    public void deleteVenta(int ventaId) throws Exception {
        Venta venta = getVentaById(ventaId);
        if (venta == null) {
            throw new Exception("Venta no encontrada con ID: " + ventaId);
        }
        
        if (!venta.isCancelado()) {
            throw new Exception("Solo se pueden eliminar ventas canceladas");
        }
        
        // LA BD BORRA SUS DETALLES SOLA
        
        listaVentas.getReadWriteLock().writeLock().lock();
        try {
            listaVentas.remove(venta);
        } finally {
            listaVentas.getReadWriteLock().writeLock().unlock();
        }
    }
    
    /**
     * Recargar ventas desde la base de datos
     */
    public void loadVentasFromDatabase() throws Exception {
        listaVentas.getReadWriteLock().writeLock().lock();
        try {
            listaVentas.clear();
            listaVentas.addAll(ventaRepo.getAllVentas());
        } finally {
            listaVentas.getReadWriteLock().writeLock().unlock();
        }
    }
    
    /**
     * Obtener total de ventas del día
     */
    public double getTotalVentasDelDia() throws Exception {
        return ventaRepo.getTotalVentasDelDia();
    }
    
    /**
     * Obtener cantidad de ventas del día
     */
    public int getCantidadVentasDelDia() throws Exception {
        return ventaRepo.getCantidadVentasDelDia();
    }
    
    /**
     * Obtener cantidad total de ventas
     */
    public int getCantidadVentas() {
        return listaVentas.size();
    }
    
    /**
     * Calcular ganancias totales (suma de todas las ventas pagadas)
     */
    public double getGananciasTotales() throws Exception {
        List<Venta> ventasPagadas = getVentasPagadas();
        return ventasPagadas.stream()
            .mapToDouble(Venta::getTotalVenta)
            .sum();
    }
    
    /**
     * Verificar si existe una venta
     */
    public boolean existeVenta(int id) throws Exception {
        return getVentaById(id) != null;
    }
}
