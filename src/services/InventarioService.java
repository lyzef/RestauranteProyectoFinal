package services;

import java.util.ArrayList;
import java.util.List;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import models.ComponenteIngredienteReceta;
import models.Estructura_receta;
import models.MovimientoInventario;
import models.MovimientoInventario.tipoMovimiento;
import repository.InventarioRepository;

public class InventarioService {
	private ComponenteService componenteService;
    private EstructuraRecetaService estructuraService;
    private InventarioRepository repo;
    
    private EventList<MovimientoInventario> listaMovimientos = new BasicEventList<MovimientoInventario>();
    
	public InventarioService(ComponenteService componenteService, EstructuraRecetaService estructuraService) {
		super();
		this.componenteService = componenteService;
		this.estructuraService = estructuraService;
		repo = new InventarioRepository();
		
		try {
			cargarMovimientos();
		} catch (Exception e) {
			System.out.println("Lista de movimiento no se cargo ..." + e.getMessage());
		}
	}
	
	
	public void guardarMovimientoInventario(MovimientoInventario mov) throws Exception {

		int i = repo.saveMovimientoInventario(mov);
		mov.setId(i);
		listaMovimientos.add(mov);
	
	}
	
    public List<MovimientoInventario> crearMovimientosParaRecetaEHijos(int idReceta, double cantidad, tipoMovimiento tipoMovimiento,String motivo) {
    	ComponenteIngredienteReceta receta = componenteService.getComponenteById(idReceta);
    	List<MovimientoInventario> listaMovimientoARegistrar = new ArrayList<MovimientoInventario>();
    	
    	//Si la receta es por lotes se descuenta al momento
    	if(receta.isInventariable()) {
    		//Crea el movimiento para hijos inventariable
    		MovimientoInventario movimiento = new MovimientoInventario(idReceta, receta.getNombre(), tipoMovimiento, 
    				cantidad, cantidad * receta.getCostoUnitario(), motivo);
    		listaMovimientoARegistrar.add(movimiento);
    		
    		return listaMovimientoARegistrar;
    	}
    	
    	//Si no es inventariable entonces obtenemos sus hijos y quitamos su stock
        List<Estructura_receta> hijos = estructuraService.getHijosByID(idReceta);
        
        for (Estructura_receta hijo : hijos) {
        	double cantidadNecesaria = cantidad * hijo.getCantidad();
            
            listaMovimientoARegistrar.addAll(
            		crearMovimientosParaRecetaEHijos(hijo.getChild_id(), cantidadNecesaria, tipoMovimiento, motivo)
            );
            
        }
        return listaMovimientoARegistrar;
    }
    
    
    /*
    private List<MovimientoInventario> descontarHijosDeInventario(MovimientoInventario mov) {
    	return descontarHijosDeInventario(mov.getComponente_id(),mov.getCantidad(),mov.getTipo_movimiento(),mov.getMotivo());
    }
    */
    
    public void guardarProduccion(int idReceta, double cantidad,String motivo) throws Exception {
    	ComponenteIngredienteReceta receta = componenteService.getComponenteById(idReceta);
    	MovimientoInventario movimiento = new MovimientoInventario(idReceta, receta.getNombre(), tipoMovimiento.ENTRADA, 
				cantidad, 0, "PRODUCCION :"+ motivo);
		
    	List<MovimientoInventario> movimientosSalida = new ArrayList<>();
    	movimientosSalida.add(movimiento);
        List<Estructura_receta> hijos = estructuraService.getHijosByID(idReceta);
        
        for (Estructura_receta hijo : hijos) {
            double cantidadNecesaria = cantidad * hijo.getCantidad();
            
            movimientosSalida.addAll(
            		crearMovimientosParaRecetaEHijos(
                            hijo.getChild_id(), 
                            cantidadNecesaria, 
                            tipoMovimiento.SALIDA, 
                            "DESCUENTO PRODUCCION: " + motivo
                    )
            );
        }
        
        repo.saveMovimientosDeInventario(movimientosSalida);
        listaMovimientos.addAll(movimientosSalida);
    	
    }
    
    public void subirConjuntoMovimientos(List<MovimientoInventario> movimientos) throws Exception {
    	repo.saveMovimientosDeInventario(movimientos);
    }
    
    public EventList<MovimientoInventario> getListaModificable() {
        return this.listaMovimientos;
    }
    
    public EventList<MovimientoInventario> getListaSoloLectura() {
        return GlazedLists.readOnlyList(this.listaMovimientos);
    }
    
    public void cargarMovimientos() throws Exception {
    	listaMovimientos.clear();
    	listaMovimientos.addAll(repo.getMovimientosInventario());
    }
}
