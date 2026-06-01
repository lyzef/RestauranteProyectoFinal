package views.Dialog;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ca.odell.glazedlists.swing.DefaultEventComboBoxModel;
import controller.PreguntaController;
import models.ComponenteIngredienteReceta;
import models.MovimientoInventario.tipoMovimiento;
import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.PanelTipoPreguntaUtil;

public class NewMovementDialog extends JDialog {
	
	// Elementos visuales
	private JPanel panelFormulario;
	private JLabel titulo;
	private JLabel subTitulo;
	private JButton botonFinalizar;
	private JButton botonCerrar;
	
	private JComboBox<ComponenteIngredienteReceta> componenteNombre;
	private JComboBox<tipoMovimiento> tipoMovimiento;
	private PanelTipoPreguntaUtil cantidad;
	private PanelTipoPreguntaUtil costo_movimiento;
	private PanelTipoPreguntaUtil motivo;

	private List<PanelTipoPreguntaUtil> listaDePreguntas;
	
	
	public NewMovementDialog(JFrame frame) {
		super(frame, true); 
		
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(true);
		setTitle("Formulario");
		setLocationRelativeTo(null);
		
		ImageIcon i = GeneradorIconos.cargarIcono("/assets/image/IconoApliacionPrincipal.jpg");
		if(i != null) {
			setIconImage(i.getImage());
		}
		
		inicializarComponentes();
		
		for(PanelTipoPreguntaUtil p : listaDePreguntas) { 
			PreguntaController.registrarPanel(p);
		}
		
	}

	private void inicializarComponentes() {
		// Paneles
		JPanel panelContenedorSuperior = new JPanel();
		panelContenedorSuperior.setLayout(new BoxLayout(panelContenedorSuperior, BoxLayout.Y_AXIS));
		panelContenedorSuperior.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		
		JPanel panelContenedorInferior = new JPanel();
		panelContenedorInferior.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		
		// Panel superior
		titulo = new JLabel("Movimiento inventario");
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
		
		// Panel inferior
		botonFinalizar = new JButton("Terminar");
		botonFinalizar.setBackground(Paleta_Colores.ACENTO_PRIMARIO.getColor());
		botonFinalizar.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelContenedorInferior.add(botonFinalizar);

		// Botón cancelar
		botonCerrar = new JButton("Cancelar");
		botonCerrar.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		botonCerrar.setBackground(Paleta_Colores.URGENTE.getColor());
		panelContenedorInferior.add(botonCerrar);
		
		// Añadiendo paneles
		add(crearFormulario(), BorderLayout.CENTER); 
		add(panelContenedorSuperior, BorderLayout.NORTH);
		add(panelContenedorInferior, BorderLayout.SOUTH);
	}
	
	private JScrollPane crearFormulario() {
		panelFormulario = new JPanel();
		panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
		panelFormulario.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
		
		JLabel tituloComponenteNombre = new JLabel("Componente");
		componenteNombre = new JComboBox<>();
		
		JLabel titulotipoMovimiento = new JLabel("Tipo de movimiento");
		this.tipoMovimiento = new JComboBox<>(models.MovimientoInventario.tipoMovimiento.values());
		
		panelFormulario.add(tituloComponenteNombre);
		panelFormulario.add(componenteNombre);
		panelFormulario.add(titulotipoMovimiento);
		panelFormulario.add(this.tipoMovimiento);
		
		cantidad = new PanelTipoPreguntaUtil("Cantidad (Unidad del ingrediente)", "DECIMAL");
		costo_movimiento = new PanelTipoPreguntaUtil("Costo de movimiento (MXN)", "DECIMAL");
		motivo = new PanelTipoPreguntaUtil("Motivo", "ALFANUMERICO");
		
		listaDePreguntas = new ArrayList<>();
		listaDePreguntas.add(cantidad);
		listaDePreguntas.add(costo_movimiento);
		listaDePreguntas.add(motivo);
		
		for(PanelTipoPreguntaUtil pregunta : listaDePreguntas) {
			panelFormulario.add(pregunta);
		}
		
		return new JScrollPane(panelFormulario);
	}


	public void mostrarMensajeValidacion(String mensaje) {
		subTitulo.setText(mensaje);
	}
	
	public void setComboComponentesModel( DefaultEventComboBoxModel<ComponenteIngredienteReceta> comboComponentes) {
		componenteNombre.setModel(comboComponentes);
	}

	public JButton getBotonFinalizar() { return botonFinalizar; }
	public JButton getBotonCerrar() { return botonCerrar; }
	public JComboBox<ComponenteIngredienteReceta> getComponenteNombre() { return componenteNombre; }
	public JComboBox<tipoMovimiento> getTipoMovimiento() { return tipoMovimiento; }
	public PanelTipoPreguntaUtil getCantidad() { return cantidad; }
	public PanelTipoPreguntaUtil getCosto_movimiento() { return costo_movimiento; }
	public PanelTipoPreguntaUtil getMotivo() { return motivo; }
	public List<PanelTipoPreguntaUtil> getListaDePreguntas() { return listaDePreguntas; }
	public void setTitulo(String titulo) {this.titulo.setText(titulo);}
}