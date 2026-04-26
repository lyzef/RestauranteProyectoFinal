package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import controller.PreguntaController;

import java.util.*;
import javax.swing.*;
import models.User;
import utilidades.PanelTipoPreguntaUtil;
import utilidades.ValidadorCadena;

public class UserFormDialog extends JDialog {
    JPanel panelContenedorCentral;
    User usuario;
    private boolean saved = false;
    
    // Parte 1
    PanelTipoPreguntaUtil nombre, fechaNacimiento, curp, telefono, correo;
    // Parte 2
    PanelTipoPreguntaUtil puestoActual, descripcionFunciones, perfilPuesto, condicionesLaborales, ubicacionOrganizacional, tipoContrato;
    // Parte 3
    PanelTipoPreguntaUtil nss, alergiasConocidas, contactoEmergencia, banco, numeroCuenta, sueldo;

    // Todas las preguntas 
    List<PanelTipoPreguntaUtil> listaPreguntas;

    // Otros 
    JComboBox<String> estadoCivil, generos, tipoSangre;
    ButtonGroup radioTurno;
    JButton botonCancelar, botonGuardar;

    public UserFormDialog(JFrame parent, User usuario) {
    	super(parent,true);
    	this.usuario = usuario;
    	
    	setSize(400,400);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(true);
		setTitle("Formulario");
		setLocationRelativeTo(null);
		
        inicializarComponentes();
        setVisible(true);
    }
    
    

    private void inicializarComponentes() {
        listaPreguntas = new ArrayList<>(); 

        JPanel panelSuperior = new JPanel();
        panelContenedorCentral = new JPanel(new BorderLayout());
        JPanel panelInferior = new JPanel();

        // Configuración de Título
        JLabel lblTitulo = new JLabel(usuario == null ? "Agregar usuario" : "Editar usuario" );
        lblTitulo.setFont(new Font("Times", Font.PLAIN, 17));
        panelSuperior.add(lblTitulo);
        
        // Botón cancelar
        botonCancelar = new JButton("Cancelar");
        botonCancelar.setBackground(new Color(255, 25, 45));
        panelInferior.add(botonCancelar);
        
        // Botón Siguiente
        botonGuardar = new JButton("Guardar");
        botonGuardar.setBackground(new Color(144, 224, 239));
        panelInferior.add(botonGuardar);
        
     

        // Crear y añadir el formulario (con scroll)
        panelContenedorCentral.add(crearFormularioCompleto(), BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelContenedorCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        
        loadData();
        
        botonGuardar.addActionListener(e -> save());
        botonCancelar.addActionListener(e -> dispose());
    }

    private JScrollPane crearFormularioCompleto() {
        JPanel panelCuestionario = new JPanel();
        panelCuestionario.setLayout(new BoxLayout(panelCuestionario, BoxLayout.Y_AXIS));
        panelCuestionario.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Parte 1
        nombre = new PanelTipoPreguntaUtil("Nombre", "ALFABETICO");
        fechaNacimiento = new PanelTipoPreguntaUtil("Fecha de nacimiento", "FECHA");
        curp = new PanelTipoPreguntaUtil("Curp", "ALFANUMERICO");
        telefono = new PanelTipoPreguntaUtil("Telefono", "NUMERICO");
        correo = new PanelTipoPreguntaUtil("Correo", "CORREO");

        // Parte 2
        puestoActual = new PanelTipoPreguntaUtil("Puesto actual:", "ALFANUMERICO");
        descripcionFunciones = new PanelTipoPreguntaUtil("Funciones", "ALFANUMERICO");
        perfilPuesto = new PanelTipoPreguntaUtil("Perfil de puesto:", "ALFANUMERICO");
        condicionesLaborales = new PanelTipoPreguntaUtil("Condiciones:", "ALFANUMERICO");
        ubicacionOrganizacional = new PanelTipoPreguntaUtil("Ubicación:", "ALFANUMERICO");
        tipoContrato = new PanelTipoPreguntaUtil("Tipo contrato:", "ALFANUMERICO");
        
        // Parte 3
        nss = new PanelTipoPreguntaUtil("NSS:", "ALFANUMERICO");
        alergiasConocidas = new PanelTipoPreguntaUtil("Alergias:", "ALFANUMERICO");
        contactoEmergencia = new PanelTipoPreguntaUtil("Contacto Emergencia:", "ALFANUMERICO");
        banco = new PanelTipoPreguntaUtil("Banco:", "ALFANUMERICO");
        numeroCuenta = new PanelTipoPreguntaUtil("Cuenta/CLABE:", "NUMERICO");
        sueldo = new PanelTipoPreguntaUtil("Sueldo:", "NUMERICO");
        

        //Anadir todos a lista
        Collections.addAll(listaPreguntas, 
            nombre, fechaNacimiento, curp, telefono, correo,
            puestoActual, descripcionFunciones, perfilPuesto, condicionesLaborales, ubicacionOrganizacional, tipoContrato,
            nss, alergiasConocidas, contactoEmergencia, banco, numeroCuenta, sueldo
        );

        // Anadir todos a panel
        for (PanelTipoPreguntaUtil p : listaPreguntas) {
            panelCuestionario.add(p);
        }
        
        conectarPreguntasAsuControlador(listaPreguntas);
        
        JLabel lblSangre = new JLabel("Tipo de sangre");
        panelCuestionario.add(lblSangre);
		String[] opcionesSangre = {"Seleccionar","O-","O+","B-","B+","A-","A+","AB+","AB-"};
		tipoSangre = new JComboBox<String>(opcionesSangre);
		tipoSangre.setSelectedIndex(0);
		panelCuestionario.add(tipoSangre);
		tipoSangre.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
		
		//Turno
		JLabel lblTurno = new JLabel("Turno");
        panelCuestionario.add(lblTurno);
        radioTurno = new ButtonGroup();
        
        JRadioButton rbMatutino = new JRadioButton("Matutino"); 
        panelCuestionario.add(rbMatutino);
        rbMatutino.setActionCommand("Matutino");
        
        JRadioButton rbVespertino = new JRadioButton("Vespertino"); 
        panelCuestionario.add(rbVespertino);
        rbVespertino.setActionCommand("Vespertino");
        
        JRadioButton rbMixto = new JRadioButton("Mixto"); 
        panelCuestionario.add(rbMixto);
        rbMixto.setActionCommand("Mixto");
        
        radioTurno.add(rbMatutino);radioTurno.add(rbVespertino);radioTurno.add(rbMixto);
       
        //Estado civil
		JLabel lblEstadoCivil = new JLabel("EstadoCivil ");
		panelCuestionario.add(lblEstadoCivil);
		String[] opcionesEstadoCivil = {"Seleccionar","Soltero","Casado","Union libre", "Viudo"};
		estadoCivil = new JComboBox<String>(opcionesEstadoCivil);
		estadoCivil.setSelectedIndex(0);
		panelCuestionario.add(estadoCivil);
		
		//Genero
		JLabel lblGenero = new JLabel("Genero ");
        panelCuestionario.add(lblGenero);
		String[] opcionesGenero = {"Seleccionar","Hombre", "Mujer","Therian","Otro"};
		generos = new JComboBox<String>(opcionesGenero);
		generos.setSelectedIndex(0); //Item preseleccionado
		panelCuestionario.add(generos);

        return new JScrollPane(panelCuestionario);
    }
    
    public void conectarPreguntasAsuControlador(List <PanelTipoPreguntaUtil> preguntas ) {
		for(PanelTipoPreguntaUtil p : preguntas) {
			PreguntaController.registrarPanel(p);
		}
	}
    
    private void loadData() {
        if (usuario != null) {
            // --- Parte 1: Datos Personales ---
            nombre.getTxtEntrada().setText(usuario.getNombre());
            fechaNacimiento.getTxtEntrada().setText(usuario.getFechaNacimiento());
            curp.getTxtEntrada().setText(usuario.getCurp());
            telefono.getTxtEntrada().setText(usuario.getTelefono());
            correo.getTxtEntrada().setText(usuario.getCorreo());
            
            // ComboBoxes (Género y Estado Civil)
            generos.setSelectedItem(usuario.getGenero());
            estadoCivil.setSelectedItem(usuario.getEstadoCivil());

            // --- Parte 2: Datos Laborales ---
            puestoActual.getTxtEntrada().setText(usuario.getPuestoActual());
            descripcionFunciones.getTxtEntrada().setText(usuario.getDescripcionFunciones());
            perfilPuesto.getTxtEntrada().setText(usuario.getPerfilPuesto());
            condicionesLaborales.getTxtEntrada().setText(usuario.getCondicionesLaborales());
            ubicacionOrganizacional.getTxtEntrada().setText(usuario.getUbicacionOrganizacional());
            tipoContrato.getTxtEntrada().setText(usuario.getTipoContrato());

            // Manejo de RadioButtons para el Turno
            if (usuario.getTurno() != null) {
                String turno = usuario.getTurno();
                Enumeration<AbstractButton> buttons = radioTurno.getElements();
                while (buttons.hasMoreElements()) {
                	JRadioButton button = (JRadioButton) buttons.nextElement();
                    if (button.getText().equals(turno)) {
                        button.setSelected(true);
                        break;
                    }
                }
            }

            // --- Parte 3: Datos Médicos y Bancarios ---
            nss.getTxtEntrada().setText(usuario.getNSS());
            alergiasConocidas.getTxtEntrada().setText(usuario.getAlergiasConocidas());
            contactoEmergencia.getTxtEntrada().setText(usuario.getContactoEmergencia());
            banco.getTxtEntrada().setText(usuario.getBanco());
            numeroCuenta.getTxtEntrada().setText(usuario.getNumeroCuenta());
            sueldo.getTxtEntrada().setText(usuario.getSueldo());
            
            // ComboBox de Sangre
            tipoSangre.setSelectedItem(usuario.getTipoDeSangre());
        }
    }
    
    private void save() {
    	if(validarFormulario() == false) {
    		return;
    	}
    	
    	if(usuario == null) {
    		usuario = new User(
					nombre.obtenerTextoEntrada(),
					fechaNacimiento.obtenerTextoEntrada(),
					curp.obtenerTextoEntrada(),
					telefono.obtenerTextoEntrada(),
					correo.obtenerTextoEntrada(),
					(String) estadoCivil.getSelectedItem(),
					(String) generos.getSelectedItem(),
					puestoActual.obtenerTextoEntrada(),
					descripcionFunciones.obtenerTextoEntrada(),
					perfilPuesto.obtenerTextoEntrada(),
					condicionesLaborales.obtenerTextoEntrada(),
					ubicacionOrganizacional.obtenerTextoEntrada(),
					tipoContrato.obtenerTextoEntrada(),
					radioTurno.getSelection().getActionCommand(),
					nss.obtenerTextoEntrada(),
					alergiasConocidas.obtenerTextoEntrada(),
					contactoEmergencia.obtenerTextoEntrada(),
			        (String) tipoSangre.getSelectedItem().toString(), // Extracción del JComboBox
			        banco.obtenerTextoEntrada(),
			        numeroCuenta.obtenerTextoEntrada(),
			        sueldo.obtenerTextoEntrada()
					);
    	} else {
    		usuario.setNombre(nombre.obtenerTextoEntrada());
            usuario.setFechaNacimiento(fechaNacimiento.obtenerTextoEntrada());
            usuario.setCurp(curp.obtenerTextoEntrada());
            usuario.setTelefono(telefono.obtenerTextoEntrada());
            usuario.setCorreo(correo.obtenerTextoEntrada());
            usuario.setEstadoCivil((String) estadoCivil.getSelectedItem());
            usuario.setGenero((String) generos.getSelectedItem());
            
            usuario.setPuestoActual(puestoActual.obtenerTextoEntrada());
            usuario.setDescripcionFunciones(descripcionFunciones.obtenerTextoEntrada());
            usuario.setPerfilPuesto(perfilPuesto.obtenerTextoEntrada());
            usuario.setCondicionesLaborales(condicionesLaborales.obtenerTextoEntrada());
            usuario.setUbicacionOrganizacional(ubicacionOrganizacional.obtenerTextoEntrada());
            usuario.setTipoContrato(tipoContrato.obtenerTextoEntrada());
            usuario.setTurno(radioTurno.getSelection().getActionCommand());
            
            usuario.setNSS(nss.obtenerTextoEntrada());
            usuario.setAlergiasConocidas(alergiasConocidas.obtenerTextoEntrada());
            usuario.setContactoEmergencia(contactoEmergencia.obtenerTextoEntrada());
            usuario.setTipoDeSangre((String) tipoSangre.getSelectedItem());
            usuario.setBanco(banco.obtenerTextoEntrada());
            usuario.setNumeroCuenta(numeroCuenta.obtenerTextoEntrada());
            usuario.setSueldo(sueldo.obtenerTextoEntrada());
    	}
    	
    	saved = true;
        dispose();
    }
    		
    public boolean isSaved() {
    	return saved;
    }
    
    public User getUsuario() {
    	return usuario;
    }

    private boolean validarFormulario() {
    	boolean formularioListo = true; //Evita cambio con entradas vacias, incompletas o incorrectas
		
		for(PanelTipoPreguntaUtil pregunta: listaPreguntas) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				formularioListo = false;
			}
		}
		
		//Comprueba checkbox
		if(estadoCivil.getSelectedItem() == "Seleccionar" || generos.getSelectedItem() == "Seleccionar" ) {
			formularioListo = false;
		}
		
		//Comprueba raddio button
				if(radioTurno.getSelection() == null) {
					formularioListo = false;
		}
		
		//Comprueba checkbox
		if(tipoSangre.getSelectedIndex() == 0) {
			formularioListo = false;
		}
				
				
		//Comprueba contenidos invalidos en textfields
		for(PanelTipoPreguntaUtil pregunta: listaPreguntas) {
			try {
				ValidadorCadena.validarContenido(pregunta);
			} catch (Exception e) {
				formularioListo = false;
			}	
		}
		
		return formularioListo;
    }
    
    
}