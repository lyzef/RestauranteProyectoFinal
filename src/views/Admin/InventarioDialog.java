package views.Admin;

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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import models.ComponenteIngredienteReceta.Unidad;
import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.PanelTipoPreguntaUtil;

public class InventarioDialog extends JDialog{
	//String
	PanelTipoPreguntaUtil nombre;
	PanelTipoPreguntaUtil tipoComponente;
	JComboBox<Unidad> unidadMedida;
	
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
	JLabel subTitulo;
	
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
		titulo = new JLabel("Nuevo componente");
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
		add(crearFormulario(),BorderLayout.CENTER); //Es el contenedor padre del panel cuestionario
		add(panelContenedorSuperior,BorderLayout.NORTH);
		add(panelContenedorInferior,BorderLayout.SOUTH);
	}

	private JScrollPane crearFormulario() {
		panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
		Border emptyBorder = BorderFactory.createEmptyBorder(10,20,10,20);
		panelFormulario.setBorder(emptyBorder);
		listaDePreguntas = new ArrayList<PanelTipoPreguntaUtil>();
		nombre = new PanelTipoPreguntaUtil("Nombre", "ALFANUMERICO"); 
		tipoComponente = new PanelTipoPreguntaUtil("Tipo de componente", "ALFANUMERICO");
		
		
		
		costoUnitario = new PanelTipoPreguntaUtil("Costo x unidad", "DECIMAL");
		caloriasPorUnidad = new PanelTipoPreguntaUtil("Calorias x unidad", "DECIMAL");
		stockMinimoAlerta = new PanelTipoPreguntaUtil("Stock minimo para alertar", "DECIMAL");
		stockMinimoBloqueo = new PanelTipoPreguntaUtil("Stock minimo para bloquear", "DECIMAL");

		listaDePreguntas.add(nombre);
		listaDePreguntas.add(tipoComponente);
		listaDePreguntas.add(costoUnitario);
		listaDePreguntas.add(caloriasPorUnidad);
		listaDePreguntas.add(stockMinimoAlerta);
		listaDePreguntas.add(stockMinimoBloqueo);
		
		for(PanelTipoPreguntaUtil pregunta :listaDePreguntas) {
			panelFormulario.add(pregunta);
		}
		
		JLabel labelUnidad = new JLabel("Selecciona la unidad de medida");
		labelUnidad.setAlignmentX(LEFT_ALIGNMENT);
		panelFormulario.add(labelUnidad);
		unidadMedida = new JComboBox<Unidad>(Unidad.values());
		panelFormulario.add(unidadMedida);
		
		esReceta = new JCheckBox("Es una receta?");
		panelFormulario.add(esReceta);
		disponibilidadManual = new JCheckBox("Esta disponible?");
		panelFormulario.add(disponibilidadManual);
		esInventariable = new JCheckBox("Cuenta para registro de inventario?");
		panelFormulario.add(esInventariable);
		
		return new JScrollPane(panelFormulario);
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
	
	
	//Getters y settes
	public JButton getBotonFinalizar() {
		return botonFinalizar;
	}

	public JButton getBotonCerrar() {
		return botonCerrar;
	}

	public String getNombre() {
        return nombre.getTextoEntrada();
    }

    public String getTipoComponente() {
        return tipoComponente.getTextoEntrada();
    }

    public Unidad getUnidadMedida() {
        return (Unidad) unidadMedida.getSelectedItem();
    }

    public String getCostoUnitario() {
        return costoUnitario.getTextoEntrada();
    }

    public String getCaloriasPorUnidad() {
        return caloriasPorUnidad.getTextoEntrada();
    }

    public String getStockMinimoBloqueo() {
        return stockMinimoBloqueo.getTextoEntrada();
    }

    public String getStockMinimoAlerta() {
        return stockMinimoAlerta.getTextoEntrada();
    }

	public List<PanelTipoPreguntaUtil> getListaDePreguntas() {
		return listaDePreguntas;
	}

	public Boolean getEsReceta() {
		return esReceta.isSelected();
	}

	public Boolean getDisponibilidadManual() {
		return disponibilidadManual.isSelected();
	}

	public Boolean getEsInventariable() {
		return esInventariable.isSelected();
	}

	public JPanel getPanelFormulario() {
		return panelFormulario;
	}

	public JLabel getTitulo() {
		return titulo;
	}

	public void setNombre(String nombre) {
        this.nombre.setTextoEntrada(nombre);
    }

    public void setTipoComponente(String texto) {
        this.tipoComponente.setTextoEntrada(texto);
    }

    public void setUnidadMedida(Unidad unidadMedida) {
        this.unidadMedida.setSelectedItem(unidadMedida);;
    }

    public void setCostoUnitario(String costoUnitario) {
        this.costoUnitario.setTextoEntrada(costoUnitario);
    }

    public void setCaloriasPorUnidad(String caloriasPorUnidad) {
        this.caloriasPorUnidad.setTextoEntrada(caloriasPorUnidad);
    }

    public void setStockMinimoBloqueo(String stockMinimoBloqueo) {
        this.stockMinimoBloqueo.setTextoEntrada(stockMinimoBloqueo);
    }

    public void setStockMinimoAlerta(String stockMinimoAlerta) {
        this.stockMinimoAlerta.setTextoEntrada(stockMinimoAlerta);
    }

	public void setEsReceta(Boolean esReceta) {
		this.esReceta.setSelected(esReceta);
	}

	public void setDisponibilidadManual(Boolean disponibilidadManual) {
		this.disponibilidadManual.setSelected(disponibilidadManual);
	}

	public void setEsInventariable(Boolean esInventariable) {
		this.esInventariable.setSelected(esInventariable);
	}

	public void setTitulo(String titulo) {
		this.titulo.setText(titulo);
	}
	
	public void setSubTitulo(String titulo) {
		this.subTitulo.setText(titulo);
	}
	
	
	
	
}
