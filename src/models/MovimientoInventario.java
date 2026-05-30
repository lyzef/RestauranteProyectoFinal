package models;

import tableFormat.filtros.MovimientoTextFilterator.TipoFiltroMovimiento;

public class MovimientoInventario {
    private int id;
    private int componente_id;
    private String componente_nombre;
    private tipoMovimiento tipo_movimiento;
    private double cantidad;
    private double costo_movimiento;
    private String motivo;
    private String fecha;
    
    public MovimientoInventario() {
    }
    
    public MovimientoInventario(int id, int componente_id, String componente_nombre, 
                               tipoMovimiento tipo_movimiento, double cantidad, 
                               double costo_movimiento, String motivo, String fecha) {
        this.id = id;
        this.componente_id = componente_id;
        this.componente_nombre = componente_nombre;
        this.tipo_movimiento = tipo_movimiento;
        this.cantidad = cantidad;
        this.costo_movimiento = costo_movimiento;
        this.motivo = motivo;
        this.fecha = fecha;
    }
    
    public MovimientoInventario(int componente_id, String componente_nombre, 
                               tipoMovimiento tipo_movimiento, double cantidad, 
                               double costo_movimiento, String motivo) {
        this.componente_id = componente_id;
        this.componente_nombre = componente_nombre;
        this.tipo_movimiento = tipo_movimiento;
        this.cantidad = cantidad;
        this.costo_movimiento = costo_movimiento;
        this.motivo = motivo;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getComponente_id() {
        return componente_id;
    }
    
    public void setComponente_id(int componente_id) {
        this.componente_id = componente_id;
    }
    
    public String getComponente_nombre() {
        return componente_nombre;
    }
    
    public void setComponente_nombre(String componente_nombre) {
        this.componente_nombre = componente_nombre;
    }
    
    public tipoMovimiento getTipo_movimiento() {
        return tipo_movimiento;
    }
    
    public void setTipo_movimiento(tipoMovimiento tipo_movimiento) {
    	this.tipo_movimiento = tipo_movimiento;
    }
    
    public double getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
    public double getCosto_movimiento() {
        return costo_movimiento;
    }
    
    public void setCosto_movimiento(double costo_movimiento) {
        this.costo_movimiento = costo_movimiento;
    }
    
    public String getMotivo() {
        return motivo;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    @Override
    public String toString() {
        return "MovimientoInventario{" +
               "id=" + id +
               ", componente_id=" + componente_id +
               ", componente_nombre='" + componente_nombre + '\'' +
               ", tipo_movimiento='" + tipo_movimiento + '\'' +
               ", cantidad=" + cantidad +
               ", costo_movimiento=" + costo_movimiento +
               ", motivo='" + motivo + '\'' +
               ", fecha='" + fecha + '\'' +
               '}';
    }
    
    public enum tipoMovimiento {
        ENTRADA("Entrada"),
        SALIDA("Salida"), 
        AJUSTE("Ajuste");

        private final String nombre;

        tipoMovimiento(String nombre) {
            this.nombre = nombre;
        }
        
        public static tipoMovimiento fromString(String text) {
            for (tipoMovimiento t : tipoMovimiento.values()) {
                if (t.nombre.equalsIgnoreCase(text)) {
                    return t;
                }
            }
            return null; 
        }
        
        @Override
        public String toString() {
            return nombre; 
        }
    }
}
