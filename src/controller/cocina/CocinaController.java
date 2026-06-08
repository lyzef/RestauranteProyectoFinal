package controller.cocina;

import models.Venta;
import models.DetalleVenta;
import services.VentaService;
import services.VentaService.ResumenCocinaDTO;
import utilidades.views.CardOrden.AccionesComanda;
import views.Cocina.VistaCocinero;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import controller.LoginController;

public class CocinaController implements AccionesComanda {
    
    private VistaCocinero vista;
    private VentaService ventaService;
    private LoginController login;
    private Timer temporizadorAutoRefresco;

    public CocinaController(VistaCocinero vista, VentaService ventaService, LoginController login) {
        this.vista = vista;
        this.ventaService = ventaService;
        this.login = login;

        
        this.vista.setAccionesListener(this);
        this.vista.setBotonRefrescarListener(e -> actualizarVista());
        
        vista.setVisible(true);
        actualizarVista();
        addListeners();
        
        iniciarAutoRefresco();
    }
    
    private void iniciarAutoRefresco() {
        int veinteSegundos = 10000; // 20 segundos en milisegundos
        
        temporizadorAutoRefresco = new Timer(veinteSegundos, e -> {
            actualizarVista();
        });
        
        temporizadorAutoRefresco.start(); // Inicia el bucle de 20 segundos
    }
    
    private void detenerAutoRefresco() {
        if (temporizadorAutoRefresco != null && temporizadorAutoRefresco.isRunning()) {
            temporizadorAutoRefresco.stop();
        }
    }
    
    private void addListeners() {
    	vista.getBotonLogOut().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                detenerAutoRefresco(); 
				vista.dispose();
				login.abrirLogin();
		    }
		});
		
    	vista.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
                detenerAutoRefresco();
		    	vista.dispose();
		    	login.cerrarApp();
		    }
		});		
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
            for (DetalleVenta detalle : venta.getDetalles()) {
                if (detalle.isPendiente()) {
                    ventaService.iniciarPreparacionPlato(detalle.getId());
                }
            }
            actualizarVista();
            // Opcional: puedes reiniciar el Timer aquí con temporizadorAutoRefresco.restart() 
            // si no quieres que coincida una acción manual con la recarga automática.
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al iniciar preparación: " + e.getMessage());
        }
    }

    @Override
    public void onCompletar(Venta venta) {
        try {
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