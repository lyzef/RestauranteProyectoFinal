package controller.dialogs;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;
import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.DefaultEventComboBoxModel;
import excepciones.invalidInput;
import models.ComponenteIngredienteReceta;
import models.Estructura_receta;
import services.CalculoRecetaService;
import services.EstructuraRecetaService;
import utilidades.ValidadorEntradasTexto;
import utilidades.views.CardIngrediente;
import views.Dialog.RecetaDialog;

public class RecetaFormController {
	RecetaDialog view;
	//Servicios 
	CalculoRecetaService calculoRecetaService;
	
	//Listas
	EventList<ComponenteIngredienteReceta> listaComponentes;
	EventList<Estructura_receta> listaEstructuraReceta;
	ComponenteIngredienteReceta receta;
	
	//Lista de hijos de receta
	ArrayList<CardIngrediente>  listaCardIngredientes = new ArrayList<CardIngrediente>();
		 
	//Lista no modificable para comboBox
	DefaultEventComboBoxModel<ComponenteIngredienteReceta> modelComboBox; 
	
	//Lista de hijos actual
	 java.util.List<Estructura_receta> hijos = new ArrayList<Estructura_receta>();
	
	 boolean soloVer;
	 
	public RecetaFormController(EventList<ComponenteIngredienteReceta> listaComponentes,EventList<Estructura_receta> listaEstructuraReceta, RecetaDialog view,
			ComponenteIngredienteReceta receta, boolean soloVer, CalculoRecetaService calculoRecetaService) {
		
		this.view = view;
		this.listaComponentes = listaComponentes;
		this.listaEstructuraReceta = listaEstructuraReceta;
		this.receta = receta;
		this.soloVer = soloVer;
		this.calculoRecetaService = calculoRecetaService;
		
		cargarComboBoxSeleccion();
		addListeners();
		cargarHijos();
		cargarContenidoReceta();
		
		if(soloVer) {
			view.setTitulo(receta.getNombre());
			view.setSubTitulo("Mostrando sub-recetas e ingredientes");
			desactivarInteracciones();
		}else {
			view.setTitulo(receta.getNombre());
			view.setSubTitulo("Modificando sub-recetas e ingredientes");
		}
		
		

			
		view.setVisible(true);
	}
	
	
	private void addListeners() {
		view.getBotonCerrar().addActionListener(e -> {
			if(view.solicitarCierre("Cerrar formulario?") == JOptionPane.YES_OPTION) {
				modelComboBox.dispose();
				view.dispose();
			}
		});
		
		view.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	if(view.solicitarCierre("Cerrar formulario?") == JOptionPane.YES_OPTION) {
					modelComboBox.dispose();
					view.dispose();
				}
		    }
		});	
		
		view.getBotonAgregarIngrediente().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				if(soloVer) {return;}
				
				//Buscar si el ingrediente es duplicado
				ComponenteIngredienteReceta componenteAgregar = (ComponenteIngredienteReceta)view.getIngredientesComboBox().getSelectedItem();
				if(componenteAgregar == null) {return;}
				
				for (CardIngrediente hijo : listaCardIngredientes) {
				   
				    if (hijo.getIngrediente().getId() == componenteAgregar.getId()) {
				    	view.mostrarDialogMensaje("Ingrediente ya añadido");
				    	return;
				    }
				}
				crearPanelIngrediente(componenteAgregar,1,false); // 1 unidad de defecto y falso de defecto
				guardarListaHijos();
		    }
		});
		
		view.getBotonFinalizar().addActionListener(e -> {
			if(view.solicitarCierre("Guardar modificaciones") == JOptionPane.YES_OPTION) {
				guardarListaHijos();
				
				String calorias = view.getCampoCaloriasTotales().getText();
				String precio = view.getCampoPrecioTotal().getText();
				try {
					ValidadorEntradasTexto.validarContenido(calorias,"DECIMAL");
					ValidadorEntradasTexto.validarContenido(precio,"DECIMAL");
					
					receta.setCaloriasPorUnidad(Double.parseDouble(calorias));
					receta.setCostoUnitario(Double.parseDouble(precio));
					
					modelComboBox.dispose();
					view.dispose();
				} catch (invalidInput e1) {
					new JOptionPane().showMessageDialog(null, "Contenido invalido escrito");
				}
				
				
			}
		});
		
		view.getBtnRefrescarPrecio().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				refrescarPrecioCalorias();
			}
		});
		
		view.getBtnRefrescarCalorias().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				refrescarPrecioCalorias();
			}
		});
	}
	
	private void vincularListener(CardIngrediente card) {
		//Elimina un objeto de la view y se remueve de la lista de cards y revalida los hijos existentes
		card.getBotonEliminar().addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(soloVer) {
    				return;
    			}
            	
                Container contenedorPadre = card.getParent();
                if (contenedorPadre != null) {
                    contenedorPadre.remove(card);
                    contenedorPadre.revalidate();
                    contenedorPadre.repaint();
                }
                
                listaCardIngredientes.remove(card);
                guardarListaHijos();
            }
        });
		
		card.getCantidadField().addActionListener(e -> {
			guardarListaHijos();
		});
	}
	
	
	//Controla los cardIngrediente que contienen a los hijos de la receta
	private void crearPanelIngrediente(ComponenteIngredienteReceta receta, double cantidad, boolean opcional) {
		CardIngrediente card = new CardIngrediente(receta);
		card.setCantidadYEstadoVisible(cantidad,opcional);
		listaCardIngredientes.add(card);
		vincularListener(card);
		view.agregarCardIngrediente(card);
		
	}
	
	//Carga y filtra el mismo componente (Evita recursividad infinita)
	private void cargarComboBoxSeleccion() {
		EventList<ComponenteIngredienteReceta> listaTemp = new BasicEventList<ComponenteIngredienteReceta>();
		listaTemp.addAll(listaComponentes);
		listaTemp.remove(receta);
		
		SortedList<ComponenteIngredienteReceta> listaOrdenada = new SortedList<>(
				listaTemp, 
			    Comparator.comparing(ComponenteIngredienteReceta::getNombre) 
			);
		
		modelComboBox = new DefaultEventComboBoxModel<ComponenteIngredienteReceta>(listaOrdenada);
		view.setComboBoxModel(modelComboBox);
	}
	
	private void desactivarInteracciones() {
		view.getBotonFinalizar().setVisible(false);
		
		for(CardIngrediente card : listaCardIngredientes) {
			card.setEditableTextField(false);
			card.setEnabledCheckBox(false);
			card.setEnableEliminar(false);
		}
	}
	
	private void cargarHijos() {
		for(Estructura_receta recetaHijo : listaEstructuraReceta) {
			if(recetaHijo.getParent_id() == receta.getId()) {
				for(ComponenteIngredienteReceta ingrediente : listaComponentes) {
					if(ingrediente.getId() == recetaHijo.getChild_id()) {
						crearPanelIngrediente(ingrediente,recetaHijo.getCantidad(),recetaHijo.isEs_opcional());
					}
				}
				
			}
		}
		guardarListaHijos();
	}
	
	//Guarda los cambios y descarta invalidos
	private void guardarListaHijos() {
		hijos = new ArrayList<>();
		for(CardIngrediente card : listaCardIngredientes) {
			Estructura_receta et = new Estructura_receta(receta.getId(),
					card.getIngrediente().getId() , card.getCantidad(), card.isOpcional());
			
			if(et.getCantidad() > 0) {
				hijos.add(et);
			}
		}
	}
	
	private void refrescarPrecioCalorias() {
		if(hijos != null) {
			double precio = calculoRecetaService.calcularCostoTotal(hijos);
			double calorias = calculoRecetaService.calcularCaloriasTotales(hijos);
			view.setPrecioSugerido(precio);
			view.setCaloriasSugeridas(calorias);
		}
	}
	
	private void cargarContenidoReceta() {
		view.getCampoCaloriasTotales().setText(Double.toString(receta.getCaloriasPorUnidad()) );
		view.getCampoPrecioTotal().setText(Double.toString(receta.getCostoUnitario()));
	}
	
	public java.util.List<Estructura_receta> getHijos() {
		return hijos;
	}
	
	
}
