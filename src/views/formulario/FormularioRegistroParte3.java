package views.formulario;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;

import utilidades.PanelTipoPreguntaUtil;


public class FormularioRegistroParte3 extends JFrame{
	PanelTipoPreguntaUtil NSS;
	PanelTipoPreguntaUtil alergiasConocidas;
	PanelTipoPreguntaUtil contactoEmergencia;
	PanelTipoPreguntaUtil banco;
	PanelTipoPreguntaUtil numeroCuenta;
	PanelTipoPreguntaUtil sueldo;
	
	List <PanelTipoPreguntaUtil> listaPreguntas;
	JComboBox<String> tipoSangre;
	JButton botonFinalizar;
	
	
	
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

	public List<PanelTipoPreguntaUtil> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(List<PanelTipoPreguntaUtil> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}

	public JComboBox<String> getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(JComboBox<String> tipoSangre) {
		this.tipoSangre = tipoSangre;
	}

	public JButton getBotonFinalizar() {
		return botonFinalizar;
	}

	public void setBotonFinalizar(JButton botonFinalizar) {
		this.botonFinalizar = botonFinalizar;
	}

	public FormularioRegistroParte3() {
	        Toolkit tk = Toolkit.getDefaultToolkit();
	        Image icono = tk.getImage("src/image/icono.jpg");
	        setIconImage(icono);
	        
		 
	        setSize(400, 400);
	        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	        setResizable(true);
	        setTitle("Formulario");
	        setLocationRelativeTo(null);

	       
	        InicializarComponentes();

	        setVisible(true);
	    }

	    public void InicializarComponentes() {
	        // Paneles
	        JPanel panelContenedorSuperior = new JPanel();
	        JPanel panelContenedorInferior = new JPanel();
	       

	        // Panel superior
	        JLabel lblTitulo = new JLabel("Registro - Datos extras");
	        lblTitulo.setFont(new Font("Times", Font.PLAIN, 17));
	        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
	        panelContenedorSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        panelContenedorSuperior.add(lblTitulo);

	        // Panel inferior
	        botonFinalizar = new JButton("Siguiente");
	        botonFinalizar.setBackground(new Color(144, 224, 239));
	        panelContenedorInferior.add(botonFinalizar);
	        // Panel central - Sub paneles
	        
	        // Scroll
	        JScrollPane scroll = new JScrollPane(panelpreguntas());
	        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	        // Añadiendo paneles
	        add(scroll, BorderLayout.CENTER);
	        add(panelContenedorSuperior, BorderLayout.NORTH);
	        add(panelContenedorInferior, BorderLayout.SOUTH);
	    }
	    
	    private JPanel panelpreguntas() {
	    	 JPanel panelContenedorCentral = new JPanel();
	    	 panelContenedorCentral.setLayout(new BoxLayout(panelContenedorCentral, BoxLayout.Y_AXIS));
		        Border emptyBorder = BorderFactory.createEmptyBorder(10, 20, 10, 20);
		        panelContenedorCentral.setBorder(emptyBorder);
		        
		        listaPreguntas = new ArrayList<PanelTipoPreguntaUtil>();
		        NSS = new PanelTipoPreguntaUtil("Numero seguro social: ", "ALFANUMERICO");
		        alergiasConocidas = new PanelTipoPreguntaUtil("Alergias: ", "ALFANUMERICO");		        
		        contactoEmergencia = new PanelTipoPreguntaUtil("Contacto emergencia", "ALFANUMERICO");
		        
		        panelContenedorCentral.add(NSS);
		        panelContenedorCentral.add(alergiasConocidas);
		        panelContenedorCentral.add(contactoEmergencia);
		        
		        listaPreguntas.add(NSS);
		        listaPreguntas.add(alergiasConocidas);
		        listaPreguntas.add(contactoEmergencia);
		        
		        JLabel lblTurno = new JLabel("Tipo de sangre");
				panelContenedorCentral.add(lblTurno);
				String[] opcionesSangre = {"Seleccionar","O-","O+","B-","B+","A-","A+","AB+","AB-"};
				tipoSangre = new JComboBox<String>(opcionesSangre);
				tipoSangre.setSelectedIndex(0);
				panelContenedorCentral.add(tipoSangre);
				tipoSangre.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
				
		
		        
		        JLabel lblDatosBancarios = new JLabel("Datos bancarios");
		        lblDatosBancarios.setFont(new Font("Arial",Font.BOLD,15));
		        panelContenedorCentral.add(lblDatosBancarios);
		        
		        banco = new PanelTipoPreguntaUtil("Banco: ", "ALFANUMERICO");
		        numeroCuenta = new PanelTipoPreguntaUtil("Clabe o numero de cuenta: ", "NUMERICO");		        
		        sueldo = new PanelTipoPreguntaUtil("Sueldo", "NUMERICO");
		        
		        panelContenedorCentral.add(banco);
		        panelContenedorCentral.add(numeroCuenta);
		        panelContenedorCentral.add(sueldo);
		        
		        listaPreguntas.add(banco);
		        listaPreguntas.add(numeroCuenta);
		        listaPreguntas.add(sueldo);
		        
	    	return panelContenedorCentral;
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
	    
}
