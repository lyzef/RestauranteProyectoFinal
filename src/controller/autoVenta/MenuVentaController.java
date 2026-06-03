package controller.autoVenta;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.itextpdf.text.pdf.AcroFields.Item;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;
import models.Categoria;
import models.Platillo;
import services.CarritoService;
import services.CarritoService.ItemCarrito;
import services.CategoriaService;
import services.ComponenteService;
import services.InventarioService;
import services.MenuCatalogoService;
import services.PlatilloService;
import services.VentasService;
import utilidades.views.CardIngrediente;
import utilidades.views.CardPlatillo;
import views.AutoVenta.MenuVentaView;

public class MenuVentaController {
	MenuVentaView view;
	
	//Servicios
	MenuCatalogoService menuCatalogoService;
	CategoriaService categoriaService;
	CarritoService carritoService;
	VentasService ventasService;
	
	HubVentaController hub;
	
	public MenuVentaController(MenuVentaView view, MenuCatalogoService menuCatalogoService,
			CarritoService carritoService, VentasService ventasService, CategoriaService categoriaService,HubVentaController hub) {
		super();
		this.view = view;
		this.menuCatalogoService = menuCatalogoService;
		this.carritoService = carritoService;
		this.ventasService = ventasService;
		this.categoriaService = categoriaService;
		this.hub = hub;
		
		cargarMenu();
		
		addListeners();
	}
	
	private void addListeners() {
		carritoService.getOnlyReadCarrito().addListEventListener(new ListEventListener<ItemCarrito>() {
			@Override
			public void listChanged(ListEvent<ItemCarrito> tipoEvento) {
	               calcularAtributosDeCarrito();
			}
			
		});
		
		view.getBtnPedir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("ACTIVO");
				hub.showCarrito();

			}
		});
		
		
	}
	
	private void vincularBotonAgregarACarrito(CardPlatillo card) {
		// Anade un elemento al carrito
		card.getBotonAgregar().addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseClicked(MouseEvent e) {
               carritoService.agregarPlatillo(card.getPlatillo());
            }
        });
	}
	
	private void calcularAtributosDeCarrito() {
		float cantidad = 0;
		for(ItemCarrito item : carritoService.getOnlyReadCarrito()) {
			cantidad += item.producto().getPrecioVenta() * item.cantidad();
		}
		
		view.setTotalTexto("$" + cantidad + " MXN");
		view.setCantidadTexto(carritoService.getSize() + " Articulos");
	}
	
	
	private void cargarMenu() {
		Map<String, EventList<Platillo>> menuPorCategoriasMap = menuCatalogoService.getPlatillosAgrupadosPorNombreCategoria();
		
		
		for(String categoria : menuPorCategoriasMap.keySet()) {
			EventList<Platillo> lista = menuPorCategoriasMap.get(categoria);
			if(lista == null || lista.size() == 0) {
				System.out.println("NO entro " +categoria);
				continue;
			}
			
			view.addSeccion(categoria, ".", crearGridCategoria(lista));
		}
		
	}
	
	public JPanel crearGridCategoria(EventList<Platillo> platillos) {
    	JPanel grid = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 4)) {
            @Override public Dimension getPreferredSize() {
                int total = 0;
                for (Component c : getComponents()) total += c.getPreferredSize().width + 14;
                return new Dimension(total + 14, view.CARD_ALTO);
            }
        };
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        for (int i = 0; i < platillos.size(); i++) {
            CardPlatillo card = new CardPlatillo(
                platillos.get(i)
            );
            card.setPreferredSize(new Dimension(view.CARD_ANCHO, view.CARD_ALTO));
            vincularBotonAgregarACarrito(card);
            grid.add(card);
        }
        return grid;
    }
	
}
