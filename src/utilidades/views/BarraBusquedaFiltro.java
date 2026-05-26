package utilidades.views;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utilidades.AppFont;
import utilidades.Paleta_Colores;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BarraBusquedaFiltro extends JPanel {

    private JTextField textFieldTabla;
    private JLabel lblFiltroActual;
    private JList<String> listaFiltros;
    private JPopupMenu popupMenuFiltro;
    private String[] opcionesActuales; 
    public BarraBusquedaFiltro(String[] opcionesFiltro) {
        this("Buscar...", opcionesFiltro);
    }

    public BarraBusquedaFiltro(String placeholderTexto, String[] opcionesFiltro) {
        this.opcionesActuales = opcionesFiltro; 
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);

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
        
        estilizarListaFiltros();
        
        // Agregar la lista al popup
        popupMenuFiltro.add(new JScrollPane(listaFiltros)); // Usar JScrollPane por si hay muchos items
        popupMenuFiltro.setBorder(BorderFactory.createLineBorder(Paleta_Colores.CONTENEDORES.getColor()));

        // Eventos para el menú desplegable
        panelFiltro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Asegurar que la lista tenga el tamaño adecuado antes de mostrar
                actualizarTamanoPopup();
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
                        popupMenuFiltro.setVisible(false);
                    }
                }
            }
        });

        add(panelSearch);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(panelFiltro);
    }

    private void estilizarListaFiltros() {
        listaFiltros.setBackground(Paleta_Colores.HEADER_TABLA.getColor());
        listaFiltros.setForeground(Color.WHITE);
        listaFiltros.setSelectionBackground(Paleta_Colores.CONTENEDORES.getColor());
        listaFiltros.setSelectionForeground(Color.WHITE);
        listaFiltros.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        listaFiltros.setVisibleRowCount(Math.min(opcionesActuales.length, 10)); // Mostrar máximo 10 items
    }

    private void actualizarTamanoPopup() {
        int itemCount = listaFiltros.getModel().getSize();
        int visibleRows = Math.min(itemCount, 10);
        listaFiltros.setVisibleRowCount(visibleRows);
        popupMenuFiltro.pack();
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
        this.opcionesActuales = listData;
        
        // Actualizar el modelo de la lista existente
        listaFiltros.setListData(listData);
        
        // Actualizar el texto del label con el primer elemento
        if (listData != null && listData.length > 0) {
            listaFiltros.setSelectedIndex(0);
            lblFiltroActual.setText(listData[0] + "  ˅");
        } else {
            lblFiltroActual.setText("Filtrar  ˅");
        }
        
        // Re-estilizar si es necesario
        estilizarListaFiltros();
        
        // Refrescar el popup
        popupMenuFiltro.revalidate();
        popupMenuFiltro.repaint();
    }
    
    /**
     * Método alternativo para cambiar completamente el contenido del popup
     */
    public void actualizarPopupCompleto(String[] nuevasOpciones) {
        this.opcionesActuales = nuevasOpciones;
        
        popupMenuFiltro.removeAll();
        
        listaFiltros = new JList<>(nuevasOpciones);
        estilizarListaFiltros();
        popupMenuFiltro.add(new JScrollPane(listaFiltros));
        listaFiltros.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String seleccion = listaFiltros.getSelectedValue();
                    if (seleccion != null) {
                        lblFiltroActual.setText(seleccion + "  ˅");
                        popupMenuFiltro.setVisible(false);
                    }
                }
            }
        });
        
        if (nuevasOpciones != null && nuevasOpciones.length > 0) {
            listaFiltros.setSelectedIndex(0);
            lblFiltroActual.setText(nuevasOpciones[0] + "  ˅");
        }
        
        popupMenuFiltro.revalidate();
        popupMenuFiltro.repaint();
    }

    public JPopupMenu getPopupMenuFiltro() {
        return popupMenuFiltro;
    }

    public JList<String> getListaFiltros() {
        return listaFiltros;
    }
    
    /**
     * Método para agregar un listener externo a la selección de filtros
     */
    public void addFiltroSelectionListener(ListSelectionListener listener) {
        listaFiltros.addListSelectionListener(listener);
    }
    
    /**
     * Método para limpiar el campo de búsqueda
     */
    public void limpiarBusqueda() {
        textFieldTabla.setText("");
    }
    
    /**
     * Método para establecer el placeholder del campo de texto
     */
    public void setPlaceholder(String placeholder) {
        textFieldTabla.setText(placeholder);
        textFieldTabla.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor());
    }
}