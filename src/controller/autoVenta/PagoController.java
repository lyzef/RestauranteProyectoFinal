package controller.autoVenta;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import ca.odell.glazedlists.EventList;
import models.DetalleVenta;
import models.Venta;
import models.Venta.tipoMetodoPago;
import services.CarritoService;
import services.CarritoService.ItemCarrito;
import services.VentaProductoService;
import services.VentaService;
import utilidades.*;
import views.AutoVenta.PagoView;
import views.Dialog.TicketVentaDialog;
public class PagoController {
	PagoView view;
	CarritoService carritoService;
	VentaProductoService ventaProductoService;
	HubVentaController hubVentaController;
	VentaService ventaService = new VentaService();
	
	EventList<ItemCarrito> carrito;
	
	public PagoController(PagoView pagoView, CarritoService carritoService, VentaProductoService ventaProductoService,
		 HubVentaController hubVentaController) {
		super();
		this.view = pagoView;
		this.carritoService = carritoService;
		this.ventaProductoService = ventaProductoService;
		this.hubVentaController = hubVentaController;
		
		addListeners();
		generarPreTicketDePago();
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
		Venta ventaCreada = new Venta();
        try {
            ventaCreada = ventaProductoService.realizarVenta(metodoPago);
            JOptionPane.showMessageDialog(view, "VENTA EXITOSA");
            
        } catch (Exception error) {
            JOptionPane.showMessageDialog(view, error.getMessage());
            error.printStackTrace();
        }
        
        abrirDetallesVenta(ventaCreada);
        hubVentaController.showMenuReset();
	}
	
	public void generarPreTicketDePago() {
		view.limpiarTicket();
		for(ItemCarrito item : carritoService.getOnlyReadCarrito()) {
			view.agregarItemTicket(item.cantidad(), item.producto().getComponenteNombre(), Double.toString(item.producto().getPrecioVenta()));
		}
		view.setTotal("$"+carritoService.costoTotalDelCarrito());
		view.setCajero(SessionUtilities.getCurrentUser().getNombre());
	}
	
	private void abrirDetallesVenta(Venta venta) {
    	try {
    		Venta ventaConDetalles;
			ventaConDetalles = ventaService.getVentaConDetalles(venta.getId());
			
			if(ventaConDetalles == null || ventaConDetalles.getDetalles().isEmpty()) {
	    		JOptionPane.showMessageDialog(view,"Fallo en creacion de ticket, Hablar al personal ... ");
	    		return;
	    	}
			
			DetalleVenta detalleVenta = ventaConDetalles.getDetalles().getFirst();
			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			TicketVentaDialog ticketConDetalles = new TicketVentaDialog(null, venta.getId(), venta.getFechaHoraFormateada());
			ticketConDetalles.setCajero(ventaConDetalles.getNombreUsuario());
			
			for(DetalleVenta detalle : ventaConDetalles.getDetalles()) {
				ticketConDetalles.agregarItemTicket(detalle.getComponenteId(),detalle.getCantidad(), detalle.getComponenteNombre(), 
						"$"+detalle.getPrecioUnitarioAplicado(), "$"+detalle.getSubtotal());
			}
			ticketConDetalles.setMetodoPago(ventaConDetalles.getMetodoPago().toString());
			ticketConDetalles.setTotal("$"+ventaConDetalles.getTotalVenta());
			ticketConDetalles.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view,"Fallo en creacion de ticket, Hablar al personal ... " + e.getMessage());
			e.printStackTrace();
		}
    	
    }
	
}
