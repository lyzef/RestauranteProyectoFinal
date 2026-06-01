package controller.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Comparator;

import javax.swing.JOptionPane;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.DefaultEventComboBoxModel;
import excepciones.invalidInput;
import models.ComponenteIngredienteReceta;
import models.MovimientoInventario;
import models.MovimientoInventario.tipoMovimiento;
import services.ComponenteService;
import services.InventarioService;
import utilidades.ValidadorEntradasTexto;
import utilidades.views.PanelTipoPreguntaUtil;
import views.Dialog.NewMovementDialog;

public class NewMovementDialogController {
	
	private InventarioService inventarioService;
	private ComponenteService componenteService;
	
	private NewMovementDialog view;
	private boolean esProduccion;
	private MovimientoInventario movimientoInventario;
	DefaultEventComboBoxModel<ComponenteIngredienteReceta> comboComponentes;
	

	public NewMovementDialogController(InventarioService inventarioService, ComponenteService componenteService,
			NewMovementDialog view, boolean esProduccion) {
		super();
		this.inventarioService = inventarioService;
		this.componenteService = componenteService;
		this.view = view;
		this.esProduccion = esProduccion;
		
		crearModelComponentes();
		
		view.mostrarMensajeValidacion("Para insumos inventariables");
		if(esProduccion) {
			mostrarFormularioParaProduccion();
			view.setTitulo("Produccion");
			view.mostrarMensajeValidacion("Para produccion en lotes");
		} 
		
		crearListeners();
		view.setVisible(true);
	}

	private void crearListeners() {
		// Evento Finalizar
		view.getBotonFinalizar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!comprobarFormulario()) {
					return;
				}
				
				int r = JOptionPane.showConfirmDialog(
					view, 
					"¿Guardar movimiento?", 
					"ATENCIÓN", 
					JOptionPane.YES_NO_OPTION
				);
				
				if(r == JOptionPane.NO_OPTION) {
					return;
				}
				
				// CORRECCIÓN: Solo cierra si el guardado fue exitoso
				if (guardarMovimientoInventario()) {
					cerrarVista();
				}
				
			}
		});
		
		// Evento Cerrar / Cancelar
		view.getBotonCerrar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cerrarVista();
			}
		});
		
		// Evento Cierre de Ventana
		view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cerrarVista();
			}
		});		
		
		// Evento Cambiar unidad de medida
		view.getComponenteNombre().addActionListener(e -> {
			ComponenteIngredienteReceta c = (ComponenteIngredienteReceta) view.getComponenteNombre().getSelectedItem();
			if (c != null && c.getUnidadMedida() != null) {
				view.getCantidad().setPregunta("Cantidad (" + c.getUnidadMedida().toString() + ")");
			}
		});
	}
	
	private void crearModelComponentes() {
		EventList<ComponenteIngredienteReceta> lista = new BasicEventList<>();
		lista.addAll(componenteService.getListaSoloLectura());
		
		if(esProduccion) {
			//Quita elementos no inventariables
			for (int i = lista.size() - 1; i >= 0; i--) {
				
			    if (lista.get(i).isInventariable()  == false || lista.get(i).isReceta() == false) {
			        lista.remove(i);
			    }
			}
		}else {
			for (int i = lista.size() - 1; i >= 0; i--) {
			    
			    if (lista.get(i).isReceta() || lista.get(i).isInventariable() == false) {
			        lista.remove(i);
			    }
			}
		}
		
		SortedList<ComponenteIngredienteReceta> listaOrdenada = new SortedList<>(
			lista, 
		    Comparator.comparing(ComponenteIngredienteReceta::getNombre) 
		);
		
		comboComponentes = new DefaultEventComboBoxModel<ComponenteIngredienteReceta>(listaOrdenada);
		view.setComboComponentesModel(comboComponentes);
	}
	
	private void mostrarFormularioParaProduccion() {
		view.getCosto_movimiento().setTextoEntrada("0");
		view.getCosto_movimiento().getJfieldEntrada().setEditable(false);
		
		view.getTipoMovimiento().setSelectedItem(tipoMovimiento.ENTRADA);
		view.getTipoMovimiento().setEnabled(false);
		
	}
	
	private void cerrarVista() {
		view.dispose();
		comboComponentes.dispose();
	}

	private boolean guardarMovimientoInventario() {
		movimientoInventario = new MovimientoInventario();
		ComponenteIngredienteReceta componenteSeleccionado = (ComponenteIngredienteReceta) view.getComponenteNombre().getSelectedItem();

		movimientoInventario.setComponente_id(componenteSeleccionado.getId());
		movimientoInventario.setTipo_movimiento((MovimientoInventario.tipoMovimiento) view.getTipoMovimiento().getSelectedItem());
		movimientoInventario.setCantidad(Double.parseDouble(view.getCantidad().getTextoEntrada()));
		movimientoInventario.setCosto_movimiento(Double.parseDouble(view.getCosto_movimiento().getTextoEntrada()));
		movimientoInventario.setMotivo(view.getMotivo().getTextoEntrada());
		
		// Solo para fines internos
		movimientoInventario.setComponente_nombre(componenteSeleccionado.getNombre());
		
		try {
			if(esProduccion) {
				inventarioService.guardarProduccion(movimientoInventario.getComponente_id(), movimientoInventario.getCantidad(), movimientoInventario.getMotivo());
			}else {
				inventarioService.guardarMovimientoInventario(movimientoInventario);
			}
			
			
			
		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(view, "Movimiento NO guardado");
			return false;
		}
		return true;
	}
	
	private boolean comprobarFormulario() {
		boolean listo = true;
		for(PanelTipoPreguntaUtil p : view.getListaDePreguntas()) {
			try {
				ValidadorEntradasTexto.validarContenido(p);
			} catch (invalidInput e) {
				p.setTextoError(e.getMessage());
				listo = false;
			}
		}
		
		if(view.getComponenteNombre().getSelectedItem() == null || view.getTipoMovimiento().getSelectedItem() == null) {
			view.mostrarMensajeValidacion("Elementos sin selección");
			listo = false;
		}
		return listo;
	}
}