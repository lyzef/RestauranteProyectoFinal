package models;

public class Componente {

    private Integer id;
    private String nombre;
    private boolean esReceta;
    private String tipoComponente;
    private String unidadMedida;
    private double costoUnitario;
    private double caloriasPorUnidad;
    private double stockActual;
    private double stockMinimoBloqueo;
    private double stockMinimoAlerta;
    private boolean disponibilidadManual;
    private boolean esInventariable;
    private Integer categoriaId;

    public Componente() {
    }

    public Componente(Integer id, String nombre, boolean esReceta, String tipoComponente, 
                      String unidadMedida, double costoUnitario, double caloriasPorUnidad, 
                      double stockActual, double stockMinimoBloqueo, double stockMinimoAlerta, 
                      boolean disponibilidadManual, boolean esInventariable, Integer categoriaId) {
        this.id = id;
        this.nombre = nombre;
        this.esReceta = esReceta;
        this.tipoComponente = tipoComponente;
        this.unidadMedida = unidadMedida;
        this.costoUnitario = costoUnitario;
        this.caloriasPorUnidad = caloriasPorUnidad;
        this.stockActual = stockActual;
        this.stockMinimoBloqueo = stockMinimoBloqueo;
        this.stockMinimoAlerta = stockMinimoAlerta;
        this.disponibilidadManual = disponibilidadManual;
        this.esInventariable = esInventariable;
        this.categoriaId = categoriaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEsReceta() {
        return esReceta;
    }

    public void setEsReceta(boolean esReceta) {
        this.esReceta = esReceta;
    }

    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public double getCaloriasPorUnidad() {
        return caloriasPorUnidad;
    }

    public void setCaloriasPorUnidad(double caloriasPorUnidad) {
        this.caloriasPorUnidad = caloriasPorUnidad;
    }

    public double getStockActual() {
        return stockActual;
    }

    public void setStockActual(double stockActual) {
        this.stockActual = stockActual;
    }

    public double getStockMinimoBloqueo() {
        return stockMinimoBloqueo;
    }

    public void setStockMinimoBloqueo(double stockMinimoBloqueo) {
        this.stockMinimoBloqueo = stockMinimoBloqueo;
    }

    public double getStockMinimoAlerta() {
        return stockMinimoAlerta;
    }

    public void setStockMinimoAlerta(double stockMinimoAlerta) {
        this.stockMinimoAlerta = stockMinimoAlerta;
    }

    public boolean isDisponibilidadManual() {
        return disponibilidadManual;
    }

    public void setDisponibilidadManual(boolean disponibilidadManual) {
        this.disponibilidadManual = disponibilidadManual;
    }

    public boolean isEsInventariable() {
        return esInventariable;
    }

    public void setEsInventariable(boolean esInventariable) {
        this.esInventariable = esInventariable;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }
}
