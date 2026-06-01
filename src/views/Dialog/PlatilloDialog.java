package views.Dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
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

import ca.odell.glazedlists.swing.DefaultEventComboBoxModel;
import models.Categoria;
import models.ComponenteIngredienteReceta;
import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.BotonPersonalizado;
import utilidades.views.PanelRedondeadoConMargen;
import utilidades.views.PanelTipoPreguntaUtil;

public class PlatilloDialog extends JDialog{
		//Propio de categoria
		private JComboBox<ImageIcon> imagenComboBox;
		private PanelTipoPreguntaUtil descripcion;
		private PanelTipoPreguntaUtil precio;
		private JComboBox<Categoria> categoriasComboBox;
		private JComboBox<ComponenteIngredienteReceta> recetaComboBox;
		
		
		//Propio del formulario
		private JPanel panelFormulario;
		private JLabel titulo;
		private JLabel subTitulo;
		private List <PanelTipoPreguntaUtil> listaDePreguntas;
		
		private JButton botonFinalizar;
		private JButton botonCerrar;
		private BotonPersonalizado botonSubirImagen;
		
		
		public PlatilloDialog(JFrame frame) {
			super(frame,true); 
			
			setSize(400,500);
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
			titulo = new JLabel("Platillo");
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
			botonFinalizar = new JButton("Guardar");
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

		private JScrollPane crearFormulario() {
			panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
			Border emptyBorder = BorderFactory.createEmptyBorder(10,20,10,20);
			panelFormulario.setBorder(emptyBorder);
			listaDePreguntas = new ArrayList<PanelTipoPreguntaUtil>();
			
			panelFormulario.add(moduloSubirSeleccionarImagen());
			
			precio = new PanelTipoPreguntaUtil("Precio (MXN)", "PRECIO");
			descripcion = new PanelTipoPreguntaUtil("Descripcion", "ALFANUMERICO");
			
			listaDePreguntas.add(precio);
			listaDePreguntas.add(descripcion);
			
			for(PanelTipoPreguntaUtil pregunta :listaDePreguntas) {
				panelFormulario.add(pregunta);
			}
			
			JLabel lblCategoria = new JLabel("Categoria platillo");
			lblCategoria.setAlignmentX(RIGHT_ALIGNMENT);
			categoriasComboBox = new JComboBox<>();
			
			panelFormulario.add(lblCategoria);
			panelFormulario.add(categoriasComboBox);
			
			JLabel lblRecetas = new JLabel("Enlazar receta");
			lblRecetas.setAlignmentX(RIGHT_ALIGNMENT);
			recetaComboBox = new JComboBox<>();
			
			panelFormulario.add(lblRecetas);
			panelFormulario.add(recetaComboBox);
			panelFormulario.add(Box.createRigidArea(new  Dimension(0,25)));
			return new JScrollPane(panelFormulario);
		}
		
		public JPanel moduloSubirSeleccionarImagen() {
			JPanel panel = new PanelRedondeadoConMargen(Paleta_Colores.CONTENEDORES.getColor());
			panel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			
			JLabel labelImagen = new JLabel("Seleccionar o subir imagen");
			labelImagen.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
			imagenComboBox = new JComboBox<ImageIcon>();
			
			botonSubirImagen = new BotonPersonalizado("Subir", Paleta_Colores.ACENTO_PRIMARIO.getColor());
			
			gbc.fill = GridBagConstraints.NONE;
			gbc.weightx = 1; 
			
			gbc.gridy = 0;
	        gbc.gridx = 0;
	        gbc.gridwidth = 2; 
	        gbc.anchor = GridBagConstraints.WEST;
	        gbc.insets = new Insets(5, 5, 5, 5);
	        panel.add(labelImagen,gbc);
	        gbc.gridy = 1;
	        gbc.gridx = 0;
	        gbc.gridwidth = 1; 
	        gbc.anchor = GridBagConstraints.WEST;
	        panel.add(imagenComboBox,gbc);
	        gbc.gridy = 1;
	        gbc.gridx = 1;
	        gbc.anchor = GridBagConstraints.EAST;
	        panel.add(botonSubirImagen,gbc);
	        
			
			return panel;
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
			descripcion.setEnabled(false);
			precio.setEnabled(false);
			imagenComboBox.setEnabled(false);
			categoriasComboBox.setEnabled(false);
			recetaComboBox.setEnabled(false);
		}
		
		public void asignarComboBoxs(DefaultEventComboBoxModel<ImageIcon> imagenesModel,
				DefaultComboBoxModel<Categoria> categoriaModel,
				DefaultComboBoxModel<ComponenteIngredienteReceta> componentesModel) {
			imagenComboBox.setModel(imagenesModel);
			categoriasComboBox.setModel(categoriaModel);
			recetaComboBox.setModel(componentesModel);
			
		}

		public JButton getBotonFinalizar() {
			return botonFinalizar;
		}

		public JButton getBotonCerrar() {
			return botonCerrar;
		}

		public void setTituloText(String titulo) {
			this.titulo.setText(titulo);
		}

		public void setSubTitulo(String subTitulo) {
			this.subTitulo.setText(subTitulo);
		}

		public List<PanelTipoPreguntaUtil> getListaDePreguntas() {
			return listaDePreguntas;
		}

		public BotonPersonalizado getBotonSubirImagen() {
			return botonSubirImagen;
		}

		public JComboBox<ImageIcon> getImagenComboBox() {
			return imagenComboBox;
		}

		public JComboBox<Categoria> getCategoriasComboBox() {
			return categoriasComboBox;
		}

		public JComboBox<ComponenteIngredienteReceta> getRecetaComboBox() {
			return recetaComboBox;
		}

		public String getDescripcion() {
			return descripcion.getTextoEntrada();
		}

		public String getPrecio() {
			return precio.getTextoEntrada();
		}

		public void setDescripcion(String descripcion) {
			this.descripcion.setTextoEntrada(descripcion);
		}

		public void setPrecio(String precio) {
			this.precio.setTextoEntrada(precio);
		}
		
		
		
		
}
