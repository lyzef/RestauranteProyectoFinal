package models;

import java.sql.Timestamp;

public class DetalleVenta {
    
    
    // Atributos
    private Integer id;
    private Integer ventaId;
    private Integer componenteId;
    private Integer cantidad;
    private Double precioUnitarioAplicado;
    private Double subtotal;
    private EstadoCocina estadoCocina;
    private Urgencia urgencia;
    private Timestamp fechaHoraInicio;
    private Timestamp fechaHoraFin;
    
    private String componenteNombre;
    
    public DetalleVenta() {
        this.cantidad = 1;
        this.estadoCocina = EstadoCocina.PENDIENTE;
        this.urgencia = Urgencia.NORMAL;
    }
    
    public DetalleVenta(Integer ventaId, Integer componenteId, Integer cantidad, 
                        Double precioUnitarioAplicado, Double subtotal) {
        this.ventaId = ventaId;
        this.componenteId = componenteId;
        this.cantidad = cantidad != null ? cantidad : 1;
        this.precioUnitarioAplicado = precioUnitarioAplicado;
        this.subtotal = subtotal;
        this.estadoCocina = EstadoCocina.PENDIENTE;
        this.urgencia = Urgencia.NORMAL;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getVentaId() {
        return ventaId;
    }
    
    public void setVentaId(Integer ventaId) {
        this.ventaId = ventaId;
    }
    
    public Integer getComponenteId() {
        return componenteId;
    }
    
    public void setComponenteId(Integer componenteId) {
        this.componenteId = componenteId;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public Double getPrecioUnitarioAplicado() {
        return precioUnitarioAplicado;
    }
    
    public void setPrecioUnitarioAplicado(Double precioUnitarioAplicado) {
        this.precioUnitarioAplicado = precioUnitarioAplicado;
    }
    
    public Double getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    
    public EstadoCocina getEstadoCocina() {
        return estadoCocina;
    }
    
    public void setEstadoCocina(EstadoCocina estadoCocina) {
        this.estadoCocina = estadoCocina;
    }
    
    public void setEstadoCocina(String estadoCocina) {
        this.estadoCocina = EstadoCocina.fromString(estadoCocina);
    }
    
    public Urgencia getUrgencia() {
        return urgencia;
    }
    
    public void setUrgencia(Urgencia urgencia) {
        this.urgencia = urgencia;
    }
    
    public void setUrgencia(String urgencia) {
        this.urgencia = Urgencia.fromString(urgencia);
    }
    
    public Timestamp getFechaHoraInicio() {
        return fechaHoraInicio;
    }
    
    public void setFechaHoraInicio(Timestamp fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }
    
    public Timestamp getFechaHoraFin() {
        return fechaHoraFin;
    }
    
    public void setFechaHoraFin(Timestamp fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }
    
    public String getComponenteNombre() {
        return componenteNombre;
    }
    
    public void setComponenteNombre(String componenteNombre) {
        this.componenteNombre = componenteNombre;
    }
    
    public void calcularSubtotal() {
        if (cantidad != null && precioUnitarioAplicado != null) {
            this.subtotal = cantidad * precioUnitarioAplicado;
        }
    }
    
    public boolean isCompletado() {
        return estadoCocina == EstadoCocina.COMPLETADO;
    }
    
    public boolean isCancelado() {
        return estadoCocina == EstadoCocina.CANCELADO;
    }
    
    public boolean isEnProceso() {
        return estadoCocina == EstadoCocina.EN_PROCESO;
    }
    
    public boolean isPendiente() {
        return estadoCocina == EstadoCocina.PENDIENTE;
    }
    
    public enum EstadoCocina {
        PENDIENTE, 
        EN_PROCESO, 
        COMPLETADO, 
        CANCELADO;
        
        public static EstadoCocina fromString(String texto) {
            if (texto == null) return PENDIENTE;
            for (EstadoCocina e : EstadoCocina.values()) {
                if (e.name().equalsIgnoreCase(texto)) {
                    return e;
                }
            }
            return PENDIENTE;
        }
    }
    
    public enum Urgencia {
        NORMAL, 
        ALTA;
        
        public static Urgencia fromString(String texto) {
            if (texto == null) return NORMAL;
            for (Urgencia u : Urgencia.values()) {
                if (u.name().equalsIgnoreCase(texto)) {
                    return u;
                }
            }
            return NORMAL;
        }
    }
    
}
