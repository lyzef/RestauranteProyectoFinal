package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import models.User;
import repository.UserRepository;
import utilidades.PasswordUtils;
import utilidades.ValidadorEntradasTexto;
import utilidades.views.PanelTipoPreguntaUtil;
import views.FormularioDialog;
import views.Login;

public class FormularioController {
	private FormularioDialog formulario;
	private String parteFormularioActual;	
	
	private User usuario;
	/**
	 * Controlador de las clase formulario, guarda, edita y comprueba el formulario
	 * @param formulario Primera parte del formulario
	 */
	public FormularioController(FormularioDialog formularioRegistro, User usuario) {
		this.formulario = formularioRegistro;
		this.usuario = usuario;
		
		//Conectando paneles pregunta a su controlador
		conectarPreguntasAsuControlador(formularioRegistro.getListaPreguntasParte1());
		conectarPreguntasAsuControlador(formularioRegistro.getListaPreguntasParte2());
		conectarPreguntasAsuControlador(formularioRegistro.getListaPreguntasParte3());
		
		//Iniciando vista formulario parte 1
		parteFormularioActual = FormularioDialog.FORMPARTE1;
		formulario.showView(parteFormularioActual);
		
		
		//Boton siguiente/guardar listener
		formulario.getBotonSiguiente().addActionListener(e -> {
			controlFlujoFormulario();
			
		});
		
		formulario.getBotonCancelar().addActionListener(e -> {
			cerrarFormularioConDialog();
		});
		
		formulario.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	cerrarFormularioConDialog();
		    }
		});		
		
		formulario.getModificarContrasenaCheckBox().addActionListener(e -> {
			if(formulario.getModificarContrasenaCheckBox().isSelected()) {
				formulario.getContrasena().setVisible(true);
			} else {

				formulario.getContrasena().setVisible(false);
			}
		});
		
		cargarDatos();
		
		//Poner contrasena para usuarios nuevos
		if(usuario == null) {
			formulario.getModificarContrasenaCheckBox().setVisible(false);
			formulario.getContrasena().setVisible(true);
		}
	}
	
	/**
	 * Controla el flujo del formulario, cambia de ventana si la actual ya esta completa y validada
	 * edita o inicializa la clase usuario 
	 */
	public void controlFlujoFormulario() {
		switch (parteFormularioActual) {
		case FormularioDialog.FORMPARTE1:{
			if(validarFormularioParte1()) {
				parteFormularioActual = formulario.FORMPARTE2;
				formulario.showView(parteFormularioActual);
			}
			break;
		}
		case FormularioDialog.FORMPARTE2:{
			if(validarFormularioParte2()) {
				parteFormularioActual = formulario.FORMPARTE3;
				formulario.showView(parteFormularioActual);
			}
			break;
		}
		case FormularioDialog.FORMPARTE3:{
			if(validarFormularioParte3()) {
				formulario.mensajeConfirmacionFormularioCompleto();
				guardarUsuario();
				formulario.dispose();
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + parteFormularioActual);
		}
	}
	

	/**
	 * Cierra el formulario con dialog
	 */
	public void cerrarFormularioConDialog() {
		int verif = formulario.confirmacionSalidaPanel();
        
        if (verif == JOptionPane.YES_OPTION) {
        	formulario.dispose();
        }
	}
	
	/**
	 * Conecta las clases preguntas con su controlador que se encarga de moderar los caracteres ingresados segun el contexto de la pregunta
	 */
	public void conectarPreguntasAsuControlador(List <PanelTipoPreguntaUtil> preguntas ) {
		for(PanelTipoPreguntaUtil p : preguntas) {
			PreguntaController.registrarPanel(p);
		}
	}
	
	/**
	 * Carga datos si no existe usuario
	 */
	private void cargarDatos() {
        if (usuario != null) {
            // --- Parte 1: Datos Personales ---
        	formulario.getNombre().getTxtEntrada().setText(usuario.getNombre());
        	formulario.getFechaNacimiento().getTxtEntrada().setText(usuario.getFechaNacimiento());
        	formulario.getCurp().getTxtEntrada().setText(usuario.getCurp());
        	formulario.getTelefono().getTxtEntrada().setText(usuario.getTelefono());
        	formulario.getCorreo().getTxtEntrada().setText(usuario.getCorreo());
            
        	formulario.getGeneros().setSelectedItem(usuario.getGenero());
        	formulario.getEstadoCivil().setSelectedItem(usuario.getEstadoCivil());

            // --- Parte 2: Datos Laborales (CORREGIDO) ---
        	formulario.getRol().getTxtEntrada().setText(usuario.getRol());
        	formulario.getDescripcionFunciones().getTxtEntrada().setText(usuario.getDescripcionFunciones());
        	formulario.getTipoContrato().getTxtEntrada().setText(usuario.getTipoContrato());

            if (usuario.getTurno() != null) {
                String turno = usuario.getTurno();
                Enumeration<AbstractButton> buttons = formulario.getRadioTurno().getElements();
                while (buttons.hasMoreElements()) {
                    JRadioButton button = (JRadioButton) buttons.nextElement();
                    if (button.getText().equals(turno)) {
                        button.setSelected(true);
                        break;
                    }
                }
            }

            // --- Parte 3: Datos Médicos y Bancarios ---
            formulario.getNSS().getTxtEntrada().setText(usuario.getNSS());
            formulario.getAlergiasConocidas().getTxtEntrada().setText(usuario.getAlergiasConocidas());
            formulario.getContactoEmergencia().getTxtEntrada().setText(usuario.getContactoEmergencia());
            formulario.getBanco().getTxtEntrada().setText(usuario.getBanco());
            formulario.getNumeroCuenta().getTxtEntrada().setText(usuario.getNumeroCuenta());
            formulario.getSueldo().getTxtEntrada().setText(usuario.getSueldo());
            formulario.getTipoSangre().setSelectedItem(usuario.getTipoDeSangre());
        }
    }
	
	/**
	 * Guarda todos las entradas del formulario a clase usuario
	 */
	private void guardarUsuario() {
	    // Obtener el comando del turno de forma segura
	    String turnoSeleccionado = (formulario.getRadioTurno().getSelection() != null) 
	        ? formulario.getRadioTurno().getSelection().getActionCommand() 
	        : "";

	    if (usuario == null) { //Usuario nuevo
	        usuario = new User(
	            formulario.getNombre().obtenerTextoEntrada(),
	            formulario.getFechaNacimiento().obtenerTextoEntrada(),
	            formulario.getCurp().obtenerTextoEntrada(),
	            formulario.getTelefono().obtenerTextoEntrada(),
	            formulario.getCorreo().obtenerTextoEntrada(),
	            (String) formulario.getEstadoCivil().getSelectedItem(),
	            (String) formulario.getGeneros().getSelectedItem(),
	            formulario.getRol().obtenerTextoEntrada(),
	            formulario.getDescripcionFunciones().obtenerTextoEntrada(),
	            formulario.getTipoContrato().obtenerTextoEntrada(),
	            turnoSeleccionado,
	            formulario.getNSS().obtenerTextoEntrada(),
	            formulario.getAlergiasConocidas().obtenerTextoEntrada(),
	            formulario.getContactoEmergencia().obtenerTextoEntrada(),
	            (String) formulario.getTipoSangre().getSelectedItem(),
	            formulario.getBanco().obtenerTextoEntrada(),
	            formulario.getNumeroCuenta().obtenerTextoEntrada(),
	            formulario.getSueldo().obtenerTextoEntrada(),
	            PasswordUtils.hashPassword(formulario.getContrasena().obtenerTextoEntrada())
	        );
	    } else {
	        usuario.setNombre(formulario.getNombre().obtenerTextoEntrada());
	        usuario.setFechaNacimiento(formulario.getFechaNacimiento().obtenerTextoEntrada());
	        usuario.setCurp(formulario.getCurp().obtenerTextoEntrada());
	        usuario.setTelefono(formulario.getTelefono().obtenerTextoEntrada());
	        usuario.setCorreo(formulario.getCorreo().obtenerTextoEntrada());
	        usuario.setEstadoCivil((String) formulario.getEstadoCivil().getSelectedItem());
	        usuario.setGenero((String) formulario.getGeneros().getSelectedItem());

	        usuario.setRol(formulario.getRol().obtenerTextoEntrada());
	        usuario.setDescripcionFunciones(formulario.getDescripcionFunciones().obtenerTextoEntrada());
	        usuario.setTipoContrato(formulario.getTipoContrato().obtenerTextoEntrada());
	        usuario.setTurno(turnoSeleccionado);

	        usuario.setNSS(formulario.getNSS().obtenerTextoEntrada());
	        usuario.setAlergiasConocidas(formulario.getAlergiasConocidas().obtenerTextoEntrada());
	        usuario.setContactoEmergencia(formulario.getContactoEmergencia().obtenerTextoEntrada());
	        usuario.setTipoDeSangre((String) formulario.getTipoSangre().getSelectedItem());
	        usuario.setBanco(formulario.getBanco().obtenerTextoEntrada());
	        usuario.setNumeroCuenta(formulario.getNumeroCuenta().obtenerTextoEntrada());
	        usuario.setSueldo(formulario.getSueldo().obtenerTextoEntrada());
	        
	        //Comprobando si usuario quiere cambiar su contrasena
	        if(formulario.getModificarContrasenaCheckBox().isSelected()) {
	    	    usuario.setContrasena(PasswordUtils.hashPassword(formulario.getContrasena().obtenerTextoEntrada()));
	        } 
	        
	    }

	    formulario.setSaved(true);
	}

	
	/**
	 * Valida la primera parte del formulario
	 */
	public boolean validarFormularioParte1() {
		boolean formularioListo = true; //Evita cambio con entradas vacias, incompletas o incorrectas
		
		for(PanelTipoPreguntaUtil pregunta: formulario.getListaPreguntasParte1()) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				formularioListo = false;
			}
		}
		
		//Comprueba checkbox
		if(formulario.getEstadoCivil().getSelectedItem() == "Seleccionar" || formulario.getGeneros().getSelectedItem() == "Seleccionar" ) {
			formularioListo = false;
		}
		
		//Comprueba contenidos invalidos en textfields
		for(PanelTipoPreguntaUtil pregunta: formulario.getListaPreguntasParte1()) {
			try {
				ValidadorEntradasTexto.validarContenido(pregunta);
			} catch (Exception e) {
				formularioListo = false;
			}	
		}
		
		return formularioListo;
		
	}
	
	public boolean validarFormularioParte2() {
		boolean formularioListo = true; //Evita cambio con entradas vacias, incompletas o incorrectas
		
		//Preguntas vacias
		for(PanelTipoPreguntaUtil pregunta: formulario.getListaPreguntasParte2()) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				formularioListo = false;
			}
		}
		
		//Comprueba raddio button
		if(formulario.getRadioTurno().getSelection() == null) {
			formularioListo = false;
		}
		
		//Comprueba contenidos invalidos en textfields
		for(PanelTipoPreguntaUtil pregunta: formulario.getListaPreguntasParte2()) {
			try {
				ValidadorEntradasTexto.validarContenido(pregunta);
			} catch (Exception e) {
				formularioListo = false;
			}	
		}
		
		return formularioListo;
		
	}
	
	public boolean validarFormularioParte3() {
		boolean formularioListo = true; //Evita cambio con entradas vacias, incompletas o incorrectas
		
		//Evitar validacion de contrasena si el usuario no desea cambiarla
		if(usuario != null && !formulario.getModificarContrasenaCheckBox().isSelected() ){
			PanelTipoPreguntaUtil c = formulario.getListaPreguntasParte3().getLast();
			formulario.getListaPreguntasParte3().removeLast();
		}
		
		//Preguntas vacias
		for(PanelTipoPreguntaUtil pregunta: formulario.getListaPreguntasParte3()) {
			if(pregunta.estaVacio()) {
				pregunta.senalarEntradaVacia();
				formularioListo = false;
			}
		}
		
		//Comprueba checkbox
		if(formulario.getTipoSangre().getSelectedIndex() == 0) {
			formularioListo = false;
		}
		
		//Comprueba contenidos invalidos en textfields
		for(PanelTipoPreguntaUtil pregunta: formulario.getListaPreguntasParte3()) {
			try {
				ValidadorEntradasTexto.validarContenido(pregunta);
			} catch (Exception e) {
				formularioListo = false;
			}	
		}
		
		return formularioListo;
		
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
	
}
