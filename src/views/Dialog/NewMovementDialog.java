package views.Dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ca.odell.glazedlists.swing.DefaultEventComboBoxModel;
import ca.odell.glazedlists.swing.EventComboBoxModel;
import controller.InventarioController;
import controller.PreguntaController;
import excepciones.invalidInput;
import models.ComponenteIngredienteReceta;
import models.MovimientoInventario;
import models.MovimientoInventario.tipoMovimiento;
import repository.InventarioRepository;
import repository.LoginRepository;
import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.Session;
import utilidades.ValidadorEntradasTexto;
import utilidades.views.PanelTipoPreguntaUtil;

public class NewMovementDialog extends JDialog{
	boolean movimientoGuardado = false;
	
	//Propio del formulario
	JPanel panelFormulario;
	JLabel titulo;
	JLabel subTitulo;
	JButton botonFinalizar;
	JButton botonCerrar;
	
	//Elementos visuales
	JComboBox<ComponenteIngredienteReceta> componenteNombre;
	JComboBox<tipoMovimiento> tipoMovimiento;
	PanelTipoPreguntaUtil cantidad;
	PanelTipoPreguntaUtil costo_movimiento;
	PanelTipoPreguntaUtil motivo;

	List <PanelTipoPreguntaUtil> listaDePreguntas;
	
	MovimientoInventario movimientoInventario;
	DefaultEventComboBoxModel<ComponenteIngredienteReceta> comboComponentes;
	
	public NewMovementDialog(JFrame frame, 	DefaultEventComboBoxModel<ComponenteIngredienteReceta> comboComponentes) {
		super(frame,true); 
		this.comboComponentes = comboComponentes;
		
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
		crearListeners();
		
		for(PanelTipoPreguntaUtil  p : listaDePreguntas) { //Registrar panel
			PreguntaController.registrarPanel(p);
		}
		
		this.setVisible(true);
	}

	private void inicializarComponentes() {
		//Paneles
		JPanel panelContenedorSuperior = new JPanel();
		panelContenedorSuperior.setLayout(new BoxLayout(panelContenedorSuperior, BoxLayout.Y_AXIS));
		panelContenedorSuperior.setBackground(Paleta_Colores.CONTENEDORES.getColor());
		
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
	
	private void crearListeners() {
		botonFinalizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int r = JOptionPane.showConfirmDialog( 
					null, 
                    "Guardar movimiento?", 
                    "ATENCION", 
                    JOptionPane.YES_NO_OPTION
				);
				
				if(r == JOptionPane.NO_OPTION) {
					return;
				}
				
				if(comprobarFormulario() && !movimientoGuardado) {
					guardarMovimientoInventario();
					comboComponentes.dispose();
					dispose();
				}
				
			}
		});
		
		botonCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				comboComponentes.dispose();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	dispose();
				comboComponentes.dispose();
		    }
		});		
		
		//Cambia la unidad de medida a mostrar
		componenteNombre.addActionListener(e -> {
			ComponenteIngredienteReceta c = (ComponenteIngredienteReceta) componenteNombre.getSelectedItem();
			cantidad.setPregunta("Cantidad (" + c.getUnidadMedida().toString()+")");
		});
		
	}
	
	private JPanel crearFormulario() {
		panelFormulario = new JPanel();
		panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
		panelFormulario.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
		
		JLabel tituloComponenteNombre = new JLabel("Componente");
		componenteNombre = new JComboBox<ComponenteIngredienteReceta>(comboComponentes);
		
		JLabel titulotipoMovimiento = new JLabel("Tipo de movimiento");
		tipoMovimiento = new JComboBox<tipoMovimiento>(MovimientoInventario.tipoMovimiento.values());
		
		panelFormulario.add(tituloComponenteNombre);
		panelFormulario.add(componenteNombre);
		panelFormulario.add(titulotipoMovimiento);
		panelFormulario.add(tipoMovimiento);
		
		cantidad = new PanelTipoPreguntaUtil("Cantidad (Unidad del ingrediente)", "DECIMAL");
		costo_movimiento = new PanelTipoPreguntaUtil("Costo de movimiento (MXN)", "DECIMAL");
		motivo = new PanelTipoPreguntaUtil("Motivo", "ALFANUMERICO");
		
		listaDePreguntas = new ArrayList<PanelTipoPreguntaUtil>();
		listaDePreguntas.add(cantidad);
		listaDePreguntas.add(costo_movimiento);
		listaDePreguntas.add(motivo);
		
		for(PanelTipoPreguntaUtil pregunta :listaDePreguntas) {
			panelFormulario.add(pregunta);
		}
		
		return (panelFormulario);
	}
	
	private void guardarMovimientoInventario() {
		movimientoInventario = new MovimientoInventario();
		movimientoInventario.setComponente_id(((ComponenteIngredienteReceta)componenteNombre.getSelectedItem()).getId());
		movimientoInventario.setTipo_movimiento((models.MovimientoInventario.tipoMovimiento) tipoMovimiento.getSelectedItem());
		movimientoInventario.setCantidad(Double.parseDouble(cantidad.getTextoEntrada()));
		movimientoInventario.setCosto_movimiento(Double.parseDouble(costo_movimiento.getTextoEntrada()));
		movimientoInventario.setMotivo(motivo.getTextoEntrada());
		
		// Solo para fines internos
		movimientoInventario.setComponente_nombre(((ComponenteIngredienteReceta)componenteNombre.getSelectedItem()).getNombre());
		movimientoGuardado = true;
	}
	
	private boolean comprobarFormulario() {
		boolean listo = true;
		for(PanelTipoPreguntaUtil  p : listaDePreguntas) {
			try {
				ValidadorEntradasTexto.validarContenido(p);
			} catch (invalidInput e) {
				subTitulo.setText(e.getMessage());
				listo = false;
			}
		}
		
		if(componenteNombre.getSelectedItem() == null || tipoMovimiento == null) {
			subTitulo.setText("Elementos sin seleccion");
			listo = false;
		}
		return listo;
	}

	public boolean isMovimientoGuardado() {
		return movimientoGuardado;
	}

	public MovimientoInventario getMovimientoInventario() {
		return movimientoInventario;
	}

	public void setMovimientoGuardado(boolean movimientoGuardado) {
		this.movimientoGuardado = movimientoGuardado;
	}

	public void setMovimientoInventario(MovimientoInventario movimientoInventario) {
		this.movimientoInventario = movimientoInventario;
	}
	
	
}
