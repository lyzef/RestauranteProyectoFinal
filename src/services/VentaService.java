package services;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.DetalleVenta;
import models.Venta;
import repository.DetalleVentaRepository;
import repository.VentasRepository;

public class VentaService {
    
    private final VentasRepository ventaRepo;
    private final DetalleVentaRepository detalleVentaRepo;
    
    public VentaService() {
        this.ventaRepo = new VentasRepository();
        this.detalleVentaRepo = new DetalleVentaRepository();
    }
    
    /**
     * Registrar una nueva venta
     */
    public Venta registrarVenta(Venta venta) throws Exception {
        if (venta.getFechaHora() == null) {
            venta.setFechaHora(Timestamp.valueOf(LocalDateTime.now()));
        }
        
        if (venta.getEstado() == null || venta.getEstado().isEmpty()) {
            venta.setEstado("PAGADO");
        }
        
        int id = ventaRepo.saveVenta(venta);
        venta.setId(id);
        
        return venta;
    }
    
    /**
     * Registrar venta completa con sus detalles SOLO PARA VENTA EN AUTOVENTA
     */
    public Venta registrarVentaCompleta(Venta venta, List<DetalleVenta> detalles, Connection conexion) throws Exception {
        if (venta.getFechaHora() == null) {
            venta.setFechaHora(Timestamp.valueOf(LocalDateTime.now()));
        }
        
        if (venta.getTotalVenta() == 0 && detalles != null) {
            double total = detalles.stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum();
            venta.setTotalVenta(total);
        }
        
        int ventaId = ventaRepo.saveVenta(venta, conexion);
        venta.setId(ventaId);
        
        if (detalles != null && !detalles.isEmpty()) {
            for (DetalleVenta detalle : detalles) {
                detalle.setVentaId(ventaId);
                detalleVentaRepo.saveDetalleVenta(detalle, conexion);
            }
        }
        
        venta.setDetalles(detalles);
        return venta;
    }
  
    /**
     * Obtener venta por ID directamente de la DB
     */
    public Venta getVentaById(int id) throws Exception {
        return ventaRepo.getVentaById(id); 
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
        Venta existing = getVentaById(venta.getId());
        if (existing == null) {
            throw new Exception("Venta no encontrada con ID: " + venta.getId());
        }
        
        if (existing.isPagado() && venta.getTotalVenta() != existing.getTotalVenta()) {
            throw new Exception("No se puede modificar el total de una venta ya pagada");
        }
        
        ventaRepo.updateEstadoVenta(venta.getId(), venta.getEstado());
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
    }
    
    
    /**
     * Obtener total monetario de ventas del día
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
     * Verificar si existe una venta
     */
    public boolean existeVenta(int id) throws Exception {
        return getVentaById(id) != null;
    }
    
    public static class ResumenCocinaDTO {
        public List<Venta> comandas = new ArrayList<>();
        public int totalPendientes = 0;
        public int totalEnProceso = 0;
        .
    }

    /**
     * Obtiene el panel general para la pantalla del Cocinero
     */
    public ResumenCocinaDTO getDashboardCocina() throws Exception {
        ResumenCocinaDTO resumen = new ResumenCocinaDTO();
        List<DetalleVenta> pendientes = detalleVentaRepo.getDetallesPendientes();
        
        // Usamos un mapa para agrupar los detalles por ID de Venta
        
        // 1
        	// Hamburguesa
        	// Pizza
        
        Map<Integer, Venta> mapaVentas = new java.util.LinkedHashMap<>();
        
        for (DetalleVenta det : pendientes) {
            if (det.isEnProceso()) resumen.totalEnProceso++;
            else if (det.isPendiente()) resumen.totalPendientes++;

            Venta venta = mapaVentas.get(det.getVentaId());
            if (venta == null) {
                // Obtenemos los metadatos principales del ticket (sin traer todos los detalles)
                venta = ventaRepo.getVentaById(det.getVentaId()); 
                venta.setDetalles(new ArrayList<>()); // Inicializamos la lista vacía
                mapaVentas.put(det.getVentaId(), venta);
            }
            // Agregamos SOLO los platos pendientes a este ticket
            venta.getDetalles().add(det); 
        }
        
        resumen.comandas.addAll(mapaVentas.values());
        return resumen;
 
    }

    /**
     * Acciones rápidas para los botones de la interfaz
     */
    public void iniciarPreparacionPlato(int idDetalle) throws Exception {
        detalleVentaRepo.iniciarPreparacion(idDetalle);
    }

    public void completarPreparacionPlato(int idDetalle) throws Exception {
        detalleVentaRepo.completarPreparacion(idDetalle);
    }
}