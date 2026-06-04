package services;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import models.MovimientoInventario;
import models.MovimientoInventario.tipoMovimiento;
import services.CarritoService.ItemCarrito;

public class VentasService {
	InventarioService inventarioService;
	CarritoService carritoService;
	MenuCatalogoService menuCatalogoService;
	LoadService loadService;
	ComponenteService componenteService;
	;
	
	// A subir en batch (POR LOTES)
	List<MovimientoInventario> movimientosSalida = new ArrayList<>();
	
	public VentasService(InventarioService inventarioService, CarritoService carritoService,
			LoadService loadService,ComponenteService componenteService) {
		super();
		this.inventarioService = inventarioService;
		this.carritoService = carritoService;
		this.loadService = loadService;
		this.componenteService = componenteService;
	}
	
	private void comprobarDisponibilidadSoloReceta(int id) throws Exception {
		if(!componenteService.getComponenteByIdFromDB(id).isDisponibilidadManual()) {
			throw new Exception("pendiente");
		}
	}
	
	private void descontarInventarioPorCadaDetalleVenta() {
		
		//Modificar por detalle de venta
		for(ItemCarrito item : carritoService.getOnlyReadCarrito()) {
			movimientosSalida.addAll(inventarioService.crearMovimientosParaRecetaEHijos(item.producto().getComponenteId(), item.cantidad(), tipoMovimiento.VENTA,
					"Venta: " + item.producto().getComponenteNombre() + " x " + item.cantidad()));
			JOptionPane.showMessageDialog(null, item.producto().getComponenteNombre() + 
					" , cantidad = " + item.cantidad());
		}
	}
	
	public void realizarVenta() throws Exception {
		//Limpiar
		movimientosSalida.clear();
		
		try {
			for(ItemCarrito item : carritoService.getOnlyReadCarrito()) {
				comprobarDisponibilidadSoloReceta(item.producto().getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			limpiarYRenovarParaNuevaVenta();
			throw new Exception("Articulo sin existencia " + e.getMessage());
			
		}
		
		
		
		//Crear venta
		
		//Crear detalle venta
		
		//Iterar por cada detalle venta y crear movimientos
		descontarInventarioPorCadaDetalleVenta();
		
		//Subir movimiento, venta y detalle venta
		if(movimientosSalida.isEmpty()) {
			limpiarYRenovarParaNuevaVenta();
			throw new Exception("Platillo no tiene hijos validos");
		}
		
		try {
			inventarioService.subirConjuntoMovimientos(movimientosSalida);
		} catch (Exception e) {
			System.err.println(e);
			limpiarYRenovarParaNuevaVenta();
			throw new Exception("Problema al subir los datos " + e.getMessage());
		}
		
		//Limpiar carrito, traer datos de db, ir a menu renovado
		limpiarYRenovarParaNuevaVenta();
	}
	
	private void limpiarYRenovarParaNuevaVenta() {
		carritoService.limpiarCarrito();
		loadService.cargarDatosParaVenta();
	}
}
