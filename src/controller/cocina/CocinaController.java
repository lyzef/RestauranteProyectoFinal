package controller.cocina;

import models.Venta;
import models.DetalleVenta;
import services.VentaService;
import services.VentaService.ResumenCocinaDTO;
import utilidades.views.CardOrden.AccionesComanda;
import views.Cocina.VistaCocinero;

import javax.swing.JOptionPane;
.
public class CocinaController implements AccionesComanda {
    
    private VistaCocinero vista;
    private VentaService ventaService;

    public CocinaController(VistaCocinero vista, VentaService ventaService) {
        this.vista = vista;
        this.ventaService = ventaService;

        // Escuchamos las acciones de la vista
        this.vista.setAccionesListener(this);
        this.vista.setBotonRefrescarListener(e -> actualizarVista());
        
        vista.setVisible(true);
        // Carga inicial
        actualizarVista();
    }

    private void actualizarVista() {
        try {
            ResumenCocinaDTO datos = ventaService.getDashboardCocina();
            vista.mostrarDatos(datos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar comandas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onIniciar(Venta venta) {
        try {
            // Iteramos sobre todos los platillos de este ticket y los pasamos a proceso
            for (DetalleVenta detalle : venta.getDetalles()) {
                if (detalle.isPendiente()) {
                    ventaService.iniciarPreparacionPlato(detalle.getId());
                }
            }
            actualizarVista();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al iniciar preparación: " + e.getMessage());
        }
    }

    @Override
    public void onCompletar(Venta venta) {
        try {
            // Iteramos sobre todos los platillos de este ticket y los marcamos completados
            for (DetalleVenta detalle : venta.getDetalles()) {
                if (detalle.isEnProceso()) {
                    ventaService.completarPreparacionPlato(detalle.getId());
                }
            }
            actualizarVista();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al completar pedido: " + e.getMessage());
        }
    }
}