package controller.autoVenta;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import controller.LoginController;
import repository.LoginRepository;
import services.CarritoService;
import services.CategoriaService;
import services.MenuCatalogoService;
import services.VentaProductoService;
import services.VentaService;
import utilidades.SessionUtilities;
import views.Login;
import views.AutoVenta.HubVentaFrame;

public class HubVentaController {
	HubVentaFrame view;
	
	
	//Controladores
	MenuVentaController menuVentaController;
	LoginController loginController;
	CarritoController carritoController;
	PagoController pagoController;
	
	MenuCatalogoService menuCatalogoService;
	CarritoService carritoService;
	VentaProductoService ventaProductoService;
	CategoriaService categoriaService;
	
	public HubVentaController(HubVentaFrame view, MenuCatalogoService menuCatalogoService,
			CarritoService carritoService, VentaProductoService ventaProductoService, CategoriaService categoriaService, LoginController loginController) {
		super();
		this.view = view;
		this.menuCatalogoService = menuCatalogoService;
		this.carritoService = carritoService;
		this.ventaProductoService = ventaProductoService;
		this.categoriaService = categoriaService;
		this.loginController = loginController;
		addListeners();
		
		showMenu();
		
		view.setVisible(true);
	}



	private void addListeners() {
		view.getBotonMenu().addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showMenu();
		    }
		});
		
		view.getBarraNavegacion().addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.abrirBarra();
		    }
		});
		
		view.getBotonConfiguracion().addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
		    }
		});
		
		view.getBotonLogOut().addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.dispose();
				loginController.abrirLogin();
		    }
		});
		
		view.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	if(SessionUtilities.isLoggedIn()) {
			    	new LoginRepository().setSesionActiva(SessionUtilities.getCurrentUser(), false);
		    	}
		    	view.dispose();
		    	loginController.cerrarApp();
		    }
		});		
	}
	
	/*
	 * Recarga de datos asi como creacion de controlador si no existe
	 */
	public void showMenuReset() {
		if(menuVentaController == null) {
			menuVentaController = new  MenuVentaController(view.getMenuPanel(),menuCatalogoService,carritoService,ventaProductoService,categoriaService,this);
		}
		menuVentaController.cargarMenu();
		view.showView(view.MENU);
	}
	
	public void showMenu() {
		if(menuVentaController == null) {
			menuVentaController = new  MenuVentaController(view.getMenuPanel(),menuCatalogoService,carritoService,ventaProductoService,categoriaService,this);
		}
		view.showView(view.MENU);
	}
	
	public void showCarrito() {
		if(carritoController == null) {
			carritoController = new  CarritoController(view.getCarritoPanel(), carritoService,this,ventaProductoService);
		}
		carritoController.crearCarrito();
		view.showView(view.CARRITO);
	}
	
	public void showPagoView() {
		if(pagoController == null) {
			pagoController = new  PagoController(view.getPagoPanel(), carritoService,ventaProductoService,this);
		}
		pagoController.generarPreTicketDePago();
		view.showView(view.PAGO);
	}
	
	
}
