package controller.admin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import controller.dialogs.CategoriaFormController;
import models.DetalleVenta;
import models.Venta;
import services.VentaService;
import tableFormat.VentaTableFormat;
import tableFormat.filtros.VentaTextFilterator;
import tableFormat.filtros.VentaTextFilterator.TipoFiltroVenta;
import views.Admin.VentaView;
import views.Dialog.CategoriaDialog;
import views.Dialog.TicketVentaDialog;

public class VentaAdminController {
    //Servicios
    VentaService ventaService;
    VentaView view;
    
    //Propio de la tabla y busqueda
    private EventList<Venta> listaVentas = new BasicEventList<Venta>();
    Timestamp fechaInicioStamp;
	Timestamp fechaFinalStamp;
    
    private AdvancedTableModel<Venta> tableModelVenta;
    VentaTextFilterator textFilteratorVenta;
    private FilterList<Venta> listaFiltradaVenta;
    private SortedList<Venta> listaOrdenada;  //Para ordenar
    private MatcherEditor<Venta> editorFiltroVenta;
    
    public VentaAdminController(VentaService ventaService, VentaView ventaView) {
        super();
        this.ventaService = ventaService;
        this.view = ventaView;
        
        crearTabla();
        addListeners();
    }
    
    private void addListeners() {
    	view.getListaFiltros().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		        	String filtroSeleccionado = view.getFiltroSeleccionado();
		    		
	    			TipoFiltroVenta tipoFiltro = TipoFiltroVenta.fromString(filtroSeleccionado);
	    			textFilteratorVenta.setFiltroActivo(tipoFiltro);
		        }
		    }
		});
    	view.getBtnCancel().addMouseListener(new MouseAdapter() {
    		@Override
    		public void mousePressed(MouseEvent e) {
    			int row = view.getSelectedRow();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un elemento");
	                return;
	            }
	            int confirm = JOptionPane.showConfirmDialog(view, "¿Estás seguro de cancelar esta venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
	            if (confirm == JOptionPane.YES_OPTION) {
	            	cancelarVenta(tableModelVenta.getElementAt(row));
	            }
				
    		}
		});
    	
    	view.getBtnBuscar().addMouseListener(new MouseAdapter() {
    		@Override
    		public void mousePressed(MouseEvent e) {
    	    	buscarPorFecha();
    		}
		});
    	
    	view.getBtnSee().addMouseListener(new MouseAdapter() {
    		@Override
    		public void mousePressed(MouseEvent e) {
    			int row = view.getSelectedRow();
	            if(row == -1) {
	                JOptionPane.showMessageDialog(view, "Selecciona un elemento");
	                return;
	            }
	            abrirDetallesVenta(tableModelVenta.getElementAt(row));
	            
    		}
		});
    }
    
    private void cancelarVenta(Venta venta) {
    	try {
			ventaService.cancelarVenta(venta.getId());
			venta.setEstado("CANCELADO");
			
			//Remplazar sin traer las ventas de db
			for (int i = 0; i < listaVentas.size(); i++) {
                if (Objects.equals(listaVentas.get(i).getId(), venta.getId())) {
                	listaVentas.set(i, venta);
                    break;
                }
            }
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "Venta no pudo ser cancelada ... " + e.getMessage());
		}
    }
    
    private void buscarPorFecha() {
    	Date fechaInicio = view.getFechaInicio();
    	Date fechaFinal = view.getFechaFin();
    	
    	if(fechaInicio == null || fechaFinal == null) {
    		return;
    	}
    	
    	fechaInicioStamp = convertirATimestamp(fechaInicio, false);
    	fechaFinalStamp = convertirATimestamp(fechaFinal, true);
    	
    	if(fechaFinalStamp.compareTo(fechaInicioStamp) < 0) {
    		JOptionPane.showMessageDialog(view, "Rango de busqueda invalido");
    		return;
    	}
    	
    	List<Venta> listaVentaPorLapsoDeTiempo = new BasicEventList<Venta>();
    	try {
			listaVentaPorLapsoDeTiempo.addAll(ventaService.getVentasByFecha(fechaInicioStamp, fechaFinalStamp));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "Problema al buscar las ventas ... " + e.getMessage());
			e.printStackTrace();
		}
    	
    	if(listaVentaPorLapsoDeTiempo.isEmpty()) {
    		JOptionPane.showMessageDialog(view, "Sin ventas registradas");
    		return;
    	}
    	
    	listaVentas.clear();
    	listaVentas.addAll(listaVentaPorLapsoDeTiempo);
    	
    	SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaInicioConvertida = formateador.format(fechaInicioStamp);
        String fechaFinalConvertida = formateador.format(fechaFinalStamp);
        
    	view.setTextoTituloTabla("Ventas por lapso de " + fechaInicioConvertida +" -> " + fechaFinalConvertida);
    	System.out.println(fechaInicio);
    	System.out.println(fechaFinal);
    	
    	
    }
    
    private void abrirDetallesVenta(Venta venta) {
    	try {
    		Venta ventaConDetalles;
			ventaConDetalles = ventaService.getVentaConDetalles(venta.getId());
			
			if(ventaConDetalles == null || ventaConDetalles.getDetalles().isEmpty()) {
	    		JOptionPane.showMessageDialog(view,"No se encontro los detalles de la venta");
	    		return;
	    	}
			
			DetalleVenta detalleVenta = ventaConDetalles.getDetalles().getFirst();
			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        String fechaInicioConvertida = detalleVenta.getFechaHoraInicio() != null ? formateador.format(detalleVenta.getFechaHoraInicio()) : "Sin iniciar";
	        String fechaFinalConvertida = detalleVenta.getFechaHoraInicio() != null ? formateador.format(detalleVenta.getFechaHoraFin()) : "Sin terminar";
			
			TicketVentaDialog ticketConDetalles = new TicketVentaDialog(null);
			ticketConDetalles.setCajero(ventaConDetalles.getNombreUsuario());
			ticketConDetalles.setDatosTicketCompleto(
					ventaConDetalles.getId(),
					detalleVenta.getEstadoCocina().toString(),
					detalleVenta.getUrgencia().toString(), 
					ventaConDetalles.getFechaHoraFormateada(),
					fechaInicioConvertida, 
					fechaFinalConvertida,
					ventaConDetalles.isCancelado() ? false : true);			
			for(DetalleVenta detalle : ventaConDetalles.getDetalles()) {
				ticketConDetalles.agregarItemTicket(detalle.getComponenteId(),detalle.getCantidad(), detalle.getComponenteNombre(), 
						"$"+detalle.getPrecioUnitarioAplicado(), "$"+detalle.getSubtotal());
			}
			ticketConDetalles.setMetodoPago(ventaConDetalles.getMetodoPago().toString());
			ticketConDetalles.setTotal("$"+ventaConDetalles.getTotalVenta());
			ticketConDetalles.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view,"No se logro recuperar los detalles de la venta ... " + e.getMessage());
			e.printStackTrace();
		}
    	
    }
    
    public void cargarDatosDelDia() {
    	try {
    		listaVentas.clear();
			listaVentas.addAll(ventaService.getVentasDelDia());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "Problema al cargar ventas del dia " + e.getMessage());
			e.printStackTrace();
		}
    }
    
    private void crearTabla() {
        textFilteratorVenta = new VentaTextFilterator();
        
        editorFiltroVenta = new TextComponentMatcherEditor<>(
            view.getTextoBuscador(),
            textFilteratorVenta
        );
        
        cargarDatosDelDia();
        
        listaFiltradaVenta = new FilterList<>(listaVentas, editorFiltroVenta);
        
        listaOrdenada = new SortedList<>(listaFiltradaVenta, null);
        listaOrdenada.setComparator((venta1, venta2) -> {
            return Integer.compare(venta2.getId(), venta1.getId());
        });
        
        tableModelVenta = GlazedListsSwing.eventTableModel(
            listaOrdenada, 
            new VentaTableFormat()
        );    
        
        view.setFiltrosBusqueda(TipoFiltroVenta.getTodasLasColumnas());
        view.setTableModel(tableModelVenta);
        
        TableComparatorChooser.install(
            view.getTabla(),
            listaOrdenada,
            TableComparatorChooser.SINGLE_COLUMN
        );
    }
    
    public Timestamp convertirATimestamp(Date fecha, boolean esFechaFin) {
        if (fecha == null) return null;
        
        if (esFechaFin) {
            // Para fecha fin 23:59:59
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            return new Timestamp(cal.getTimeInMillis());
        } else {
            // Para fecha inicio, inicio del dia
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            return new Timestamp(cal.getTimeInMillis());
        }
    }
}