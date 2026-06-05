package controller.autoVenta;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import ca.odell.glazedlists.EventList;
import controller.dialogs.CategoriaFormController;
import models.Venta.tipoMetodoPago;
import services.CarritoService;
import services.CarritoService.ItemCarrito;
import services.VentaProductoService;
import services.VentaService;
import utilidades.Session;
import utilidades.views.ItemCarritoCard;
import views.AutoVenta.PagoView;
import views.Dialog.CategoriaDialog;

public class PagoController {
	PagoView view;
	CarritoService carritoService;
	VentaProductoService ventaProductoService;
	HubVentaController hubVentaController;
	
	EventList<ItemCarrito> carrito;
	
	public PagoController(PagoView pagoView, CarritoService carritoService, VentaProductoService ventaProductoService,
		 HubVentaController hubVentaController) {
		super();
		this.view = pagoView;
		this.carritoService = carritoService;
		this.ventaProductoService = ventaProductoService;
		this.hubVentaController = hubVentaController;
		
		addListeners();
		generarTicketDePago();
	}
	
	private void addListeners() {
		view.getBotonEfectivo().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				crearVenta(tipoMetodoPago.EFECTIVO);
			}
		});
		
		view.getBotonTarjeta().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				crearVenta(tipoMetodoPago.TARJETA);
			}
		});
		
		view.getBotonTransferencia().addMouseListener(new MouseAdapter() {
			@Override
		    public void mousePressed(MouseEvent e) {
				crearVenta(tipoMetodoPago.TRANSFERENCIA);
			}
		});
	}
	
	private void crearVenta(tipoMetodoPago metodoPago) {
        try {
            ventaProductoService.realizarVenta(metodoPago);
            JOptionPane.showMessageDialog(view, "VENTA EXITOSA");
            
        } catch (Exception error) {
            JOptionPane.showMessageDialog(view, error.getMessage());
            error.printStackTrace();
        }
        hubVentaController.showMenuReset();
	}
	
	public void generarTicketDePago() {
		view.limpiarTicket();
		for(ItemCarrito item : carritoService.getOnlyReadCarrito()) {
			view.agregarItemTicket(item.cantidad(), item.producto().getComponenteNombre(), Double.toString(item.producto().getPrecioVenta()));
		}
		view.setTotal("$"+carritoService.costoTotalDelCarrito());
		view.setCajero(Session.getCurrentUser().getNombre());
	}
	
	
}
