package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.PanelTipoPreguntaUtil;

public class InventarioDialog extends JDialog{
	//String
	PanelTipoPreguntaUtil nombre;
	PanelTipoPreguntaUtil tipoComponente;
	PanelTipoPreguntaUtil unidadMedida;
	
	//Doubles
	PanelTipoPreguntaUtil costoUnitario;
	PanelTipoPreguntaUtil caloriasPorUnidad;
	//PanelTipoPreguntaUtil stockActual;
	PanelTipoPreguntaUtil stockMinimoBloqueo;
	PanelTipoPreguntaUtil stockMinimoAlerta;
	
	List <PanelTipoPreguntaUtil> listaDePreguntas;
	//Boolean
	JCheckBox esReceta;
	JCheckBox disponibilidadManual;
	JCheckBox esInventariable;
	
	//Propio del formulario
	JPanel panelFormulario;
	JLabel titulo;
	JButton botonFinalizar;
	JButton botonCerrar;
	
	
	
	public InventarioDialog(JFrame frame) {
		super(frame,true); 
		
		setSize(400,400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(true);
		setTitle("Formulario");
		setLocationRelativeTo(null);
		
		ImageIcon i = GeneradorIconos.cargarIcono("/assets/image/IconoApliacionPrincipal.jpg");
		if(i != null) {
			setIconImage(i.getImage());
		}
		
		inicializarComponentes();
		setVisible(true);
	}

	private void inicializarComponentes() {
		//Paneles
		JPanel panelContenedorSuperior = new JPanel();
		panelContenedorSuperior.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		
		panelFormulario = new JPanel();
		
		JPanel panelContenedorInferior = new JPanel();
		panelContenedorInferior.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		
		//Panel superior
		titulo = new JLabel("Nuevo componente");
		titulo.setFont(AppFont.title());
		titulo.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		panelContenedorSuperior.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panelContenedorSuperior.add(titulo);
		
		//Panel inferior
		botonFinalizar = new JButton("Siguiente");
		botonFinalizar.setBackground(Paleta_Colores.ACENTO_PRIMARIO.getColor());
		botonFinalizar.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelContenedorInferior.add(botonFinalizar);

		// Botón cancelar
        botonCerrar = new JButton("Cancelar");
        botonFinalizar.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        botonCerrar.setBackground(Paleta_Colores.URGENTE.getColor());
		panelContenedorInferior.add(botonCerrar);
        
		crearFormulario();
		
		//Anadiendo paneles
		add(panelFormulario,BorderLayout.CENTER); //Es el contenedor padre del panel cuestionario
		add(panelContenedorSuperior,BorderLayout.NORTH);
		add(panelContenedorInferior,BorderLayout.SOUTH);
	}

	private JScrollPane crearFormulario() {
		panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
		Border emptyBorder = BorderFactory.createEmptyBorder(10,20,10,20);
		panelFormulario.setBorder(emptyBorder);
		//panelFormulario.setBackground(Paleta_Colores.FONDO.getColor());
		
		listaDePreguntas = new ArrayList<PanelTipoPreguntaUtil>();

		nombre = new PanelTipoPreguntaUtil("Nombre", "ALFANUMERICO"); 
		tipoComponente = new PanelTipoPreguntaUtil("Tipo de componente", "ALFANUMERICO");
		unidadMedida = new PanelTipoPreguntaUtil("Unidad de medida del componente", "ALFABETICO");
		costoUnitario = new PanelTipoPreguntaUtil("Costo x unidad", "DECIMAL");
		caloriasPorUnidad = new PanelTipoPreguntaUtil("Calorias x unidad", "DECIMAL");
		stockMinimoAlerta = new PanelTipoPreguntaUtil("Stock minimo para alertar", "DECIMAL");
		stockMinimoBloqueo = new PanelTipoPreguntaUtil("Stock minimo para bloquear", "DECIMAL");

		listaDePreguntas.add(nombre);
		listaDePreguntas.add(tipoComponente);
		listaDePreguntas.add(unidadMedida);
		listaDePreguntas.add(costoUnitario);
		listaDePreguntas.add(caloriasPorUnidad);
		listaDePreguntas.add(stockMinimoAlerta);
		listaDePreguntas.add(stockMinimoBloqueo);
		
		for(PanelTipoPreguntaUtil pregunta :listaDePreguntas) {
			panelFormulario.add(pregunta);
		}
		
		esReceta = new JCheckBox("Es una receta?");
		
		disponibilidadManual = new JCheckBox("Esta disponible?");
		
		esInventariable = new JCheckBox("Cuenta para registro de inventario?");
		
		return new JScrollPane(panelFormulario);
	}
	
	
}
