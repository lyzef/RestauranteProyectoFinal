package utilidades.views;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.ComponenteIngredienteReceta;
import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;

public class CardIngrediente extends PanelRedondeadoConMargen {
    
    private ComponenteIngredienteReceta ingrediente;
    private JTextField cantidadField;
    private JLabel eliminar;
    private JCheckBox esOpcionalCheck; // 1. Agregamos el CheckBox

    public CardIngrediente(ComponenteIngredienteReceta ingrediente) {
        super(PanelRedondeadoConMargen.RADIO_ESQUINA_ESTANDAR, Paleta_Colores.CONTENEDORES.getColor(), 2, 2);
        this.ingrediente = ingrediente;
        inicializarPanel();
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
    }

    private void inicializarPanel() {
        setLayout(new GridBagLayout());
        
        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setOpaque(false);
        
        JLabel nombreIngrediente = new JLabel(ingrediente.getNombre());
        nombreIngrediente.setFont(AppFont.normal());
        nombreIngrediente.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        
        JLabel precioXUnidad = new JLabel("$" + ingrediente.getCostoUnitario() + " / " + ingrediente.getUnidadMedida().toString());
        precioXUnidad.setFont(AppFont.small());
        precioXUnidad.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor()); 
        
        panelTextos.add(nombreIngrediente);
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
        
        JLabel unidad = new JLabel(ingrediente.getUnidadMedida().toString());
        unidad.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());

        esOpcionalCheck = new JCheckBox("Opcional");
        esOpcionalCheck.setOpaque(false); 
        esOpcionalCheck.setFont(AppFont.small());
        esOpcionalCheck.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        esOpcionalCheck.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
        add(esOpcionalCheck, gbc);
        
        gbc.gridx = 4;
        add(eliminar, gbc);
    }

    public ComponenteIngredienteReceta getIngrediente() {
        return ingrediente;
    }
    
    public void setCantidadYEstadoVisible(double cantidad, boolean opcional) {
        cantidadField.setText(String.valueOf(cantidad));
        esOpcionalCheck.setSelected(opcional);
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
            return 0.0;
        }
    }
    
    public boolean isOpcional() {
        return esOpcionalCheck.isSelected();
    }
    
    public void setEnabledCheckBox(boolean b) {
    	esOpcionalCheck.setEnabled(b);
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
    
    
}