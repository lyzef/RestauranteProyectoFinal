package controller.autoVenta;

import java.awt.Menu;
import java.util.Map;

import ca.odell.glazedlists.EventList;
import models.Categoria;
import models.Platillo;
import services.CarritoService;
import services.CategoriaService;
import services.ComponenteService;
import services.InventarioService;
import services.MenuCatalogoService;
import services.PlatilloService;
import services.VentasService;
import views.AutoVenta.MenuVentaView;

public class MenuVentaController {
	MenuVentaView view;
	
	//Servicios
	MenuCatalogoService menuCatalogoService;
	CategoriaService categoriaService;
	CarritoService carritoService;
	VentasService ventasService;
	public MenuVentaController(MenuVentaView view, MenuCatalogoService menuCatalogoService,
			CarritoService carritoService, VentasService ventasService, CategoriaService categoriaService) {
		super();
		this.view = view;
		this.menuCatalogoService = menuCatalogoService;
		this.carritoService = carritoService;
		this.ventasService = ventasService;
		this.categoriaService = categoriaService;
		
		cargarMenu();
	}
	
	private void cargarMenu() {
		Map<String, EventList<Platillo>> menuPorCategoriasMap = menuCatalogoService.getPlatillosAgrupadosPorNombreCategoria();
		
		
		for(String categoria : menuPorCategoriasMap.keySet()) {
			EventList<Platillo> lista = menuPorCategoriasMap.get(categoria);
			if(lista == null || lista.size() == 0) {
				System.out.println("NO entro " +categoria);
				continue;
			}
			
			view.addSeccion(categoria, ".", view.crearGridCategoria(lista));
		}
		
		
		
		
	}
	
}
