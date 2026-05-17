package views.Admin;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import utilidades.AppFont;
import utilidades.Paleta_Colores;
import utilidades.views.PanelRedondeadoConMargen;

public class DashboardView extends JPanel{
	
	public DashboardView() {
		//Ajustes
		this.setBackground(Paleta_Colores.FONDO.getColor());
		
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
		this.add(moduloTotalVenta(),gbc);
		
		gbc.gridx = 2;
		gbc.gridwidth = 1;
		this.add(moduloTotalVenta(),gbc);
		
		// 1 Modulo de tabla
		gbc.weighty = 0.75;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		this.add(moduloTotalVenta(), gbc);
		
		
		
		
		
		
	}
	
	
	private JPanel moduloTotalVenta() {
		JPanel panelTotalVentas = new PanelRedondeadoConMargen(15, Paleta_Colores.CONTENEDORES.getColor(), 5);
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
		
		JLabel VentasTotales = new JLabel("Sin datos");
		VentasTotales.setFont(AppFont.normal());
		VentasTotales.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(VentasTotales);
		
		JLabel AumentoEnVentasDesdeAyer = new JLabel("Aumento en 15% desde ayer");
		AumentoEnVentasDesdeAyer.setFont(AppFont.small());
		AumentoEnVentasDesdeAyer.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(AumentoEnVentasDesdeAyer);
		
		return panelTotalVentas;
	}
	
	private JPanel moduloOrdenesHoy() {
		JPanel panelTotalVentas = new PanelRedondeadoConMargen(15, Paleta_Colores.CONTENEDORES.getColor(), 5);
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
		
		JLabel VentasTotales = new JLabel("Sin datos");
		VentasTotales.setFont(AppFont.normal());
		VentasTotales.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(VentasTotales);
		
		JLabel AumentoEnVentasDesdeAyer = new JLabel("25 % menos que el promedio");
		AumentoEnVentasDesdeAyer.setFont(AppFont.small());
		AumentoEnVentasDesdeAyer.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(AumentoEnVentasDesdeAyer);
	
		return panelTotalVentas;
	}
	
	private JPanel moduloTopVentas() {
		JPanel panelTotalVentas = new PanelRedondeadoConMargen(15, Paleta_Colores.CONTENEDORES.getColor(), 5);
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

        
		JLabel titulo = new JLabel("Top ventas", JLabel.LEFT);
		titulo.setFont(AppFont.normal());
		titulo.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor());
		panelPrincipal.add(titulo);

		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));
		
		JLabel VentasTotales = new JLabel("Sin datos");
		VentasTotales.setFont(AppFont.normal());
		VentasTotales.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(VentasTotales);
		
		JLabel AumentoEnVentasDesdeAyer = new JLabel("67 unidaes vendidas hoy");
		AumentoEnVentasDesdeAyer.setFont(AppFont.small());
		AumentoEnVentasDesdeAyer.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(AumentoEnVentasDesdeAyer);
		
		return panelTotalVentas;
	}
	
}
