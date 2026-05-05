package models;

import java.util.*;

public class User {

    String nombre;
    String fechaNacimiento;
    String curp;
    String telefono;
    String correo;
    String estadoCivil;
    String genero;
    String puestoActual;
    String descripcionFunciones;
    String perfilPuesto;
    String condicionesLaborales;
    String ubicacionOrganizacional;
    String tipoContrato;
    String turno;
    String NSS;
    String alergiasConocidas;
    String contactoEmergencia;
    String tipoDeSangre;
    String banco;
    String numeroCuenta;
    String sueldo;

    public User() {
    }

    public User(String correo, char[] contrasena) {
        this.correo = correo;
    }
    
    public User(String nombre, String fechaNacimiento, String curp, String telefono, String correo, 
                String estadoCivil, String genero, String puestoActual, String descripcionFunciones, 
                String perfilPuesto, String condicionesLaborales, String ubicacionOrganizacional, 
                String tipoContrato, String turno, String NSS, String alergiasConocidas, 
                String contactoEmergencia, String tipoDeSangre, String banco, String numeroCuenta, String sueldo) {
        
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.curp = curp;
        this.telefono = telefono;
        this.correo = correo;
        this.estadoCivil = estadoCivil;
        this.genero = genero;
        this.puestoActual = puestoActual;
        this.descripcionFunciones = descripcionFunciones;
        this.perfilPuesto = perfilPuesto;
        this.condicionesLaborales = condicionesLaborales;
        this.ubicacionOrganizacional = ubicacionOrganizacional;
        this.tipoContrato = tipoContrato;
        this.turno = turno;
        this.NSS = NSS;
        this.alergiasConocidas = alergiasConocidas;
        this.contactoEmergencia = contactoEmergencia;
        this.tipoDeSangre = tipoDeSangre;
        this.banco = banco;
        this.numeroCuenta = numeroCuenta;
        this.sueldo = sueldo;
    }
    
    public String toCSV() {
        return String.join(",", 
            (nombre != null ? nombre : ""),
            (fechaNacimiento != null ? fechaNacimiento : ""),
            (curp != null ? curp : ""),
            (telefono != null ? telefono : ""),
            (correo != null ? correo : ""),
            (estadoCivil != null ? estadoCivil : ""),
            (genero != null ? genero : ""),
            (puestoActual != null ? puestoActual : ""),
            (descripcionFunciones != null ? descripcionFunciones : ""),
            (perfilPuesto != null ? perfilPuesto : ""),
            (condicionesLaborales != null ? condicionesLaborales : ""),
            (ubicacionOrganizacional != null ? ubicacionOrganizacional : ""),
            (tipoContrato != null ? tipoContrato : ""),
            (turno != null ? turno : ""),
            (NSS != null ? NSS : ""),
            (alergiasConocidas != null ? alergiasConocidas : ""),
            (contactoEmergencia != null ? contactoEmergencia : ""),
            (tipoDeSangre != null ? tipoDeSangre : ""),
            (banco != null ? banco : ""),
            (numeroCuenta != null ? numeroCuenta : ""),
            (sueldo != null ? sueldo : "")
        );
    }
    
    public static User fromCSV(String linea) {
        if (linea == null || linea.trim().isEmpty()) return null;

        String[] datos = linea.split(",", -1);
        
        String[] campos = new String[21];
        Arrays.fill(campos, ""); 
        
        for (int i = 0; i < Math.min(datos.length, 21); i++) {
            campos[i] = datos[i].trim();
        }

        return new User(
            campos[0], 
            campos[1], 
            campos[2],
            campos[3],
            campos[4],
            campos[5],
            campos[6],
            campos[7],
            campos[8],
            campos[9],
            campos[10],
            campos[11],
            campos[12],
            campos[13],
            campos[14],
            campos[15],
            campos[16],
            campos[17],
            campos[18],
            campos[19],
            campos[20]
        );
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
    public String getPuestoActual() {
    	return puestoActual; 
    	}
    public void setPuestoActual(String puestoActual) {
    	this.puestoActual = puestoActual; 
    	}
    public String getDescripcionFunciones() {
    	return descripcionFunciones; 
    	}
    public void setDescripcionFunciones(String descripcionFunciones) {
    	this.descripcionFunciones = descripcionFunciones; 
    	}
    public String getPerfilPuesto() { 
    	return perfilPuesto; 
    	}
    public void setPerfilPuesto(String perfilPuesto) {
    	this.perfilPuesto = perfilPuesto; 
    	}
    public String getCondicionesLaborales() { 
    	return condicionesLaborales; 
    	}
    public void setCondicionesLaborales(String condicionesLaborales) {
    	this.condicionesLaborales = condicionesLaborales;
    	}
    public String getUbicacionOrganizacional() {
    	return ubicacionOrganizacional; 
    	}
    public void setUbicacionOrganizacional(String ubicacionOrganizacional) { 
    	this.ubicacionOrganizacional = ubicacionOrganizacional;
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
    public String getTipoDeSangre() { return tipoDeSangre; }
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
}