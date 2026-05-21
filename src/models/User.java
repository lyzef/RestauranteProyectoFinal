package models;

public class User {
    
    private int id;
    private String contrasena;
    private String nombre, fechaNacimiento, curp, telefono, correo, NSS,rol, estadoCivil, genero;
    private String descripcionFunciones, tipoContrato, turno;
    private String alergiasConocidas, contactoEmergencia, tipoDeSangre, banco, numeroCuenta, sueldo;
    
    //De utilidad
    private boolean activo;
    private String ultimaSesion;

    // Constructor vacío
    public User() {}

    public User(String correo, char[] contrasena) {
        this.contrasena = new String(contrasena);
        this.correo = correo;
    }
    
    // Constructor session
    public User(int id, String correo, char[] contrasena,String rol) {
        this.id = id;
        this.contrasena = new String(contrasena);
        this.correo = correo;
        this.rol = rol;
    }
    
    // Constructor completo para formulario
    public User(String nombre, String fechaNacimiento, String curp, String telefono, String correo, String NSS,
                String estadoCivil, String genero, String rol, String descripcionFunciones, 
                String tipoContrato, String turno, String alergiasConocidas, 
                String contactoEmergencia, String tipoDeSangre, String banco, String numeroCuenta, String sueldo, String contrasena,boolean activo,String ultimaSesion ) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.curp = curp;
        this.telefono = telefono;
        this.correo = correo;
        this.estadoCivil = estadoCivil;
        this.genero = genero;
        this.rol = rol;
        this.descripcionFunciones = descripcionFunciones;
        this.tipoContrato = tipoContrato;
        this.turno = turno;
        this.NSS = NSS;
        this.alergiasConocidas = alergiasConocidas;
        this.contactoEmergencia = contactoEmergencia;
        this.tipoDeSangre = tipoDeSangre;
        this.banco = banco;
        this.numeroCuenta = numeroCuenta;
        this.sueldo = sueldo;
        this.contrasena = contrasena;
        this.activo = activo;
        this.ultimaSesion = ultimaSesion;
        }

    // --- GETTERS Y SETTERS ---
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    public String getFechaNacimiento() { 
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(String fechaNacimiento) { 
        this.fechaNacimiento = fechaNacimiento; 
    }
    
    public String getCurp() { 
        return curp; 
    }
    
    public void setCurp(String curp) { 
        this.curp = curp; 
    }
    
    public String getTelefono() { 
        return telefono;
    }
    
    public void setTelefono(String telefono) { 
        this.telefono = telefono;
    }
    
    public String getCorreo() { 
        return correo; 
    }
    
    public void setCorreo(String correo) { 
        this.correo = correo;
    }
    
    public String getEstadoCivil() { 
        return estadoCivil;
    }
    
    public void setEstadoCivil(String estadoCivil) { 
        this.estadoCivil = estadoCivil;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) { 
        this.genero = genero; 
    }
    
    
    
    public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getDescripcionFunciones() { 
        return descripcionFunciones;
    }
    
    public void setDescripcionFunciones(String descripcionFunciones) { 
        this.descripcionFunciones = descripcionFunciones;
    }

    public String getTipoContrato() { 
        return tipoContrato; 
    }
    
    public void setTipoContrato(String tipoContrato) { 
        this.tipoContrato = tipoContrato;
    }
    
    public String getTurno() { 
        return turno;
    }
    
    public void setTurno(String turno) { 
        this.turno = turno; 
    }
    
    public String getNSS() { 
        return NSS;
    }
    
    public void setNSS(String nSS) { 
        this.NSS = nSS; 
    }
    
    public String getAlergiasConocidas() { 
        return alergiasConocidas;
    }
    
    public void setAlergiasConocidas(String alergiasConocidas) { 
        this.alergiasConocidas = alergiasConocidas;
    }
    
    public String getContactoEmergencia() { 
        return contactoEmergencia;
    }
    
    public void setContactoEmergencia(String contactoEmergencia) { 
        this.contactoEmergencia = contactoEmergencia;
    }
    
    public String getTipoDeSangre() { 
        return tipoDeSangre;
    }
    
    public void setTipoDeSangre(String tipoDeSangre) { 
        this.tipoDeSangre = tipoDeSangre; 
    }
    
    public String getBanco() { 
        return banco;
    }
    
    public void setBanco(String banco) {
        this.banco = banco;
    }
    
    public String getNumeroCuenta() { 
        return numeroCuenta;
    }
    
    public void setNumeroCuenta(String numeroCuenta) { 
        this.numeroCuenta = numeroCuenta;
    }
    
    public String getSueldo() { 
        return sueldo; 
    }
    
    public void setSueldo(String sueldo) { 
        this.sueldo = sueldo;
    }

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getUltimaSesion() {
		return ultimaSesion;
	}

	public void setUltimaSesion(String ultimaSesion) {
		this.ultimaSesion = ultimaSesion;
	}
	
	
    
    
}
