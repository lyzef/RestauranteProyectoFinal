package controller.autoVenta;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import services.CarritoService;
import services.CategoriaService;
import services.MenuCatalogoService;
import services.VentasService;
import views.AutoVenta.HubVentaFrame;

public class HubVentaController {
	HubVentaFrame view;
	
	
	//Controladores
	MenuVentaController menuVentaController;
	MenuCatalogoService menuCatalogoService;
	CarritoService carritoService;
	VentasService ventasService;
	CategoriaService categoriaService;
	
	public HubVentaController(HubVentaFrame view, MenuCatalogoService menuCatalogoService,
			CarritoService carritoService, VentasService ventasService, CategoriaService categoriaService) {
		super();
		this.view = view;
		this.menuCatalogoService = menuCatalogoService;
		this.carritoService = carritoService;
		this.ventasService = ventasService;
		this.categoriaService = categoriaService;
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
				
		    }
		});
	}
	
	private void showMenu() {
		if(menuVentaController == null) {
			menuVentaController = new  MenuVentaController(view.getMenuPanel(),menuCatalogoService,carritoService,ventasService,categoriaService);
		}
		view.showView(view.MENU);
	}
	
}
