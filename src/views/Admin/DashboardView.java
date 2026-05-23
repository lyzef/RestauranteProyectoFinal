package views.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import tablemodels.LiveTransaccionTableModel;
import tablemodels.UserTableFormat;
import utilidades.AppFont;
import utilidades.Paleta_Colores;
import utilidades.views.PanelRedondeadoConMargen;

public class DashboardView extends JPanel{
	//Modulo de total venta
	JLabel VentasTotales;
	JLabel AumentoEnVenta;
	
	//Modulo de total ordenes hoy
	JLabel OrdenesTotales;
	JLabel AumentoEnOrdenes;
	
	//Modulo de platillo TOP
	JLabel platilloMasVendido;
	JLabel UnidadesDePlatilloVendidas;
	
	//Data set de tabla
	DefaultCategoryDataset dataset;
	
	//Modulo de transacciones
	JTable tablaTransacciones;
	
	public DashboardView() {
		//Ajustes
		this.setBackground(Paleta_Colores.FONDO.getColor());
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 0.15;
		
		// Tres modulos superiores
		gbc.gridx = 0;
		this.add(moduloTotalVenta(),gbc);
		gbc.gridx = 1;
		this.add(moduloOrdenesHoy(),gbc);
		gbc.gridx = 2;
		this.add(moduloTopVentas(),gbc);
		
		// 2 modulos centrales
		gbc.weighty = 1.0;
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		this.add(moduloGraficaVentas(),gbc);
		
		gbc.gridx = 2;
		gbc.gridwidth = 1;
		this.add(moduloTotalVenta(),gbc);
		
		// 1 Modulo de tabla
		gbc.weighty = 0.75;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		this.add(crearModuloTransacciones(), gbc);
		
		
		
	}
	
	private JPanel moduloTotalVenta() {
		JPanel panelTotalVentas = new PanelRedondeadoConMargen();
		panelTotalVentas.setLayout(new GridBagLayout());
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setOpaque(false);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		//JPanel principal
		gbc.gridx = 0;          // columna 0
        gbc.weightx = 0.75;     // 75% del espacio horizontal
        gbc.fill = GridBagConstraints.BOTH;
     
        panelTotalVentas.add(panelPrincipal,gbc);
        
        //JPanel principal
  		gbc.gridx = 1;          // columna 0
        gbc.weightx = 0.25;     // 25% del espacio horizontal
        gbc.fill = GridBagConstraints.BOTH;
        panelTotalVentas.add(new JLabel("Imagen"),gbc);

        
		JLabel titulo = new JLabel("Ventas totales", JLabel.LEFT);
		titulo.setFont(AppFont.normal());
		titulo.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor());
		panelPrincipal.add(titulo);

		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));
		
		VentasTotales = new JLabel("Sin datos");
		VentasTotales.setFont(AppFont.normal());
		VentasTotales.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(VentasTotales);
		
		AumentoEnVenta = new JLabel("Aumento en 15% desde ayer");
		AumentoEnVenta.setFont(AppFont.small());
		AumentoEnVenta.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(AumentoEnVenta);
		
		return panelTotalVentas;
	}
	
	private JPanel moduloOrdenesHoy() {
		JPanel panelTotalVentas = new PanelRedondeadoConMargen();
		panelTotalVentas.setLayout(new GridBagLayout());
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setOpaque(false);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		//JPanel principal
		gbc.gridx = 0;          // columna 0
        gbc.weightx = 0.75;     // 75% del espacio horizontal
        gbc.fill = GridBagConstraints.BOTH;
     
        panelTotalVentas.add(panelPrincipal,gbc);
        
        //JPanel principal
  		gbc.gridx = 1;          // columna 0
        gbc.weightx = 0.25;     // 25% del espacio horizontal
        gbc.fill = GridBagConstraints.BOTH;
        panelTotalVentas.add(new JLabel("Imagen"),gbc);

        
		JLabel titulo = new JLabel("Ordenes en el dia", JLabel.LEFT);
		titulo.setFont(AppFont.normal());
		titulo.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor());
		panelPrincipal.add(titulo);

		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));
		
		OrdenesTotales = new JLabel("Sin datos");
		OrdenesTotales.setFont(AppFont.normal());
		OrdenesTotales.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(OrdenesTotales);
		
		AumentoEnOrdenes = new JLabel("25 % menos que el promedio");
		AumentoEnOrdenes.setFont(AppFont.small());
		AumentoEnOrdenes.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(AumentoEnOrdenes);
	
		return panelTotalVentas;
	}
	
	private JPanel moduloTopVentas() {
		JPanel panelTotalVentas = new PanelRedondeadoConMargen();
		panelTotalVentas.setLayout(new GridBagLayout());
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setOpaque(false);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		//JPanel principal
		gbc.gridx = 0;          // columna 0
        gbc.weightx = 0.75;     // 75% del espacio horizontal
        gbc.fill = GridBagConstraints.BOTH;
     
        panelTotalVentas.add(panelPrincipal,gbc);
        
        //JPanel principal
  		gbc.gridx = 1;          // columna 0
        gbc.weightx = 0.25;     // 25% del espacio horizontal
        gbc.fill = GridBagConstraints.BOTH;
        panelTotalVentas.add(new JLabel("Imagen"),gbc);

        
		JLabel titulo = new JLabel("Platillo top", JLabel.LEFT);
		titulo.setFont(AppFont.normal());
		titulo.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor());
		panelPrincipal.add(titulo);

		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));
		
		platilloMasVendido = new JLabel("Sin datos");
		platilloMasVendido.setFont(AppFont.normal());
		platilloMasVendido.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(platilloMasVendido);
		
		JLabel UnidadesDePlatilloVendidas = new JLabel("67 unidaes vendidas hoy");
		UnidadesDePlatilloVendidas.setFont(AppFont.small());
		UnidadesDePlatilloVendidas.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(UnidadesDePlatilloVendidas);
		
		return panelTotalVentas;
	}
	
	private JPanel moduloGraficaVentas() {
		dataset = new DefaultCategoryDataset();

	    // Estructura: dataset.setValue(valor, "Leyenda/Serie", "Eje X/Categoría");
	    dataset.setValue(0, "Ventas", "Lunes");
	    dataset.setValue(0, "Ventas", "Martes");
	    dataset.setValue(0, "Ventas", "Miercoles");
	    dataset.setValue(0, "Ventas", "Jueves");
	    dataset.setValue(0, "Ventas", "Viernes");
	    dataset.setValue(0, "Ventas", "Sabado");

	    // 2. Crear la gráfica de columnas (Bar Chart)
	    JFreeChart chart = ChartFactory.createBarChart(
	            "Tendencia de ventas",                  // Título de la gráfica
	            "Días",                                 // Etiqueta del Eje X (Cambiado a Días ya que usas Lunes-Sábado)
	            "Cantidad (MXN)",                       // Etiqueta del Eje Y
	            dataset,                                // Datos
	            PlotOrientation.VERTICAL,               // Orientación (Vertical = Columnas)
	            true,                                   // ¿Incluir leyenda?
	            true,                                   // ¿Incluir tooltips?
	            false                                   // ¿Incluir URLs?
	    );
	    Color colorTexto = Paleta_Colores.TEXTO_PRINCIPAL.getColor();

	    chart.setBackgroundPaint(new Color(0,0,0,0)); 
	    CategoryPlot plot = chart.getCategoryPlot();
	    plot.setBackgroundPaint(new Color(0,0,0,0));
	    
	    //Lineas del chart
	    plot.setDomainGridlinePaint(new Color(0,0,0,0));
	    plot.setRangeGridlinePaint(new Color(255, 255, 255, 30));
	    
	    //Texto principal
	    chart.getTitle().setPaint(colorTexto);

	   //Leyenda inferior
	    if (chart.getLegend() != null) {
	        chart.getLegend().setItemPaint(colorTexto);
	        chart.getLegend().setBackgroundPaint(new Color(0,0,0,0));
	    }
	    
	    //Categorias 
	    CategoryAxis domainAxis = plot.getDomainAxis();
	    domainAxis.setLabelPaint(colorTexto);   
	    domainAxis.setTickLabelPaint(colorTexto);  
	    
	    //Valores numericos
	    ValueAxis rangeAxis = plot.getRangeAxis();
	    rangeAxis.setLabelPaint(colorTexto);       
	    rangeAxis.setTickLabelPaint(colorTexto);   

	    ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setOpaque(false);

	    JPanel panelEstadisticas = new PanelRedondeadoConMargen();
	    panelEstadisticas.setLayout(new BorderLayout());
	    panelEstadisticas.add(chartPanel, BorderLayout.CENTER);
	    

	    return panelEstadisticas;
	}
	
	public void actualizarGrafica(double[] nuevosValores,String nuevosDias[]) {
	    //Limpiar por completo los datos anteriores de la gráfica
	    dataset.clear();

	    //Insertar los nuevos datos recorriendo los arreglos
	    for (int i = 0; i < nuevosValores.length; i++) {
	        dataset.setValue(nuevosValores[i], "Ventas", nuevosDias[i]);
	    }
	    
	}
	
	public JPanel crearModuloTransacciones() {
		tablaTransacciones = new JTable();
		tablaTransacciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
		JPanel panelTransacciones = new PanelRedondeadoConMargen();
		panelTransacciones.add(tablaTransacciones);
		
		
        return panelTransacciones;
	}
	
	public void setTableModel(UserTableFormat model) {
		tablaTransacciones.setModel(model);
    }
	
}
