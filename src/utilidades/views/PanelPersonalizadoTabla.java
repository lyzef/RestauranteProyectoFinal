package utilidades.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import utilidades.AppFont;
import utilidades.Paleta_Colores;

public class PanelPersonalizadoTabla extends PanelRedondeadoConMargen{
	JTable tabla;
	public PanelPersonalizadoTabla() {
		super(RADIO_ESQUINA_ESTANDAR,Paleta_Colores.CONTENEDORES.getColor(),MARGEN_ESTANDAR,PADDING_INTERNO_TABLAS);
		this.setLayout(new BorderLayout());
		tabla = new JTable();
		JScrollPane panelConTabla = new JScrollPane(tabla);
		panelConTabla.setOpaque(false);
		panelConTabla.getViewport().setOpaque(false);
		panelConTabla.setBorder(null);
		panelConTabla.setViewportBorder(null);
		
		this.add(panelConTabla, BorderLayout.CENTER);
		
		personalizarTabla();
	}

	public void personalizarTabla() {
        Color colorFondo = this.colorFondo;       //Mismo color que el panel
        Color colorHeader = Paleta_Colores.HEADER_TABLA.getColor();      
        Color colorLineas = Paleta_Colores.HEADER_TABLA.getColor();      
        Color colorTexto = Paleta_Colores.TEXTO_PRINCIPAL.getColor();    // Blanco humo para el texto

        // FONDO Y TEXTO DE LA TABLA 
        tabla.setBackground(colorFondo);
        tabla.setForeground(colorTexto);
        tabla.setFont(AppFont.normal());
        
        // Altura de las filas
        tabla.setRowHeight(70);

        // FONDO CUANDO LA TABLA ESTÁ VACÍA 
        tabla.setFillsViewportHeight(true); 

        // LÍNEAS DE LA CUADRÍCULA ---
        // Solo lineas horizontales
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(colorLineas);
        // Quitar el espacio extra entre celdas
        tabla.setIntercellSpacing(new Dimension(0, 0));

        // ---  PERSONALIZACIÓN DEL HEADER ---
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(colorHeader);
        header.setForeground(colorTexto);
        header.setFont(AppFont.normal());
        header.setPreferredSize(new Dimension(100, 45)); // Header más alto
        
        //SELECCION DE FILA
        tabla.setCellSelectionEnabled(false);
        tabla.setRowSelectionAllowed(true);
        
        //tabla.setSelectionBackground(new Color(70, 130, 180)); // Color de fondo al seleccionar
		//tabla.setSelectionForeground(Color.WHITE);             // Color del texto al seleccionar
        
        // Para que el encabezado sea completamente plano 
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(colorHeader);
        headerRenderer.setForeground(colorTexto);
        headerRenderer.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, colorLineas)); // Solo línea abajo
        headerRenderer.setHorizontalAlignment(JLabel.CENTER); // Centrar el texto
        
        tabla.getTableHeader().setDefaultRenderer(headerRenderer); //Todas las columnas del encabezado por defecto
        
     // --- PERSONALIZACIÓN DE LAS CELDAS (FILAS) ---
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER); // Centrar el texto en las celdas
        cellRenderer.setBackground(colorFondo); // Mantener tu color de fondo
        cellRenderer.setForeground(colorTexto); // Mantener tu color de texto

        // Aplicar este renderizador a todas las celdas por defecto
        tabla.setDefaultRenderer(Object.class, cellRenderer);
    }

	public JTable getTabla() {
		return tabla;
	}

	public void setTabla(JTable tabla) {
		this.tabla = tabla;
	}
	
	
	
}
