package utilidades.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;

public class ModuloParaEstadistica extends PanelRedondeadoConMargen {
    
    private JLabel lblValor;
    private JLabel lblSubtitulo;
    private JLabel lblIcono;
    
    PanelRedondeadoConMargen pIcono;
    
    public ModuloParaEstadistica(String titulo, String valorInicial, String subtituloInicial, Color colorFondoIcono, String rutaIcono) {
        super();
        this.setLayout(new GridBagLayout());
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setOpaque(false);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        
        gbc.gridx = 0;
        gbc.weightx = 0.85;
        this.add(panelPrincipal, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.weightx = 0.15;
        
        pIcono = new PanelRedondeadoConMargen(colorFondoIcono);
        pIcono.setLayout(new GridBagLayout());
        lblIcono = new JLabel();
        if(rutaIcono != null && !rutaIcono.isEmpty()) {
            GeneradorIconos.aplicarIcono(rutaIcono, lblIcono);
        }
        pIcono.add(lblIcono, new GridBagConstraints());
        
        this.add(pIcono, gbc);

        JLabel lblTitulo = new JLabel(titulo, JLabel.LEFT);
        lblTitulo.setFont(AppFont.normal());
        lblTitulo.setForeground(Paleta_Colores.TEXTO_SECUNDARIO.getColor());
        panelPrincipal.add(lblTitulo);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));
        
        lblValor = new JLabel(valorInicial);
        lblValor.setFont(AppFont.title());
        lblValor.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        panelPrincipal.add(lblValor);
        
        lblSubtitulo = new JLabel(subtituloInicial);
        lblSubtitulo.setFont(AppFont.small());
        lblSubtitulo.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        panelPrincipal.add(lblSubtitulo);
    }
    
    public void setValor(String valor) {
        this.lblValor.setText(valor);
    }

    public void setSubtitulo(String subtitulo) {
        this.lblSubtitulo.setText(subtitulo);
    }

    public void setColorIconAndSubtitulo(Color color) {
        this.lblSubtitulo.setForeground(color);
        this.pIcono.colorFondo = color;
    }
}