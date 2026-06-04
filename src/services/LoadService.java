package services;

import javax.swing.JOptionPane;

public class LoadService {
	ComponenteService componenteService;
	CategoriaService categoriaService;
	EstructuraRecetaService estructuraRecetaService;
	InventarioService inventarioService;
	PlatilloService platilloService;
	public LoadService(ComponenteService componenteService, CategoriaService categoriaService,
			EstructuraRecetaService estructuraRecetaService, InventarioService inventarioService,
			PlatilloService platilloService) {
		super();
		this.componenteService = componenteService;
		this.categoriaService = categoriaService;
		this.estructuraRecetaService = estructuraRecetaService;
		this.inventarioService = inventarioService;
		this.platilloService = platilloService;
	}
	
	
	public void cargarDatosParaVenta() {
		try {
			componenteService.cargarDatosDesdeBD();
			categoriaService.loadCategoriasFromDataBase();
			estructuraRecetaService.cargarDatosDesdeBD();
			platilloService.loadPlatillosFromDatabase();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
	}
	
}
