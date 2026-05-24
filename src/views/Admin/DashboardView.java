package views.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.JTable;

import ca.odell.glazedlists.swing.AdvancedTableModel;
import models.User;
import utilidades.Paleta_Colores;
import utilidades.views.PanelRedondeadoConMargen;
import utilidades.views.PanelPersonalizadoTabla;
import utilidades.views.ModuloParaEstadistica;

public class DashboardView extends JPanel {
    
    // Modulos superiores
    public ModuloParaEstadistica moduloVentas;
    public ModuloParaEstadistica moduloOrdenes;
    public ModuloParaEstadistica moduloTop;
    
    DefaultCategoryDataset dataset;
    
    JTable tablaTransacciones;
    
    public DashboardView() {
        // Ajustes
        this.setBackground(Paleta_Colores.FONDO.getColor());
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.1;
        
        // Modulks superiores
        moduloVentas = new ModuloParaEstadistica(
            "Ventas hoy", "Sin datos", "Aumento en 15% desde ayer", 
            Paleta_Colores.ACENTO_PRIMARIO.getColor(), "/assets/image/dineroIcon.png"
        );
        
        moduloOrdenes = new ModuloParaEstadistica(
            "Ordenes en el dia", "Sin datos", "25 % menos que el promedio", 
            Paleta_Colores.ATENCION.getColor(), "/assets/image/receipt.png"
        );
        
        moduloTop = new ModuloParaEstadistica(
            "Platillo top", "Sin datos", "67 unidaes vendidas hoy", 
            Paleta_Colores.EXITO.getColor(), "/assets/image/star.png"
        );

        gbc.gridx = 0;
        gbc.weightx = 0.33; 
        this.add(moduloVentas, gbc);
        
        gbc.gridx = 1;
        this.add(moduloOrdenes, gbc);
        
        gbc.gridx = 2;
        this.add(moduloTop, gbc);
        
        // Grafica y modulo central
        gbc.weighty = 1.0;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        this.add(moduloGraficaVentas(), gbc);
        
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        this.add(new PanelRedondeadoConMargen(), gbc);
        
        // Módulo de tabla inferior
        gbc.weighty = 0.75;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        this.add(crearModuloTransacciones(), gbc);
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
		PanelPersonalizadoTabla panel = new PanelPersonalizadoTabla();
        tablaTransacciones = panel.getTabla();
        return panel;
	}
	
	public void setTableModel(AdvancedTableModel<User> tableModel){
		tablaTransacciones.setModel(tableModel);
    }
	
}
