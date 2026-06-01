package models;

public class Platillo {

    // Atributos
    private Integer id;
    private Integer componenteId;
    private Integer categoriaId;
    private String descripcion;
    private String imagenUrl;
    private Double precioVenta;
    
    //Atributos extras
    private String componenteNombre = "";
    private String categoriaNombre = "";
    
    // Constructor vacío
    public Platillo() {
    }

    // Constructor con parámetros
    public Platillo(Integer id, Integer componenteId, Integer categoriaId, String descripcion, String imagenUrl, Double precioVenta) {
        this.id = id;
        this.componenteId = componenteId;
        this.categoriaId = categoriaId;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.precioVenta = precioVenta;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComponenteId() {
        return componenteId;
    }

    public void setComponenteId(Integer componenteId) {
        this.componenteId = componenteId;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

	public String getComponenteNombre() {
		return componenteNombre;
	}

	public String getCategoriaNombre() {
		return categoriaNombre;
	}

	public void setComponenteNombre(String componenteNombre) {
		this.componenteNombre = componenteNombre;
	}

	public void setCategoriaNombre(String categoriaNombre) {
		this.categoriaNombre = categoriaNombre;
	}
    
    
}
