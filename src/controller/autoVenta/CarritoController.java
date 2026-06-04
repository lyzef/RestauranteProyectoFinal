package controller.autoVenta;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;
import models.ComponenteIngredienteReceta;
import models.Platillo;
import services.CarritoService;
import services.CarritoService.ItemCarrito;
import services.VentasService;
import utilidades.views.CardCarritoPlatillo;
import utilidades.views.CardIngrediente;
import views.AutoVenta.CarritoView;

public class CarritoController {
	CarritoView view;
	CarritoService carritoService;
	VentasService ventasService;
	HubVentaController hubVentaController;
	
	ArrayList<CardCarritoPlatillo>  listCardPlatillos = new ArrayList<CardCarritoPlatillo>();
	EventList<ItemCarrito> carrito;
	
	public CarritoController(CarritoView view, CarritoService carritoService,
			HubVentaController hubVentaController,VentasService ventasService) {
		super();
		this.view = view;
		this.carritoService = carritoService;
		this.hubVentaController = hubVentaController;
		this.ventasService = ventasService;
		
		carrito = carritoService.getCarrito();
		crearCarrito();
		addListeners();
	}
	
	private void addListeners() {
		carritoService.getOnlyReadCarrito().addListEventListener(new ListEventListener<ItemCarrito>() {
			@Override
			public void listChanged(ListEvent<ItemCarrito> tipoEvento) {
	               calcularAtributosDeCarrito();
			}
			
		});
		
		view.getBtnPedir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					ventasService.realizarVenta();
				} catch (Exception error) {
					JOptionPane.showMessageDialog(view, error.getMessage());
					error.printStackTrace();
				} finally {
					hubVentaController.showMenuReset();
				}
				
			}
		});
		
	}
	
	private void vincularListener(CardCarritoPlatillo card) {
		//Elimina un objeto de la view y se remueve de la lista de cards y revalida los hijos existentes
		card.getBotonEliminar().addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseClicked(MouseEvent e) {
                Container contenedorPadre = card.getParent();
                if (contenedorPadre != null) {
                    contenedorPadre.remove(card);
                    contenedorPadre.revalidate();
                    contenedorPadre.repaint();
                }
                
                listCardPlatillos.remove(card);
                carritoService.eliminarPlatillo(card.getPlatillo());
            }
        });
		
		card.getCantidadField().addActionListener(e -> {
			carritoService.modificarCantidadPlatillo(card.getPlatillo(), (int) card.getCantidad());
		});

		// Listener para perder foco
		card.getCantidadField().addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusLost(FocusEvent e) {
		    	carritoService.modificarCantidadPlatillo(card.getPlatillo(), (int) card.getCantidad());
		    }
		});
	}
	
	//Controla los cardIngrediente que contienen a los hijos de la receta
	private void anadirPlatilloACarrito(Platillo platillo, double cantidad) {
		CardCarritoPlatillo card = new CardCarritoPlatillo(platillo,cantidad);
		listCardPlatillos.add(card);
		vincularListener(card);
		view.agregarCardPlatillo(card);
		
	}
	
	public void crearCarrito() {
		limpiarPlatillo();
		for(ItemCarrito item : carrito) {
			anadirPlatilloACarrito(item.producto(), item.cantidad());
		}
		calcularAtributosDeCarrito();
	}
	
	private void limpiarPlatillo() {
		listCardPlatillos.clear();
		view.getPanelIngredientes().removeAll();
	}
	
	private void calcularAtributosDeCarrito() {
		float cantidad = 0;
		int cantidadProductos = 0;
		for(ItemCarrito item : carritoService.getOnlyReadCarrito()) {
			cantidadProductos += item.cantidad();
			cantidad += item.producto().getPrecioVenta() * item.cantidad();
		}
		view.setCantidadLabel("Total de articulos: " + cantidadProductos);
		view.setTotalLabel("Subtotal: $" + cantidad + " MXN");
	}
	
}
