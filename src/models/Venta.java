package models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Calendar;

public class Venta {
    private int id;
    private int usuarioID;
    private Timestamp fechaHora;
    private double totalVenta;
    private tipoMetodoPago metodoPago;
    private tipoPedido tipoPedidoVenta;
    private String estado;
    
    private String nombreUsuario;
    private int cantidadProductos; 
    private int cantidadUnidades;
    
    //Como los detalles no se modifican (Como en el grafo de recetas)
    //si puedo amarrarlos a la clase venta
    private List<DetalleVenta> detalles;
    
    public Venta() {
        
    }
    
    public Venta(int id, int usuarioID, Timestamp fechaHora, double totalVenta, 
                 tipoMetodoPago metodoPago, tipoPedido tipoPedidoVenta, String estado) {
        this.id = id;
        this.usuarioID = usuarioID;
        this.fechaHora = redondearMilisegundos(fechaHora);
        this.totalVenta = totalVenta;
        this.metodoPago = metodoPago;
        this.tipoPedidoVenta = tipoPedidoVenta;
        this.estado = estado;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUsuarioID() {
        return usuarioID;
    }
    
    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }
    
    public Timestamp getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = redondearMilisegundos(fechaHora);
    }
    
    // Setter con LocalDateTime (más moderno)
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = redondearMilisegundos(Timestamp.valueOf(fechaHora));
    }
    
    // Getter como LocalDateTime
    public LocalDateTime getFechaHoraAsLocalDateTime() {
        return fechaHora != null ? fechaHora.toLocalDateTime() : null;
    }
    
    public double getTotalVenta() {
        return totalVenta;
    }
    
    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }
    
    public tipoMetodoPago getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(tipoMetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
    
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = tipoMetodoPago.fromString(metodoPago);
    }
    
    public tipoPedido getTipoPedidoVenta() {
        return tipoPedidoVenta;
    }
    
    public void setTipoPedidoVenta(tipoPedido tipoPedidoVenta) {
        this.tipoPedidoVenta = tipoPedidoVenta;
    }
    
    public void setTipoPedidoVenta(String tipoPedidoVenta) {
        this.tipoPedidoVenta = tipoPedido.fromString(tipoPedidoVenta);
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public int getCantidadProductos() {
        return cantidadProductos;
    }
    
    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }
    
    public int getCantidadUnidades() {
        return cantidadUnidades;
    }

    public void setCantidadUnidades(int cantidadUnidades) {
        this.cantidadUnidades = cantidadUnidades;
    }
    
    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
    
    /**
     * Redondea solo los milisegundos a 0
     * Ejemplo: 2024-01-15 14:30:45.123 -> 2024-01-15 14:30:45.000
     */
    private Timestamp redondearMilisegundos(Timestamp timestamp) {
        if (timestamp == null) return null;
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.set(Calendar.MILLISECOND, 0);
        
        return new Timestamp(cal.getTimeInMillis());
    }
    
    // Métodos útiles
    public boolean isPagado() {
        return "PAGADO".equalsIgnoreCase(estado);
    }
    
    public boolean isCancelado() {
        return "CANCELADO".equalsIgnoreCase(estado);
    }
    
    public String getFechaHoraFormateada() {
        if (fechaHora == null) return "";
        return fechaHora.toLocalDateTime().toString().replace("T", " ");
    }
       
    // Enums
    public enum tipoMetodoPago {
        EFECTIVO("EFECTIVO"),
        TARJETA("TARJETA"),
        TRANSFERENCIA("TRANSFERENCIA");

        private final String valorDB;
        
        tipoMetodoPago(String valorDB) {
            this.valorDB = valorDB;
        }
        
        public String getValorDB() {
            return valorDB;
        }
        
        public static tipoMetodoPago fromString(String text) {
            if (text == null) return null;
            for (tipoMetodoPago t : tipoMetodoPago.values()) {
                if (t.valorDB.equalsIgnoreCase(text) || t.name().equalsIgnoreCase(text)) {
                    return t;
                }
            }
            return null;
        }
        
        @Override
        public String toString() {
            return valorDB;
        }
    }
    
    public enum tipoPedido {
        COMER_AQUI("COMER_AQUI"),
        LLEVAR("LLEVAR");
        
        private final String valorDB;
        
        tipoPedido(String valorDB) {
            this.valorDB = valorDB;
        }
        
        public String getValorDB() {
            return valorDB;
        }
        
        public static tipoPedido fromString(String text) {
            if (text == null) return null;
            for (tipoPedido t : tipoPedido.values()) {
                if (t.valorDB.equalsIgnoreCase(text) || t.name().equalsIgnoreCase(text)) {
                    return t;
                }
            }
            return null;
        }
        
        @Override
        public String toString() {
            return valorDB;
        }
    }
}