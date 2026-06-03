package models;

public class Platillo {

    // Atributos
    private Integer id;
    private Integer componenteId;
    private Integer categoriaId;
    private String descripcion;
    private String imagenUrl;
    private Emblema emblema = Emblema.SUPER;
    private Double precioVenta;
    
    //Atributos extras
    private String componenteNombre = "";
    private String categoriaNombre = "";
    private Double calorias = (double) 67;
    
    // Constructor vacío
    public Platillo() {
    }

    // Constructor con parámetros
    public Platillo(Integer id, Integer componenteId, Integer categoriaId, String descripcion, String imagenUrl, Double precioVenta, Emblema emblema) {
        this.id = id;
        this.componenteId = componenteId;
        this.categoriaId = categoriaId;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.precioVenta = precioVenta;
        this.emblema = emblema;
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

	public Double getCalorias() {
		return calorias;
	}

	public void setCalorias(Double calorias) {
		this.calorias = calorias;
	}
    
	
	
	public Emblema getEmblema() {
		return emblema;
	}

	public void setEmblema(Emblema emblema) {
		this.emblema = emblema;
	}



	public enum Emblema {
	    FAMILIAR("FAMILIAR"),
	    FAVORITOS("FAVORITOS"),
	    MEJOR_CALIFICADOS("MEJOR CALIFICADOS"),
	    NUEVO("NUEVO"),
	    COMBO("COMBO"),
	    DE_TEMPORADA("DE TEMPORADA"),
	    VEGANO("VEGANO"),
	    SIN_GLUTEN("SIN GLUTEN"),
	    INFANTIL("INFANTIL"),
	    SUPER("SUPER");

	    private final String valorBaseDatos;

	    Emblema(String valorBaseDatos) {
	        this.valorBaseDatos = valorBaseDatos;
	    }

	    public String getValorBaseDatos() { // EN BASE DE DATOS NO SE USA _ 
	        return valorBaseDatos;
	    }

	    public static Emblema desdeString(String texto) {
	        if (texto == null) {
	            return SUPER;
	        }
	        for (Emblema b : Emblema.values()) {
	            if (b.valorBaseDatos.equalsIgnoreCase(texto)) {
	                return b;
	            }
	        }
	        throw new IllegalArgumentException("Ningún emblema válido encontrado para: " + texto);
	    }
	}
    
}
