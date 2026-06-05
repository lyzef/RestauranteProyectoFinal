package services;

import javax.swing.JOptionPane;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import models.Platillo;
import services.CarritoService.ItemCarrito;

public class CarritoService {
	ComponenteService componenteService;
    private final EventList<ItemCarrito> carrito = new BasicEventList<>();
    
    public record ItemCarrito(Platillo producto, int cantidad) {}
    
    public CarritoService(ComponenteService componenteService) {
    	this.componenteService = componenteService;
    }
    
    /**
     * Agrega un platillo al carrito. 
     * Si ya existe, incrementa su cantidad en 1.
     * Si no existe, lo agrega por primera vez con cantidad 1.
     */
    public void agregarPlatillo(Platillo platillo) {
        carrito.getReadWriteLock().writeLock().lock();
        try {
            // Buscamos el item en el carrito
            ItemCarrito itemExistente = carrito.stream()
                    .filter(item -> item.producto().equals(platillo))
                    .findFirst()
                    .orElse(null);

            if (itemExistente != null) {
                // Nueva cantidad
                int indice = carrito.indexOf(itemExistente);
                int nuevaCantidad = itemExistente.cantidad() + 1;
                
                // Se remueve e inserta para actualizar interfaz con listeners
                carrito.remove(indice);
                carrito.add(indice, new ItemCarrito(platillo, nuevaCantidad));

            	System.out.println("Item " + platillo.getComponenteNombre() + " tiene "+ nuevaCantidad);
            } else {
                // Si no existe, se agrega nuevo con cantidad inicial de 1
            	System.out.println("Nuevo item anadido " + platillo.getComponenteNombre());
                carrito.add(new ItemCarrito(platillo, 1));
            }
        } finally {
            carrito.getReadWriteLock().writeLock().unlock();
        }
    }

    /**
     * Reduce en 1 la cantidad de un platillo en el carrito.
     * Si la cantidad llega a 0, elimina el platillo por completo del carrito.
     */
    public void eliminarOReducirPlatillo(Platillo platillo) {
        carrito.getReadWriteLock().writeLock().lock();
        try {
            //Encontramos elemento
            ItemCarrito itemExistente = carrito.stream()
                    .filter(item -> item.producto().equals(platillo))
                    .findFirst()
                    .orElse(null);

            // No existe en carrito
            if (itemExistente == null) return;

            int index = carrito.indexOf(itemExistente);

            if (itemExistente.cantidad() > 1) {
                // Se reduce si es posible
                int nuevaCantidad = itemExistente.cantidad() - 1;
                carrito.remove(index);
                carrito.add(index, new ItemCarrito(platillo, nuevaCantidad));
            } else {
                //Remover en caso que quede en 0
                carrito.remove(index);
            }
        } finally {
            carrito.getReadWriteLock().writeLock().unlock();
        }
    }
    
    /**
     * Elimina completamente un platillo del carrito sin importar su cantidad.
     * Si el platillo existe en el carrito, lo remueve por completo.
     * Si no existe, no hace nada.
     */
    public void eliminarPlatillo(Platillo platillo) {
        carrito.getReadWriteLock().writeLock().lock();
        try {
            // Buscamos el item en el carrito
            ItemCarrito itemExistente = carrito.stream()
                    .filter(item -> item.producto().equals(platillo))
                    .findFirst()
                    .orElse(null);
            
            // Si existe, lo removemos
            if (itemExistente != null) {
                int index = carrito.indexOf(itemExistente);
                carrito.remove(index);
                System.out.println("Platillo " + platillo.getDescripcion() + " eliminado completamente del carrito");
            }
        } finally {
            carrito.getReadWriteLock().writeLock().unlock();
        }
    }
    
    /**
     * Modifica la cantidad de un platillo existente en el carrito.
     * Si la nueva cantidad es 0 o negativa, elimina el platillo del carrito.
     * Si el platillo no existe y la cantidad es mayor a 0, lo agrega.
     * Si el platillo no existe y la cantidad es 0 o negativa, no hace nada.
     * 
     * @param platillo El platillo a modificar
     * @param nuevaCantidad La nueva cantidad deseada (debe ser >= 0)
     */
    public void modificarCantidadPlatillo(Platillo platillo, int nuevaCantidad) {
        carrito.getReadWriteLock().writeLock().lock();
        try {
            // Si la nueva cantidad es 0 o negativa, eliminar el platillo
            if (nuevaCantidad <= 0) {
                eliminarPlatillo(platillo);
                return;
            }
            
            // Buscar el item existente
            ItemCarrito itemExistente = carrito.stream()
                    .filter(item -> item.producto().equals(platillo))
                    .findFirst()
                    .orElse(null);
            
            if (itemExistente != null) {
                // Actualizar cantidad del platillo existente
                int index = carrito.indexOf(itemExistente);
                carrito.remove(index);
                carrito.add(index, new ItemCarrito(platillo, nuevaCantidad));
                System.out.println("Cantidad de " + platillo.getDescripcion() + " actualizada a: " + nuevaCantidad);
            } else {
                // Si no existe y la nueva cantidad es positiva, se agregar
                if (nuevaCantidad > 0) {
                    carrito.add(new ItemCarrito(platillo, nuevaCantidad));
                    System.out.println("Platillo " + platillo.getDescripcion() + " agregado con cantidad: " + nuevaCantidad);
                }
            }
        } finally {
            carrito.getReadWriteLock().writeLock().unlock();
        }
    }
    
    public boolean verificarDisponibilidadDelCarrito() throws Exception {
		int articulosNoValidos = 0;
	        
	    // Validación de disponibilidad
	    for(ItemCarrito item : carrito) {
	        if(componenteService.getComponenteByIdFromDB(item.producto().getComponenteId()).isDisponibilidadManual() == false) {
	            eliminarPlatillo(item.producto());
	            articulosNoValidos++;
	        }
	    }
	    
	    if(articulosNoValidos > 0) {
	        JOptionPane.showMessageDialog(null, "Artículos sin existencia encontrados en el carrito.");
	        return false;
	    }
	    
	    return true;
    }
    
    public double costoTotalDelCarrito() {
    	double subtotal = 0;
        for (ItemCarrito item : carrito) {
            subtotal += item.producto().getPrecioVenta() * item.cantidad();
        }
        return subtotal;
    }
    
    public void limpiarCarrito() {
    	if(carrito != null) {
    		carrito.clear();
    	}
    }

    public EventList<ItemCarrito> getOnlyReadCarrito() {
        return GlazedLists.readOnlyList(carrito);
    }
    
    public EventList<ItemCarrito> getCarrito() {
        return carrito;
    }
    
    public int getSize() {
    	return carrito.size();
    }
}