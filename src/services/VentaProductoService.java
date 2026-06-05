package services;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import config.DatabaseConnection;
import models.DetalleVenta;
import models.DetalleVenta.EstadoCocina;
import models.DetalleVenta.Urgencia;
import models.MovimientoInventario;
import models.MovimientoInventario.tipoMovimiento;
import models.Venta;
import models.Venta.tipoMetodoPago;
import models.Venta.tipoPedido;
import services.CarritoService.ItemCarrito;
import utilidades.Session;

public class VentaProductoService {
    InventarioService inventarioService;
    CarritoService carritoService;
    MenuCatalogoService menuCatalogoService;
    LoadService loadService;
    ComponenteService componenteService;
    VentaService ventaService;

    public VentaProductoService(InventarioService inventarioService, CarritoService carritoService,
            LoadService loadService, ComponenteService componenteService, VentaService ventaService) {
        this.inventarioService = inventarioService;
        this.carritoService = carritoService;
        this.loadService = loadService;
        this.componenteService = componenteService;
        this.ventaService = ventaService;
    }
    
    private List<DetalleVenta> crearDetallesVentaPorProducto() {
        List<DetalleVenta> detalles = new ArrayList<>();
        for(ItemCarrito item : carritoService.getOnlyReadCarrito()) {
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setComponenteId(item.producto().getComponenteId());
            detalleVenta.setCantidad(item.cantidad());
            detalleVenta.setPrecioUnitarioAplicado(item.producto().getPrecioVenta());
            detalleVenta.calcularSubtotal();
            detalleVenta.setEstadoCocina(EstadoCocina.PENDIENTE);
            detalleVenta.setUrgencia(Urgencia.NORMAL);
            
            detalles.add(detalleVenta);
        }
        return detalles;
    }
    
    private List<MovimientoInventario> generarMovimientoSalidaPorCadaProducto() {
        List<MovimientoInventario> movimientos = new ArrayList<>();
        for(ItemCarrito item : carritoService.getOnlyReadCarrito()) {
            movimientos.addAll(inventarioService.crearMovimientosParaRecetaEHijos(
                    item.producto().getComponenteId(), 
                    item.cantidad(), 
                    tipoMovimiento.VENTA,
                    "Venta: " + item.producto().getComponenteNombre() + " x " + item.cantidad()
            ));
        }
        return movimientos;
    }
    
    public void realizarVenta(tipoMetodoPago metodoPago) throws Exception {
        List<MovimientoInventario> movimientosSalida;
        List<DetalleVenta> detallesVenta;
        Connection conexion = null;
        
        // Crear Venta
        Venta venta = new Venta();
        venta.setUsuarioID(Session.getCurrentUser().getId());
        venta.setTotalVenta(carritoService.costoTotalDelCarrito());
        venta.setMetodoPago(metodoPago);
        venta.setTipoPedidoVenta(tipoPedido.COMER_AQUI);
        venta.setEstado("PAGADO");
        
        // Generamos listas
        detallesVenta = crearDetallesVentaPorProducto();
        movimientosSalida = generarMovimientoSalidaPorCadaProducto();
        
        if(movimientosSalida.isEmpty()) {
            limpiarYRenovarParaNuevaVenta();
            throw new Exception("Platillo no tiene hijos válidos para descontar inventario.");
        }
        
        try {
            conexion = DatabaseConnection.getConnection();
            conexion.setAutoCommit(false); // Inicia transacción

            //registrarVentaCompleta tiene que asignar el ID de la venta a los detalles internamente
            ventaService.registrarVentaCompleta(venta, detallesVenta, conexion);
            inventarioService.subirConjuntoMovimientos(movimientosSalida, conexion);
            
            conexion.commit(); 

        } catch (Exception e) {
            System.err.println(e);
            if (conexion != null) {
                try {
                    conexion.rollback(); // Deshacer cambios en DB
                } catch (Exception ex) {
                    System.err.println("Error fatal al hacer rollback: " + ex);
                }
            }
            limpiarYRenovarParaNuevaVenta();
            throw new Exception("Problema al subir los datos: " + e.getMessage());

        } finally {
            if (conexion != null) {
                try {
                    conexion.setAutoCommit(true); 
                    conexion.close();
                } catch (Exception ex) {
                    System.err.println("Error al cerrar conexión: " + ex);
                }
            }
        }
        
        limpiarYRenovarParaNuevaVenta();
    }
    
    private void limpiarYRenovarParaNuevaVenta() {
        carritoService.limpiarCarrito();
        loadService.cargarDatosParaVenta();
    }
}