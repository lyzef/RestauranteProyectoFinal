package views.Dialog;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import models.ComponenteIngredienteReceta.Unidad;
import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.PanelTipoPreguntaUtil;

public class CategoriaDialog extends JDialog{
	
	//Propio de categoria
	private PanelTipoPreguntaUtil nombre;
	private PanelTipoPreguntaUtil descripcion;
	private JCheckBox caracteristicaActiva;
	
	//Propio del formulario
	private JPanel panelFormulario;
	private JLabel titulo;
	private JLabel subTitulo;
	private List <PanelTipoPreguntaUtil> listaDePreguntas;
	
	private JButton botonFinalizar;
	private JButton botonCerrar;
	
	
	public CategoriaDialog(JFrame frame) {
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
	}

	private void inicializarComponentes() {
		//Paneles
		JPanel panelContenedorSuperior = new JPanel();
		panelContenedorSuperior.setLayout(new BoxLayout(panelContenedorSuperior, BoxLayout.Y_AXIS));
		panelContenedorSuperior.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		
		panelFormulario = new JPanel();
		
		JPanel panelContenedorInferior = new JPanel();
		panelContenedorInferior.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		
		//Panel superior
		titulo = new JLabel("Categoria");
		titulo.setFont(AppFont.title());
		titulo.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		titulo.setAlignmentX(CENTER_ALIGNMENT);
		subTitulo = new JLabel(" ");
		subTitulo.setFont(AppFont.normal());
		subTitulo.setForeground(Paleta_Colores.ATENCION.getColor());
		subTitulo.setAlignmentX(CENTER_ALIGNMENT);
		
		panelContenedorSuperior.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panelContenedorSuperior.add(titulo);
		panelContenedorSuperior.add(subTitulo);
		
		//Panel inferior
		botonFinalizar = new JButton("Terminar");
		botonFinalizar.setBackground(Paleta_Colores.ACENTO_PRIMARIO.getColor());
		botonFinalizar.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelContenedorInferior.add(botonFinalizar);

		// Botón cancelar
        botonCerrar = new JButton("Cancelar");
        botonFinalizar.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        botonCerrar.setBackground(Paleta_Colores.URGENTE.getColor());
		panelContenedorInferior.add(botonCerrar);
        
		
		//Anadiendo paneles
		add(crearFormulario(),BorderLayout.CENTER); 
		add(panelContenedorSuperior,BorderLayout.NORTH);
		add(panelContenedorInferior,BorderLayout.SOUTH);
	}

	private JPanel crearFormulario() {
		panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
		Border emptyBorder = BorderFactory.createEmptyBorder(10,20,10,20);
		panelFormulario.setBorder(emptyBorder);
		listaDePreguntas = new ArrayList<PanelTipoPreguntaUtil>();
		
		nombre = new PanelTipoPreguntaUtil("Nombre", "ALFANUMERICO"); 
		descripcion = new PanelTipoPreguntaUtil("Descripcion", "ALFANUMERICO");
		
		listaDePreguntas.add(nombre);
		listaDePreguntas.add(descripcion);
		
		for(PanelTipoPreguntaUtil pregunta :listaDePreguntas) {
			panelFormulario.add(pregunta);
		}
		
		JLabel labelActivo = new JLabel("Esta activo?");
		labelActivo.setAlignmentX(LEFT_ALIGNMENT);
		panelFormulario.add(labelActivo);
		caracteristicaActiva = new JCheckBox();
		panelFormulario.add(caracteristicaActiva);
		
		return panelFormulario;
	}
	
	public int solicitarCierre(String texto) {
	    // Muestra el diálogo y guarda la respuesta (0 = Si, 1 = No)
	    int respuesta = JOptionPane.showConfirmDialog(
	        null, 
	        texto, 
	        "Confirmación", 
	        JOptionPane.YES_NO_OPTION
	    );

	    return respuesta;
	}
	
	public void desactivarEntradas() {
		nombre.setEnabled(false);
		descripcion.setEnabled(false);
		caracteristicaActiva.setEnabled(false);
	}
	
    public String getNombreText() {
        return nombre.getTextoEntrada();
    }

    public String getDescripcionText() {
        return descripcion.getTextoEntrada();
    }

    public boolean getCaracteristicaActiva() {
        return caracteristicaActiva.isSelected();
    }

    public void setNombre(String nombre) {
		this.nombre.setTextoEntrada(nombre);
	}

	public void setDescripcion(String descripcion) {
		this.descripcion.setTextoEntrada(descripcion);
	}

	public void setCaracteristicaActiva(boolean caracteristicaActiva) {
		this.caracteristicaActiva.setSelected(caracteristicaActiva);
	}

	public void setTituloText(String texto) {
        titulo.setText(texto);
    }

    public void setSubTituloText(String texto) {
        subTitulo.setText(texto);
    }

    public JButton getBotonFinalizar() {
        return botonFinalizar;
    }

    public JButton getBotonCerrar() {
        return botonCerrar;
    }
    
    public List<PanelTipoPreguntaUtil> getListaPreguntas(){
    	return listaDePreguntas;
    }
	
}
