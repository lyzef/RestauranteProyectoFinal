package utilidades.views;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.Platillo;
import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;

public class CardCarritoPlatillo extends PanelRedondeadoConMargen {
    
    private Platillo platillo;
    private JTextField cantidadField;
    private JLabel eliminar;

    // Constructor sin cantidad inicial
    public CardCarritoPlatillo(Platillo platillo) {
        this(platillo, 1.0);
    }

    // Constructor con cantidad inicial
    public CardCarritoPlatillo(Platillo platillo, double cantidadInicial) {
        super(PanelRedondeadoConMargen.RADIO_ESQUINA_ESTANDAR, Paleta_Colores.CONTENEDORES.getColor(), 2, 2);
        this.platillo = platillo;
        inicializarPanel();
        setCantidad(cantidadInicial);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
    }

    private void inicializarPanel() {
        setLayout(new GridBagLayout());
        
        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setOpaque(false);
        
        JLabel nombrePlatillo = new JLabel(platillo.getComponenteNombre());
        nombrePlatillo.setFont(AppFont.normal());
        nombrePlatillo.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        
        JLabel precioXUnidad = new JLabel("$" + platillo.getPrecioVenta() + " / unidad");
        precioXUnidad.setFont(AppFont.small());
        precioXUnidad.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor()); 
        
        panelTextos.add(nombrePlatillo);
        panelTextos.add(precioXUnidad);
        
        JPanel panelField = new PanelRedondeadoConMargen(
                PanelRedondeadoConMargen.RADIO_ESQUINA_ESTANDAR, 
                Paleta_Colores.TEXTO_PRINCIPAL.getColor(), 1, 1);
        panelField.setOpaque(true);
        
        cantidadField = new JTextField();
        cantidadField.setColumns(3); 
        cantidadField.setHorizontalAlignment(JTextField.CENTER); 
        cantidadField.setBorder(null);
        panelField.add(cantidadField);
        
        JLabel unidad = new JLabel("uds");
        unidad.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());

        eliminar = new JLabel();
        GeneradorIconos.aplicarIcono("/assets/image/cruz.png", eliminar);
        eliminar.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        add(panelTextos, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        add(panelField, gbc);
        
        gbc.gridx = 2;
        add(unidad, gbc);
        
        gbc.gridx = 3;
        add(eliminar, gbc);
    }

    public Platillo getPlatillo() {
        return platillo;
    }
    
    public void setCantidad(double cantidad) {
        cantidadField.setText(String.valueOf(cantidad));
    }
    
    public JLabel getBotonEliminar() {
        return eliminar;
    }
    
    public double getCantidad() {
        try {
            String texto = cantidadField.getText().trim();
            if (texto.isEmpty()) {
                return 0.0;
            }
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
        	System.out.println("ESCRIBE UN FKIN ENTERO");
            return 0.0;
        }
    }
    
    public void setEnableEliminar(boolean b) {
        eliminar.setEnabled(b);
    }
    
    public void setEditableTextField(boolean b) {
        cantidadField.setEditable(b);
    }

    public JTextField getCantidadField() {
        return cantidadField;
    }
    
    public JLabel getEliminarLabel() {
        return eliminar;
    }
    
    public double getPrecioTotal() {
        return getCantidad() * platillo.getPrecioVenta();
    }
}