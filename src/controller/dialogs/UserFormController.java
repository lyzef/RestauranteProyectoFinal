package controller.dialogs;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import controller.PreguntaController;
import models.User;
import repository.UserRepository;
import utilidades.PasswordUtils;
import utilidades.ValidadorEntradasTexto;
import utilidades.views.PanelTipoPreguntaUtil;
import views.Login;
import views.Dialog.UserFormDialog;

public class UserFormController {
	private UserFormDialog formulario;
	private String parteFormularioActual;	
	
	private User usuario;
	/**
	 * Controlador de las clase formulario, guarda, edita y comprueba el formulario
	 * @param formulario Primera parte del formulario
	 */
	public UserFormController(UserFormDialog formularioRegistro, User usuario) {
		this.formulario = formularioRegistro;
		this.usuario = usuario;
		
		//Conectando paneles pregunta a su controlador
		conectarPreguntasAsuControlador(formularioRegistro.getListaPreguntasParte1());
		conectarPreguntasAsuControlador(formularioRegistro.getListaPreguntasParte2());
		conectarPreguntasAsuControlador(formularioRegistro.getListaPreguntasParte3());
		
		//Iniciando vista formulario parte 1
		parteFormularioActual = UserFormDialog.FORMPARTE1;
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
	 * edita o inicializa la clase usuario, activa el modo solo vista
	 */
	public void controlFlujoFormulario() {
		switch (parteFormularioActual) {
		case UserFormDialog.FORMPARTE1:{
			if(formulario.getSoloVista()) {
				parteFormularioActual = formulario.FORMPARTE2;
				formulario.showView(parteFormularioActual);
				break;
			}
			
			if(validarFormularioParte1()) {
				parteFormularioActual = formulario.FORMPARTE2;
				formulario.showView(parteFormularioActual);
			}
			break;
		}
		case UserFormDialog.FORMPARTE2:{
			if(formulario.getSoloVista()) {
				parteFormularioActual = formulario.FORMPARTE3;
				formulario.showView(parteFormularioActual);
				break;
			}
			
			if(validarFormularioParte2()) {
				parteFormularioActual = formulario.FORMPARTE3;
				formulario.showView(parteFormularioActual);
			}
			break;
		}
		case UserFormDialog.FORMPARTE3:{
			if(formulario.getSoloVista()) {
				formulario.dispose();
				break;
			}
			
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
        	formulario.getNombre().setTextoEntrada(usuario.getNombre());
        	formulario.getFechaNacimiento().setTextoEntrada(usuario.getFechaNacimiento());
        	formulario.getCurp().setTextoEntrada(usuario.getCurp());
        	formulario.getTelefono().setTextoEntrada(usuario.getTelefono());
        	formulario.getCorreo().setTextoEntrada(usuario.getCorreo());
            
        	formulario.getGeneros().setSelectedItem(usuario.getGenero());
        	formulario.getEstadoCivil().setSelectedItem(usuario.getEstadoCivil());

            // --- Parte 2: Datos Laborales (CORREGIDO) ---
        	formulario.getRol().setSelectedItem(usuario.getRol());
        	formulario.getDescripcionFunciones().setTextoEntrada(usuario.getDescripcionFunciones());
        	formulario.getTipoContrato().setTextoEntrada(usuario.getTipoContrato());

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
            formulario.getNSS().setTextoEntrada(usuario.getNSS());
            formulario.getAlergiasConocidas().setTextoEntrada(usuario.getAlergiasConocidas());
            formulario.getContactoEmergencia().setTextoEntrada(usuario.getContactoEmergencia());
            formulario.getBanco().setTextoEntrada(usuario.getBanco());
            formulario.getNumeroCuenta().setTextoEntrada(usuario.getNumeroCuenta());
            formulario.getSueldo().setTextoEntrada(usuario.getSueldo());
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
	            formulario.getNombre().getTextoEntrada(),
	            formulario.getFechaNacimiento().getTextoEntrada(),
	            formulario.getCurp().getTextoEntrada(),
	            formulario.getTelefono().getTextoEntrada(),
	            formulario.getCorreo().getTextoEntrada(),
	            formulario.getNSS().getTextoEntrada(),
	            (String) formulario.getEstadoCivil().getSelectedItem(),
	            (String) formulario.getGeneros().getSelectedItem(),
	            (String) formulario.getRol().getSelectedItem(),
	            formulario.getDescripcionFunciones().getTextoEntrada(),
	            formulario.getTipoContrato().getTextoEntrada(),
	            turnoSeleccionado,
	            formulario.getAlergiasConocidas().getTextoEntrada(),
	            formulario.getContactoEmergencia().getTextoEntrada(),
	            (String) formulario.getTipoSangre().getSelectedItem(),
	            formulario.getBanco().getTextoEntrada(),
	            formulario.getNumeroCuenta().getTextoEntrada(),
	            formulario.getSueldo().getTextoEntrada(),
	            PasswordUtils.hashPassword(formulario.getContrasena().getTextoEntrada()),
	            false, // Usuario inactivo por defecto (Se activa al logearse)
	            " " // Ultima sesion se actualiza solo en db
	        );
	    } else { //Solo se modifica lo modificable
	        usuario.setNombre(formulario.getNombre().getTextoEntrada());
	        usuario.setFechaNacimiento(formulario.getFechaNacimiento().getTextoEntrada());
	        usuario.setCurp(formulario.getCurp().getTextoEntrada());
	        usuario.setTelefono(formulario.getTelefono().getTextoEntrada());
	        usuario.setCorreo(formulario.getCorreo().getTextoEntrada());
	        usuario.setNSS(formulario.getNSS().getTextoEntrada());

	        usuario.setEstadoCivil((String) formulario.getEstadoCivil().getSelectedItem());
	        usuario.setGenero((String) formulario.getGeneros().getSelectedItem());
	        usuario.setRol((String) formulario.getRol().getSelectedItem());
	        usuario.setDescripcionFunciones(formulario.getDescripcionFunciones().getTextoEntrada());
	        usuario.setTipoContrato(formulario.getTipoContrato().getTextoEntrada());
	        usuario.setTurno(turnoSeleccionado);

	        usuario.setAlergiasConocidas(formulario.getAlergiasConocidas().getTextoEntrada());
	        usuario.setContactoEmergencia(formulario.getContactoEmergencia().getTextoEntrada());
	        usuario.setTipoDeSangre((String) formulario.getTipoSangre().getSelectedItem());
	        usuario.setBanco(formulario.getBanco().getTextoEntrada());
	        usuario.setNumeroCuenta(formulario.getNumeroCuenta().getTextoEntrada());
	        usuario.setSueldo(formulario.getSueldo().getTextoEntrada());
	        
	        //Comprobando si usuario quiere cambiar su contrasena
	        if(formulario.getModificarContrasenaCheckBox().isSelected()) {
	    	    usuario.setContrasena(PasswordUtils.hashPassword(formulario.getContrasena().getTextoEntrada()));
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
		
		//Comprueba checkbox
		if(formulario.getRol().getSelectedItem() == "Seleccionar" ) {
			formularioListo = false;
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
