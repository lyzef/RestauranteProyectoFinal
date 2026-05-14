package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

import utilidades.views.PanelTipoPreguntaUtil;

/**
 *Crea la primera parte visual del formulario
 *Inicializa las preguntas y atributos del formulario, controladores de preguntas se crean desde controlador de este formulario
 */
public class FormularioView extends JFrame{
	public static final String FORMPARTE1 = "FORMPARTE1"; //Dato
	public static final String FORMPARTE2 = "FORMPARTE2"; //Info puesto
	public static final String FORMPARTE3 = "FORMPARTE3"; //Datos extra
	
	CardLayout cardLayout;
	JPanel panelContenedorCentral;
	
	//Formulario de datos -PARTE 1-
	PanelTipoPreguntaUtil nombre;
	PanelTipoPreguntaUtil fechaNacimiento;
	PanelTipoPreguntaUtil curp;
	PanelTipoPreguntaUtil telefono;
	PanelTipoPreguntaUtil correo;
	List <PanelTipoPreguntaUtil> listaPreguntasParte1;
	JComboBox<String> estadoCivil;
	JComboBox<String> generos;
	JButton botonSiguiente;
	
	//Formulario de datos del puesto -PARTE 2-
	PanelTipoPreguntaUtil puestoActual;
	PanelTipoPreguntaUtil descripcionFunciones;
	PanelTipoPreguntaUtil perfilPuesto;
	PanelTipoPreguntaUtil condicionesLaborales;
	PanelTipoPreguntaUtil ubicacionOrganizacional;
	PanelTipoPreguntaUtil tipoContrato;
	List <PanelTipoPreguntaUtil> listaPreguntasParte2;
	ButtonGroup radioTurno;
	
	//Formulario de datos extras-PARTE 3-
	PanelTipoPreguntaUtil NSS;
	PanelTipoPreguntaUtil alergiasConocidas;
	PanelTipoPreguntaUtil contactoEmergencia;
	PanelTipoPreguntaUtil banco;
	PanelTipoPreguntaUtil numeroCuenta;
	PanelTipoPreguntaUtil sueldo;
	List <PanelTipoPreguntaUtil> listaPreguntasParte3;
	JComboBox<String> tipoSangre;
	
	public FormularioView() {
		setSize(400,400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(true);
		setTitle("Formulario");
		setLocationRelativeTo(null);
		
		//Icono
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icono = tk.getImage("src/image/icono.jpg");
		setIconImage(icono);
		
		InicializarComponentes();
		
		setVisible(true);
		
	}
	
	public void InicializarComponentes() {
		//Paneles
		JPanel panelContenedorSuperior = new JPanel();
		panelContenedorCentral = new JPanel();
		JPanel panelContenedorInferior = new JPanel();
		
		//Panel superior
		JLabel lblTitulo = new JLabel("Registro - Datos personales");
		lblTitulo.setFont(new Font("Times", Font.PLAIN,17));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		panelContenedorSuperior.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panelContenedorSuperior.add(lblTitulo);
		
		//Panel inferior
		botonSiguiente = new JButton("Siguiente");
		botonSiguiente.setBackground(new Color(144, 224, 239));
		panelContenedorInferior.add(botonSiguiente);
	        
		//Panel central
		
		cardLayout = new CardLayout();
		panelContenedorCentral.setLayout(cardLayout);
		crearFormulario();
		
		//Anadiendo paneles
		add(panelContenedorCentral,BorderLayout.CENTER); //Es el contenedor padre del panel cuestionario
		add(panelContenedorSuperior,BorderLayout.NORTH);
		add(panelContenedorInferior,BorderLayout.SOUTH);
		
		
	}
	
	public void crearFormulario() {
		panelContenedorCentral.add(formularioParte1(), FORMPARTE1);
		panelContenedorCentral.add(formularioParte2(), FORMPARTE2);
		panelContenedorCentral.add(formularioParte3(), FORMPARTE3);
	}
	
	private JScrollPane formularioParte1() {
		//Panel central cuestionario
		JPanel panelCuestionarioParte1 = new JPanel();
		panelCuestionarioParte1.setLayout(new  BoxLayout(panelCuestionarioParte1, BoxLayout.Y_AXIS));
		Border emptyBorder = BorderFactory.createEmptyBorder(10,20,10,20);
		panelCuestionarioParte1.setBorder(emptyBorder);
		
		//Inicializacion paneles
		nombre = new PanelTipoPreguntaUtil("Nombre", "ALFABETICO");
		fechaNacimiento = new PanelTipoPreguntaUtil("Fecha de nacimiento", "FECHA");
		curp = new PanelTipoPreguntaUtil("Curp", "ALFANUMERICO");
		telefono = new PanelTipoPreguntaUtil("Telefono", "NUMERICO");
		correo = new PanelTipoPreguntaUtil("Correo", "CORREO");
		
		//Inicializacion de array
		listaPreguntasParte1 = new ArrayList<>();
		
		listaPreguntasParte1.add(nombre);
		listaPreguntasParte1.add(fechaNacimiento);
		listaPreguntasParte1.add(curp);
		listaPreguntasParte1.add(telefono);
		listaPreguntasParte1.add(correo);
		
		for(PanelTipoPreguntaUtil pregunta : listaPreguntasParte1) {
			panelCuestionarioParte1.add(pregunta);
		}
		
		
		
		//ComboBox
		JLabel lblGenero = new JLabel("Genero ");
		panelCuestionarioParte1.add(lblGenero);
		String[] opcionesGenero = {"Seleccionar","Hombre", "Mujer","Therian","Otro"};
		generos = new JComboBox<String>(opcionesGenero);
		generos.setSelectedIndex(0); //Item preseleccionado
		panelCuestionarioParte1.add(generos);
		
		JLabel lblEstadoCivil = new JLabel("EstadoCivil ");
		panelCuestionarioParte1.add(lblEstadoCivil);
		String[] opcionesEstadoCivil = {"Seleccionar","Soltero","Casado","Union libre", "Viudo"};
		estadoCivil = new JComboBox<String>(opcionesEstadoCivil);
		estadoCivil.setSelectedIndex(0);
		panelCuestionarioParte1.add(estadoCivil);
		
		
		JScrollPane scroll = new JScrollPane(panelCuestionarioParte1);
    	return scroll;
	}
	
	private JScrollPane formularioParte2() {
	    	JPanel panelCuestionarioParte2 = new JPanel();
	    	
	    	panelCuestionarioParte2.setLayout(new BoxLayout(panelCuestionarioParte2, BoxLayout.Y_AXIS));
	        Border emptyBorder = BorderFactory.createEmptyBorder(10, 20, 10, 20);
	        panelCuestionarioParte2.setBorder(emptyBorder);

	        puestoActual = new PanelTipoPreguntaUtil("Puesto actual:", "ALFANUMERICO");
	    	descripcionFunciones = new PanelTipoPreguntaUtil("Funciones en la empresa", "ALFANUMERICO");
	    	perfilPuesto = new PanelTipoPreguntaUtil("Perfil de puesto: ", "ALFANUMERICO");
	    	condicionesLaborales = new PanelTipoPreguntaUtil("Condiciones laborales: ", "ALFANUMERICO");
	    	ubicacionOrganizacional = new PanelTipoPreguntaUtil("Ubicacion organizacional", "ALFANUMERICO");
	    	tipoContrato = new PanelTipoPreguntaUtil("Tipo de contrato: ", "ALFANUMERICO");
	        
	    	listaPreguntasParte2 = new ArrayList<>();
	    	listaPreguntasParte2.add(puestoActual);
	    	listaPreguntasParte2.add(descripcionFunciones);
	    	listaPreguntasParte2.add(perfilPuesto);
	    	listaPreguntasParte2.add(condicionesLaborales);
	    	listaPreguntasParte2.add(ubicacionOrganizacional);
	    	listaPreguntasParte2.add(tipoContrato);
	    	
	    	for(PanelTipoPreguntaUtil pregunta : listaPreguntasParte2) {
	    		panelCuestionarioParte2.add(pregunta);
			}
	    	
	        JLabel lblTurno = new JLabel("Turno");
	        panelCuestionarioParte2.add(lblTurno);
	        radioTurno = new ButtonGroup();
	        JRadioButton rbMatutino = new JRadioButton("Matutino"); panelCuestionarioParte2.add(rbMatutino);
	        rbMatutino.setActionCommand("Matutino");
	        
	        JRadioButton rbVespertino = new JRadioButton("Vespertino"); panelCuestionarioParte2.add(rbVespertino);
	        rbVespertino.setActionCommand("Vespertino");
	        
	        JRadioButton rbMixto = new JRadioButton("Mixto"); panelCuestionarioParte2.add(rbMixto);
	        rbMixto.setActionCommand("Mixto");
	        
	        radioTurno.add(rbMatutino);radioTurno.add(rbVespertino);radioTurno.add(rbMixto);
	     
	        JScrollPane scroll = new JScrollPane(panelCuestionarioParte2);
	    	return scroll;
	}
	
    private JScrollPane formularioParte3() {
   	 		JPanel panelCuestionarioParte3 = new JPanel();
   	 		panelCuestionarioParte3.setLayout(new BoxLayout(panelCuestionarioParte3, BoxLayout.Y_AXIS));
	        Border emptyBorder = BorderFactory.createEmptyBorder(10, 20, 10, 20);
	        panelCuestionarioParte3.setBorder(emptyBorder);
	        
	        listaPreguntasParte3 = new ArrayList<PanelTipoPreguntaUtil>();
	        NSS = new PanelTipoPreguntaUtil("Numero seguro social: ", "ALFANUMERICO");
	        alergiasConocidas = new PanelTipoPreguntaUtil("Alergias: ", "ALFANUMERICO");		        
	        contactoEmergencia = new PanelTipoPreguntaUtil("Contacto emergencia", "ALFANUMERICO");
	        
	        panelCuestionarioParte3.add(NSS);
	        panelCuestionarioParte3.add(alergiasConocidas);
	        panelCuestionarioParte3.add(contactoEmergencia);
	        
	        listaPreguntasParte3.add(NSS);
	        listaPreguntasParte3.add(alergiasConocidas);
	        listaPreguntasParte3.add(contactoEmergencia);
	        
	        JLabel lblTurno = new JLabel("Tipo de sangre");
	        panelCuestionarioParte3.add(lblTurno);
			String[] opcionesSangre = {"Seleccionar","O-","O+","B-","B+","A-","A+","AB+","AB-"};
			tipoSangre = new JComboBox<String>(opcionesSangre);
			tipoSangre.setSelectedIndex(0);
			panelCuestionarioParte3.add(tipoSangre);
			tipoSangre.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
			
	
	        
	        JLabel lblDatosBancarios = new JLabel("Datos bancarios");
	        lblDatosBancarios.setFont(new Font("Arial",Font.BOLD,15));
	        panelCuestionarioParte3.add(lblDatosBancarios);
	        
	        banco = new PanelTipoPreguntaUtil("Banco: ", "ALFANUMERICO");
	        numeroCuenta = new PanelTipoPreguntaUtil("Clabe o numero de cuenta: ", "NUMERICO");		        
	        sueldo = new PanelTipoPreguntaUtil("Sueldo", "NUMERICO");
	        
	        panelCuestionarioParte3.add(banco);
	        panelCuestionarioParte3.add(numeroCuenta);
	        panelCuestionarioParte3.add(sueldo);
	        
	        listaPreguntasParte3.add(banco);
	        listaPreguntasParte3.add(numeroCuenta);
	        listaPreguntasParte3.add(sueldo);
	        
	        JScrollPane scroll = new JScrollPane(panelCuestionarioParte3);
	    	return scroll;
    }
    
    public void showView(String view) {
		cardLayout.show(panelContenedorCentral, view);
	}
    
    public int confirmacionSalidaPanel() {
	    	return JOptionPane.showConfirmDialog(null, 
		            "¿Seguro que quieres salir?", 
		            "Confirmar salida", JOptionPane.YES_NO_OPTION);
	}
	
    public void mensajeConfirmacionFormularioCompleto() {
    	JOptionPane.showMessageDialog(null, 
    		    "Formulario terminado", 
    		    "Información", 
    		    JOptionPane.INFORMATION_MESSAGE);
    }

    
    
    
    
	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public void setCardLayout(CardLayout cardLayoutForm) {
		this.cardLayout = cardLayoutForm;
	}

	public JPanel getPanelContenedorCentral() {
		return panelContenedorCentral;
	}

	public void setPanelContenedorCentral(JPanel panelContenedorCentral) {
		this.panelContenedorCentral = panelContenedorCentral;
	}

	public PanelTipoPreguntaUtil getNombre() {
		return nombre;
	}

	public void setNombre(PanelTipoPreguntaUtil nombre) {
		this.nombre = nombre;
	}

	public PanelTipoPreguntaUtil getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(PanelTipoPreguntaUtil fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public PanelTipoPreguntaUtil getCurp() {
		return curp;
	}

	public void setCurp(PanelTipoPreguntaUtil curp) {
		this.curp = curp;
	}

	public PanelTipoPreguntaUtil getTelefono() {
		return telefono;
	}

	public void setTelefono(PanelTipoPreguntaUtil telefono) {
		this.telefono = telefono;
	}

	public PanelTipoPreguntaUtil getCorreo() {
		return correo;
	}

	public void setCorreo(PanelTipoPreguntaUtil correo) {
		this.correo = correo;
	}

	public List<PanelTipoPreguntaUtil> getListaPreguntasParte1() {
		return listaPreguntasParte1;
	}

	public void setListaPreguntasParte1(List<PanelTipoPreguntaUtil> listaPreguntasParte1) {
		this.listaPreguntasParte1 = listaPreguntasParte1;
	}

	public JComboBox<String> getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(JComboBox<String> estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public JComboBox<String> getGeneros() {
		return generos;
	}

	public void setGeneros(JComboBox<String> generos) {
		this.generos = generos;
	}

	public JButton getBotonSiguiente() {
		return botonSiguiente;
	}

	public void setBotonSiguiente(JButton botonSiguiente) {
		this.botonSiguiente = botonSiguiente;
	}

	public PanelTipoPreguntaUtil getPuestoActual() {
		return puestoActual;
	}

	public void setPuestoActual(PanelTipoPreguntaUtil puestoActual) {
		this.puestoActual = puestoActual;
	}

	public PanelTipoPreguntaUtil getDescripcionFunciones() {
		return descripcionFunciones;
	}

	public void setDescripcionFunciones(PanelTipoPreguntaUtil descripcionFunciones) {
		this.descripcionFunciones = descripcionFunciones;
	}

	public PanelTipoPreguntaUtil getPerfilPuesto() {
		return perfilPuesto;
	}

	public void setPerfilPuesto(PanelTipoPreguntaUtil perfilPuesto) {
		this.perfilPuesto = perfilPuesto;
	}

	public PanelTipoPreguntaUtil getCondicionesLaborales() {
		return condicionesLaborales;
	}

	public void setCondicionesLaborales(PanelTipoPreguntaUtil condicionesLaborales) {
		this.condicionesLaborales = condicionesLaborales;
	}

	public PanelTipoPreguntaUtil getUbicacionOrganizacional() {
		return ubicacionOrganizacional;
	}

	public void setUbicacionOrganizacional(PanelTipoPreguntaUtil ubicacionOrganizacional) {
		this.ubicacionOrganizacional = ubicacionOrganizacional;
	}

	public PanelTipoPreguntaUtil getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(PanelTipoPreguntaUtil tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public List<PanelTipoPreguntaUtil> getListaPreguntasParte2() {
		return listaPreguntasParte2;
	}

	public void setListaPreguntasParte2(List<PanelTipoPreguntaUtil> listaPreguntasParte2) {
		this.listaPreguntasParte2 = listaPreguntasParte2;
	}

	public ButtonGroup getRadioTurno() {
		return radioTurno;
	}

	public void setRadioTurno(ButtonGroup radioTurno) {
		this.radioTurno = radioTurno;
	}

	public PanelTipoPreguntaUtil getNSS() {
		return NSS;
	}

	public void setNSS(PanelTipoPreguntaUtil nSS) {
		NSS = nSS;
	}

	public PanelTipoPreguntaUtil getAlergiasConocidas() {
		return alergiasConocidas;
	}

	public void setAlergiasConocidas(PanelTipoPreguntaUtil alergiasConocidas) {
		this.alergiasConocidas = alergiasConocidas;
	}

	public PanelTipoPreguntaUtil getContactoEmergencia() {
		return contactoEmergencia;
	}

	public void setContactoEmergencia(PanelTipoPreguntaUtil contactoEmergencia) {
		this.contactoEmergencia = contactoEmergencia;
	}

	public PanelTipoPreguntaUtil getBanco() {
		return banco;
	}

	public void setBanco(PanelTipoPreguntaUtil banco) {
		this.banco = banco;
	}

	public PanelTipoPreguntaUtil getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(PanelTipoPreguntaUtil numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public PanelTipoPreguntaUtil getSueldo() {
		return sueldo;
	}

	public void setSueldo(PanelTipoPreguntaUtil sueldo) {
		this.sueldo = sueldo;
	}

	public List<PanelTipoPreguntaUtil> getListaPreguntasParte3() {
		return listaPreguntasParte3;
	}

	public void setListaPreguntasParte3(List<PanelTipoPreguntaUtil> listaPreguntasParte3) {
		this.listaPreguntasParte3 = listaPreguntasParte3;
	}

	public JComboBox<String> getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(JComboBox<String> tipoSangre) {
		this.tipoSangre = tipoSangre;
	}

	public static String getFormparte1() {
		return FORMPARTE1;
	}

	public static String getFormparte2() {
		return FORMPARTE2;
	}

	public static String getFormparte3() {
		return FORMPARTE3;
	}
    
    
	
}
