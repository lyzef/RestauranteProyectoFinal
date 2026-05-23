package views.Admin;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utilidades.AppFont;
import utilidades.Paleta_Colores;
import utilidades.views.PanelRedondeadoConMargen;

public class InventoryView extends JPanel{
	JLabel totalVentas;
	JLabel mensajeTotalVenta;
	
	
	public InventoryView() {
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
		gbc.gridx = 3;
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
	
	private JPanel crearModuloTotalVentas() {
		JPanel panelPrincipal = new PanelRedondeadoConMargen();
		panelPrincipal.setLayout(new GridBagLayout());
		
		JPanel panelInfo = new JPanel();
		panelPrincipal.setOpaque(false);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		//JPanel principal
		gbc.gridx = 0;          // columna 0
        gbc.weightx = 0.75;     // 75% del espacio horizontal
        gbc.fill = GridBagConstraints.BOTH;
     
        panelPrincipal.add(panelInfo,gbc);
        
        //JLabel mensaje info superior
        mensajeTotalVenta = new JLabel("Icono");
        
  		gbc.gridx = 1;          // columna 0
        gbc.weightx = 0.25;     // 25% del espacio horizontal
        gbc.fill = GridBagConstraints.BOTH;
        panelPrincipal.add(mensajeTotalVenta,gbc);

        
		JLabel titulo = new JLabel("Ordenes en el dia", JLabel.LEFT);
		titulo.setFont(AppFont.normal());
		titulo.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor());
		panelPrincipal.add(titulo);

		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));
		
		totalVentas = new JLabel("Sin datos");
		totalVentas.setFont(AppFont.normal());
		totalVentas.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
		panelPrincipal.add(totalVentas);
		
		return panelPrincipal;
	}
	
	private JPanel crearModuloArticulosBajoStock() {
		
	}
	
	private JPanel crearModuloGastoMensuales() {
		
	}
	
	private JPanel crearModuloOrdenesDelDia() {
		
	}
	
	
}
