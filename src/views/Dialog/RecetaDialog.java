package views.Dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ca.odell.glazedlists.swing.DefaultEventComboBoxModel;
import models.ComponenteIngredienteReceta;
import utilidades.AppFont;
import utilidades.GeneradorIconos;
import utilidades.Paleta_Colores;
import utilidades.views.BotonPersonalizado;
import utilidades.views.CardIngrediente;
import utilidades.views.PanelRedondeadoConMargen;

public class RecetaDialog extends JDialog {
    
    private JPanel panelFormulario;
    private JPanel panelIngredientes;
    private JLabel titulo;
    private JLabel subTitulo;
    private BotonPersonalizado botonAgregarIngrediente;
    private JButton botonFinalizar;
    private JButton botonCerrar;
    private JComboBox<ComponenteIngredienteReceta> ingredientesComboBox;
    
    // Variables para Precio
    private JTextField campoPrecioTotal;
    private JLabel labelPrecioSugerido;
    private JLabel refrescarPrecio;
    
    // AÑADIDO: Variables para Calorías Totales
    private JTextField campoCaloriasTotales;
    private JLabel labelCaloriasSugeridas;
    private JLabel refrescarCalorias;
    
    public RecetaDialog(JFrame frame) {
        super(frame, true); 
        
        setSize(700,500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setTitle("Formulario");
        setLocationRelativeTo(null);
        
        ImageIcon i = GeneradorIconos.cargarIcono("/assets/image/IconoApliacionPrincipal.jpg");
        if (i != null) {
            setIconImage(i.getImage());
        }
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        JPanel panelContenedorSuperior = new JPanel();
        panelContenedorSuperior.setLayout(new BoxLayout(panelContenedorSuperior, BoxLayout.Y_AXIS));
        panelContenedorSuperior.setBackground(Paleta_Colores.CONTENEDORES.getColor());
        
        panelFormulario = new JPanel();
        
        JPanel panelContenedorInferior = new JPanel();
        panelContenedorInferior.setBackground(Paleta_Colores.CONTENEDORES.getColor());
        
        titulo = new JLabel("Nuevo componente");
        titulo.setFont(AppFont.title());
        titulo.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        
        subTitulo = new JLabel(" ");
        subTitulo.setFont(AppFont.normal());
        subTitulo.setForeground(Paleta_Colores.ATENCION.getColor());
        subTitulo.setAlignmentX(CENTER_ALIGNMENT);
        
        panelContenedorSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelContenedorSuperior.add(titulo);
        panelContenedorSuperior.add(subTitulo);
        
        botonFinalizar = new JButton("Terminar");
        botonFinalizar.setBackground(Paleta_Colores.ACENTO_PRIMARIO.getColor());
        botonFinalizar.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        panelContenedorInferior.add(botonFinalizar);

        botonCerrar = new JButton("Cancelar");
        botonCerrar.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        botonCerrar.setBackground(Paleta_Colores.URGENTE.getColor());
        panelContenedorInferior.add(botonCerrar);
        
        JScrollPane scrollPane = new JScrollPane(crearFormulario());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        add(scrollPane, BorderLayout.CENTER);
        add(panelContenedorSuperior, BorderLayout.NORTH);
        add(panelContenedorInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearFormulario() {
        panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Paleta_Colores.FONDO.getColor());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0; 
        gbc.weighty = 0.0; 
        gbc.gridx = 0;
        
        gbc.gridy = 0;
        panelFormulario.add(crearPanelPrecioSugerido(), gbc);
        
        gbc.gridy = 1;
        panelFormulario.add(crearPanelCaloriasTotales(), gbc);
        
        gbc.gridy = 2;
        panelFormulario.add(crearPanelAgregarIngredientes(), gbc);
        
        gbc.gridy = 3;
        panelIngredientes = new JPanel(); 
        panelIngredientes.setOpaque(false);
        panelIngredientes.setLayout(new BoxLayout(panelIngredientes, BoxLayout.Y_AXIS));
        panelFormulario.add(panelIngredientes, gbc);
        
        // 5. DESPLAZADO: Espaciador (ahora gridy = 4)
        gbc.gridy = 4;
        gbc.weighty = 1.0; 
        gbc.fill = GridBagConstraints.BOTH;
        JPanel espaciador = new JPanel();
        espaciador.setOpaque(false);
        panelFormulario.add(espaciador, gbc);

        return panelFormulario;
    }
    
    private JPanel crearPanelPrecioSugerido() {
        JPanel panelPrecio = new PanelRedondeadoConMargen();
        panelPrecio.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        panelPrecio.setLayout(new GridBagLayout());
        
        JLabel tituloPanel = new JLabel("Costo Final de la Receta");
        tituloPanel.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        tituloPanel.setFont(AppFont.bold());
        
        campoPrecioTotal = new JTextField();
        campoPrecioTotal.setFont(AppFont.normal());
        
        JPanel panelPrecioYBoton = new JPanel();
        panelPrecioYBoton.setOpaque(false);
        panelPrecioYBoton.setLayout(new BoxLayout(panelPrecioYBoton, BoxLayout.X_AXIS));
        
        labelPrecioSugerido = new JLabel("Costo sugerido en base a ingredientes: $0.00");
        labelPrecioSugerido.setForeground(Paleta_Colores.ATENCION.getColor());
        labelPrecioSugerido.setFont(AppFont.normal());
        
        refrescarPrecio = new JLabel();
        GeneradorIconos.aplicarIcono("/assets/image/actualizar.png", refrescarPrecio);
        
        panelPrecioYBoton.add(labelPrecioSugerido);
        panelPrecioYBoton.add(Box.createHorizontalGlue());
        panelPrecioYBoton.add(refrescarPrecio);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.weightx = 1.0;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPrecio.add(tituloPanel, gbc);
        
        gbc.gridy = 1;
        panelPrecio.add(campoPrecioTotal, gbc);
        
        gbc.insets = new Insets(0, 10, 10, 10); 
        gbc.gridy = 2;
        panelPrecio.add(panelPrecioYBoton, gbc);
        
        return panelPrecio;
    }

    private JPanel crearPanelCaloriasTotales() {
        JPanel panelCalorias = new PanelRedondeadoConMargen();
        panelCalorias.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        panelCalorias.setLayout(new GridBagLayout());
        
        JLabel tituloPanel = new JLabel("Calorías Totales de la Receta");
        tituloPanel.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        tituloPanel.setFont(AppFont.bold());
        
        campoCaloriasTotales = new JTextField();
        campoCaloriasTotales.setFont(AppFont.normal());
        
        JPanel panelCaloriasYBoton = new JPanel();
        panelCaloriasYBoton.setOpaque(false);
        panelCaloriasYBoton.setLayout(new BoxLayout(panelCaloriasYBoton, BoxLayout.X_AXIS));
        
        labelCaloriasSugeridas = new JLabel("Calorías calculadas en base a ingredientes: 0 kcal");
        labelCaloriasSugeridas.setForeground(Paleta_Colores.ATENCION.getColor());
        labelCaloriasSugeridas.setFont(AppFont.normal());
        
        refrescarCalorias = new JLabel();
        GeneradorIconos.aplicarIcono("/assets/image/actualizar.png", refrescarCalorias);
        
        panelCaloriasYBoton.add(labelCaloriasSugeridas);
        panelCaloriasYBoton.add(Box.createHorizontalGlue());
        panelCaloriasYBoton.add(refrescarCalorias);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.weightx = 1.0;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCalorias.add(tituloPanel, gbc);
        
        gbc.gridy = 1;
        panelCalorias.add(campoCaloriasTotales, gbc);
        
        gbc.insets = new Insets(0, 10, 10, 10); 
        gbc.gridy = 2;
        panelCalorias.add(panelCaloriasYBoton, gbc);
        
        return panelCalorias;
    }
    
    private JPanel crearPanelAgregarIngredientes() {
        JPanel infoPanel = new PanelRedondeadoConMargen();
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        infoPanel.setLayout(new GridBagLayout());
        
        JLabel tituloPanel = new JLabel("Agregar ingredientes");
        tituloPanel.setForeground(Paleta_Colores.TEXTO_PRINCIPAL.getColor());
        tituloPanel.setFont(AppFont.bold());
        
        ingredientesComboBox = new JComboBox<>();
        botonAgregarIngrediente = new BotonPersonalizado("+", Paleta_Colores.EXITO.getColor());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(tituloPanel, gbc);
        
        gbc.weightx = 0.85;
        gbc.gridy = 1;
        infoPanel.add(ingredientesComboBox, gbc);
        
        gbc.weightx = 0.15;
        gbc.gridx = 1;
        infoPanel.add(botonAgregarIngrediente, gbc);
        
        return infoPanel;
    }
    
    
    public void agregarCardIngrediente(CardIngrediente card) {
        panelIngredientes.add(card);
        panelIngredientes.revalidate();
        panelIngredientes.repaint();
    }
    
    public int solicitarCierre(String texto) {
        return JOptionPane.showConfirmDialog(null, texto, "Confirmación", JOptionPane.YES_NO_OPTION);
    }
    
    public void mostrarDialogMensaje(String texto) {
        JOptionPane.showMessageDialog( null,texto);
    }
    
    public Component[] getListaIngredientes() {
        return panelIngredientes.getComponents();
    }
    
    public void setComboBoxModel(DefaultEventComboBoxModel<ComponenteIngredienteReceta> model) {
        ingredientesComboBox.setModel(model);
    }

    public BotonPersonalizado getBotonAgregarIngrediente() { return botonAgregarIngrediente; }
    public void setBotonAgregarIngrediente(BotonPersonalizado botonAgregarIngrediente) { this.botonAgregarIngrediente = botonAgregarIngrediente; }

    public JButton getBotonFinalizar() { return botonFinalizar; }
    public void setBotonFinalizar(JButton botonFinalizar) { this.botonFinalizar = botonFinalizar; }

    public JButton getBotonCerrar() { return botonCerrar; }
    public void setBotonCerrar(JButton botonCerrar) { this.botonCerrar = botonCerrar; }

    public JComboBox<ComponenteIngredienteReceta> getIngredientesComboBox() { return ingredientesComboBox; }
    public JPanel getPanelIngredientes() { return panelIngredientes; }
  
    public void setSubTitulo(String t) {subTitulo.setText(t);}
    public void setTitulo(String titulo) {this.titulo.setText(titulo);}
    
    public JTextField getCampoPrecioTotal() { return campoPrecioTotal; }
    public void setPrecioSugerido(double precio) { 
        this.labelPrecioSugerido.setText("Costo sugerido en base a ingredientes: $" + precio); }
    
    public JLabel getBtnRefrescarPrecio() {
        return refrescarPrecio;
    }

    public JTextField getCampoCaloriasTotales() { 
        return campoCaloriasTotales; 
    }
    
    public void setCaloriasSugeridas(double calorias) { 
        this.labelCaloriasSugeridas.setText("Calorías calculadas en base a ingredientes: " + calorias + " kcal"); 
    }
    
    public JLabel getBtnRefrescarCalorias() {
        return refrescarCalorias;
    }
}