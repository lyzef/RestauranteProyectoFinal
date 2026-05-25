package utilidades.views;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utilidades.AppFont;
import utilidades.Paleta_Colores;
// Asegúrate de importar tu clase AppFont si está en otro paquete

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BarraBusquedaFiltro extends JPanel {

    private JTextField textFieldTabla;
    private JLabel lblFiltroActual;
    private JList<String> listaFiltros;
    private JPopupMenu popupMenuFiltro;


    public BarraBusquedaFiltro(String[] opcionesFiltro) {
        this("Buscar...", opcionesFiltro);
    }

    public BarraBusquedaFiltro(String placeholderTexto, String[] opcionesFiltro) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false); // Fondo transparente para no tapar el contenedor padre

        
        PanelRedondeadoConMargen panelSearch = new PanelRedondeadoConMargen(
                PanelRedondeadoConMargen.RADIO_ESQUINA_ESTANDAR,
                Paleta_Colores.HEADER_TABLA.getColor(), 
                5, 2 
        );
        panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));


        textFieldTabla = new JTextField(placeholderTexto, 25);
        textFieldTabla.setOpaque(false);
        textFieldTabla.setFont(AppFont.normal());
        textFieldTabla.setBorder(null);
        textFieldTabla.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor());
        panelSearch.add(textFieldTabla);

        //Boton filtro
        PanelRedondeadoConMargen panelFiltro = new PanelRedondeadoConMargen(
                PanelRedondeadoConMargen.RADIO_ESQUINA_ESTANDAR,
                Paleta_Colores.HEADER_TABLA.getColor(),
                5, 2
        );
        panelFiltro.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelFiltro.setCursor(new Cursor(Cursor.HAND_CURSOR)); 

        String textoFiltroInicial = (opcionesFiltro != null && opcionesFiltro.length > 0) ? opcionesFiltro[0] : "Filtrar";
        
        lblFiltroActual = new JLabel(textoFiltroInicial + "  ˅");
        lblFiltroActual.setFont(AppFont.normal()); 
        lblFiltroActual.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor()); 
        panelFiltro.add(lblFiltroActual);

        popupMenuFiltro = new JPopupMenu();
        listaFiltros = new JList<>(opcionesFiltro);
        
        //Estilizacion de JList
        listaFiltros.setBackground(Paleta_Colores.HEADER_TABLA.getColor());
        listaFiltros.setForeground(Color.WHITE);
        listaFiltros.setSelectionBackground(Paleta_Colores.CONTENEDORES.getColor());
        listaFiltros.setSelectionForeground(Color.WHITE);
        listaFiltros.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        popupMenuFiltro.add(listaFiltros);
        popupMenuFiltro.setBorder(BorderFactory.createLineBorder(Paleta_Colores.CONTENEDORES.getColor()));

        // Eventos para el menú desplegable
        panelFiltro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Mostrar el popup justo debajo del panel de filtro
                popupMenuFiltro.show(panelFiltro, 0, panelFiltro.getHeight() - 5);
            }
        });

        listaFiltros.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String seleccion = listaFiltros.getSelectedValue();
                    if (seleccion != null) {
                        lblFiltroActual.setText(seleccion + "  ˅");
                        popupMenuFiltro.setVisible(false); // OCultar flechita
                    }
                }
            }
        });

        
        add(panelSearch);
        add(Box.createRigidArea(new Dimension(10, 0))); // Espacio entre barra y filtro
        add(panelFiltro);
    }

    public String getTextoBusqueda() {
        return textFieldTabla.getText();
    }

    public String getFiltroSeleccionado() {
        String seleccion = listaFiltros.getSelectedValue();
        if (seleccion == null && listaFiltros.getModel().getSize() > 0) {
            return listaFiltros.getModel().getElementAt(0);
        }
        return seleccion;
    } 

	public JTextField getTextFieldTabla() {
		return textFieldTabla;
	}
	
	public void setListaFiltros(String[] listData) {
		listaFiltros.setListData(listData);
	}

	public JPopupMenu getPopupMenuFiltro() {
		return popupMenuFiltro;
	}

	public JList<String> getListaFiltros() {
		return listaFiltros;
	}
	
	
    
    
}
