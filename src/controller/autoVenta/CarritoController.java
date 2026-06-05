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
import models.Platillo;
import services.CarritoService;
import services.CarritoService.ItemCarrito;
import services.VentaProductoService;
import utilidades.views.ItemCarritoCard;
import views.AutoVenta.CarritoView;

public class CarritoController {
    CarritoView view;
    CarritoService carritoService;
    VentaProductoService ventasService;
    HubVentaController hubVentaController;
    
    ArrayList<ItemCarritoCard> listCardPlatillos = new ArrayList<ItemCarritoCard>();
    EventList<ItemCarrito> carrito;
    
    public CarritoController(CarritoView view, CarritoService carritoService,
            HubVentaController hubVentaController, VentaProductoService ventasService) {
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
        // Listener para pasar a panel de pago
        view.getBtnConfirmar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(carrito.isEmpty()) {
            		JOptionPane.showMessageDialog(view, "Elige articulos");
            		hubVentaController.showMenu();
            		return;
            	}
            	
                try {
                	carritoService.verificarDisponibilidadDelCarrito();
                	hubVentaController.showPagoView();
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(view, error.getMessage());
                    hubVentaController.showMenuReset();
                    error.printStackTrace();
                }
                
                
            }
        });
        
        // Regresar a carrito
        view.getBtnCancelar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hubVentaController.showMenu();
			}
		});
        
        // Listener para botón "Limpiar Carrito"
        if (view.getIconoLimpiar() != null) {
            view.getIconoLimpiar().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(view, 
                        "¿Estás seguro de que deseas limpiar todo el carrito?", 
                        "Limpiar Carrito", 
                        JOptionPane.YES_NO_OPTION);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        limpiarCarritoCompleto();
                    }
                }
            });
        }
    }
    
    /**
     * Vincula los listeners a un item del carrito
     */
    private void vincularListener(ItemCarritoCard itemCard) {
        // Listener para botón "+"
        itemCard.setOnMasListener(e -> {
            Platillo platillo = obtenerPlatilloPorId(itemCard.getProductoId());
            if (platillo != null) {
                int nuevaCantidad = itemCard.getCantidad() + 1;
                itemCard.setCantidad(nuevaCantidad);
                carritoService.modificarCantidadPlatillo(platillo, nuevaCantidad);
                calcularAtributosDeCarrito();
            }
        });
        
        // Listener para botón "-"
        itemCard.setOnMenosListener(e -> {
            Platillo platillo = obtenerPlatilloPorId(itemCard.getProductoId());
            if (platillo != null) {
                int cantidadActual = itemCard.getCantidad();
                if (cantidadActual > 1) {
                    int nuevaCantidad = cantidadActual - 1;
                    itemCard.setCantidad(nuevaCantidad);
                    carritoService.modificarCantidadPlatillo(platillo, nuevaCantidad);
                    calcularAtributosDeCarrito();
                } else {
                    // Eliminar item si cantidad es 1
                    int confirm = JOptionPane.showConfirmDialog(view, 
                        "¿Eliminar " + itemCard.getNombre() + " del carrito?", 
                        "Eliminar producto", 
                        JOptionPane.YES_NO_OPTION);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        view.eliminarItem(itemCard);
                        listCardPlatillos.remove(itemCard);
                        carritoService.eliminarPlatillo(platillo);
                        calcularAtributosDeCarrito();
                    } else {
                        // Restaurar cantidad a 1
                        itemCard.setCantidad(1);
                    }
                }
            }
        });
    }
    
    /**
     * Agrega un platillo al carrito visualmente
     */
    private void anadirPlatilloACarrito(Platillo platillo, int cantidad) {
        // Crear la card del item
        ItemCarritoCard card = new ItemCarritoCard(
            platillo.getId(),
            platillo.getComponenteNombre(),
            "",  // Detalle vacío por ahora
            cantidad,
            platillo.getPrecioVenta()
        );
        
        listCardPlatillos.add(card);
        vincularListener(card);
        view.agregarItem(card);
    }
    
    /**
     * Obtiene un platillo por su ID desde el carrito
     */
    private Platillo obtenerPlatilloPorId(int productoId) {
        for (ItemCarrito item : carrito) {
            if (item.producto().getId() == productoId) {
                return item.producto();
            }
        }
        return null;
    }
    
    /**
     * Crea todo el carrito desde el servicio
     */
    public void crearCarrito() {
        limpiarCarrito();
        for (ItemCarrito item : carrito) {
            anadirPlatilloACarrito(item.producto(), item.cantidad());
        }
        calcularAtributosDeCarrito();
    }
    
    /**
     * Limpia el carrito visualmente
     */
    private void limpiarCarrito() {
        listCardPlatillos.clear();
        view.limpiarCarrito();
    }
    
    /**
     * Limpia el carrito visual y en servicio
     */
    private void limpiarCarritoCompleto() {
        carritoService.limpiarCarrito();
        limpiarCarrito();
        calcularAtributosDeCarrito();
    }
    
    /**
     * Calcula y actualiza los atributos del carrito
     */
    private void calcularAtributosDeCarrito() {
        int cantidadProductos = 0;
        double subtotal = 0;
        
        for (ItemCarrito item : carritoService.getOnlyReadCarrito()) {
            cantidadProductos += item.cantidad();
            subtotal += item.producto().getPrecioVenta() * item.cantidad();
        }
        
        // Actualizar vista
        //view.setCantidadLabel(cantidadProductos + " Productos");
        view.setSubtotal(subtotal);
    }
}