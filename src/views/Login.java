package views;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private Font font = new Font("Arial", Font.PLAIN, 16);
    private Font miniFont = new Font("Arial", Font.PLAIN, 13);
    private JTextField entradaCorreo;
    private JPasswordField entradaContrasena;
    private JLabel labelAdvertenciaContrasena;
    private JLabel labelAdvertenciaCorreo;
    private JButton botonEntrar;
    private JLabel botonRegistrar;
    private Color colorFondo = new Color(242,244,243);

    public Login() {
        setSize(500,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Madero's Chef System");
        setVisible(false);

        loadIcon();
        initializeComponents();
        setVisible(true);
    }

    private void loadIcon(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image icono = tk.getImage("src/image/IconoApliacionPrincipal.jpg");
        setIconImage(icono);
    }

    private void initializeComponents() {
        createLogo();
        createForm();
        createButtons();
    }

    private void createLogo() {
        JPanel panelLogo = new JPanel();
        panelLogo.setBackground(colorFondo);
        panelLogo.setLayout(new BoxLayout(panelLogo, BoxLayout.Y_AXIS));

        JLabel labelNombreRestaurante = new JLabel("Madero's");
        labelNombreRestaurante.setFont(new Font("Times", Font.PLAIN, 24));
        labelNombreRestaurante.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLogo.add(labelNombreRestaurante);

        JLabel labelTitulo = new JLabel("Inicio de sesion");
        labelTitulo.setFont(font);
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLogo.add(labelTitulo);

        panelLogo.setBorder(BorderFactory.createEmptyBorder(10,5,5,5));
        add(panelLogo, BorderLayout.NORTH);
    }

    private void createForm() {
        JPanel panelForm = new JPanel(new GridLayout(6, 1, 10, 10));
        panelForm.setBackground(colorFondo);

        JLabel labelCorreo = new JLabel("Ingrese el correo electrónico");
        labelCorreo.setFont(font);
        panelForm.add(labelCorreo);

        entradaCorreo = new JTextField(20);
        entradaCorreo.setFont(font);
        panelForm.add(entradaCorreo);

        labelAdvertenciaCorreo = new JLabel("Correo es requerido");
        labelAdvertenciaCorreo.setFont(miniFont);
        labelAdvertenciaCorreo.setVisible(false);
        panelForm.add(labelAdvertenciaCorreo);

        JLabel labelPass = new JLabel("Ingrese la contraseña");
        labelPass.setFont(font);
        panelForm.add(labelPass);

        entradaContrasena = new JPasswordField(20);
        entradaContrasena.setFont(font);
        panelForm.add(entradaContrasena);

        labelAdvertenciaContrasena = new JLabel("Contrasena es requerido");
        labelAdvertenciaContrasena.setFont(miniFont);
        labelAdvertenciaContrasena.setVisible(false);
        panelForm.add(labelAdvertenciaContrasena);

        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panelForm, BorderLayout.CENTER);
    }

    private void createButtons() {
        JPanel panelButtons = new JPanel();
        panelButtons.setBackground(colorFondo);
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));

        botonEntrar = new JButton("Entrar");
        botonEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonEntrar.setFont(font);
        panelButtons.add(botonEntrar);

        botonRegistrar = new JLabel("Registrar nuevo empleado");
        botonRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRegistrar.setFont(miniFont);
        panelButtons.add(botonRegistrar);

        panelButtons.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panelButtons, BorderLayout.SOUTH);
    }

    // Getters para el controlador
    public JTextField getEntradaCorreo() { 
    	return entradaCorreo; 
    	}
    public JPasswordField getEntradaContrasena() { 
    	return entradaContrasena; 
    	}
    public JLabel getLabelAdvertenciaContrasena() { 
    	return labelAdvertenciaContrasena; 
    	}
    public JLabel getLabelAdvertenciaCorreo() { 
    	return labelAdvertenciaCorreo; 
    	}
    public JButton getBotonEntrar() { 
    	return botonEntrar; 
    	}
    public JLabel getBotonRegistrar() { 
    	return botonRegistrar; 
    	}
}
