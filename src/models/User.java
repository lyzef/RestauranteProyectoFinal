package models;

import java.util.*;

import javax.swing.ButtonGroup;

import utilidades.PanelTipoPreguntaUtil;

public class User {
    String nombre;
    String fechaNacimiento;
    String curp;
    String telefono;
    String correo;
    String estadoCivil;
    String genero;
    //
    String puestoActual;
    String descripcionFunciones;
    String perfilPuesto;
    String condicionesLaborales;
    String ubicacionOrganizacional;
    String tipoContrato;
    String turno;
    //
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
                String contactoEmergencia,String tipoDeSangre, String banco, String numeroCuenta, String sueldo) {
        
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
            nombre,
            fechaNacimiento,
            curp,
            telefono,
            correo,
            estadoCivil,
            genero,
            puestoActual,
            descripcionFunciones,
            perfilPuesto,
            condicionesLaborales,
            ubicacionOrganizacional,
            tipoContrato,
            turno,
            NSS,
            alergiasConocidas,
            contactoEmergencia,
            tipoDeSangre,
            banco,
            numeroCuenta,
            sueldo
        );
    }
    
    public static User fromCSV(String linea) {
        String[] datos = linea.split(",");
        if (datos.length >= 21) {
            return new User(
                datos[0],  // nombre
                datos[1],  // fechaNacimiento
                datos[2],  // curp
                datos[3],  // telefono
                datos[4],  // correo
                datos[5],  // estadoCivil
                datos[6],  // genero
                datos[7],  // puestoActual
                datos[8],  // descripcionFunciones
                datos[9],  // perfilPuesto
                datos[10], // condicionesLaborales
                datos[11], // ubicacionOrganizacional
                datos[12], // tipoContrato
                datos[13], // turno
                datos[14], // NSS
                datos[15], // alergiasConocidas
                datos[16], // contactoEmergencia
                datos[17],  // tipoSangre
                datos[18], // banco
                datos[19], // numeroCuenta
                datos[20]  // sueldo
            );
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "=== DATOS DEL USUARIO ===\n" +
               "[PERSONALES]\n" +
               "Nombre: " + nombre + "\n" +
               "Fecha Nacimiento: " + fechaNacimiento + "\n" +
               "CURP: " + curp + "\n" +
               "Teléfono: " + telefono + "\n" +
               "Correo: " + correo + "\n" +
               "Estado Civil: " + estadoCivil + "\n" +
               "Género: " + genero + "\n\n" +
               
               "[LABORALES]\n" +
               "Puesto Actual: " + puestoActual + "\n" +
               "Funciones: " + descripcionFunciones + "\n" +
               "Perfil: " + perfilPuesto + "\n" +
               "Condiciones: " + condicionesLaborales + "\n" +
               "Ubicación: " + ubicacionOrganizacional + "\n" +
               "Contrato: " + tipoContrato + "\n" +
               "Turno: " + turno + "\n\n" +
               
               "[MEDICOS Y BANCARIOS]\n" +
               "NSS: " + NSS + "\n" +
               "Alergias: " + alergiasConocidas + "\n" +
               "Contacto Emergencia: " + contactoEmergencia + "\n" +
               "Tipo Sangre: " + tipoDeSangre + "\n" +
               "Banco: " + banco + "\n" +
               "No. Cuenta: " + numeroCuenta + "\n" +
               "Sueldo: " + sueldo + "\n" +
               "========================";
    }
    
}