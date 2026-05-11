package views;

import javax.swing.*;
import java.awt.*;
import controller.UserController;

public class Login extends JFrame {

    private Font font = new Font("Arial", Font.BOLD, 16);   
    private Font miniFont = new Font("Arial", Font.BOLD, 13); 
    private JTextField entradaCorreo;
    private JPasswordField entradaContrasena;
    private JLabel labelAdvertenciaContrasena;
    private JLabel labelAdvertenciaCorreo;
    private JButton botonEntrar;
    private JLabel botonRegistrar;
    private Color colorFondo = new Color(30,30,30);
    private Color colorBoton = new Color(255,102,0);

    public Login() {
        setSize(800,400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Madero's Chef System");

        loadIcon();
        initializeComponents();
        //configurarEventos(); // Método para la lógica del botón
        setVisible(true);
    }

    private void loadIcon(){
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Image icono = tk.getImage("src/image/IconoApliacionPrincipal.jpg"); 
            setIconImage(icono);
        } catch (Exception e) {
            System.out.println("Icono no encontrado, continuando...");
        }
    }

    private void initializeComponents() {
        JPanel panelPrincipal = new JPanel(new GridLayout(1,2));

        // Panel Izquierdo: Imagen
        JPanel panelImagen = new JPanel();
        panelImagen.setBackground(colorFondo);

        try {
            ImageIcon tacoOriginal = new ImageIcon("src/image/Logo.png"); 
            Image tacoEscalado = tacoOriginal.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel labelTaco = new JLabel(new ImageIcon(tacoEscalado));
            labelTaco.setHorizontalAlignment(SwingConstants.CENTER);
            panelImagen.add(labelTaco);
        } catch (Exception e) {
            panelImagen.add(new JLabel("Logo no encontrado"));
        }

        // Panel Derecho: Login
        JPanel panelLogin = new JPanel(new BorderLayout());
        panelLogin.setBackground(colorFondo);

        createLogo(panelLogin);
        createForm(panelLogin);
        createButtons(panelLogin);

        panelPrincipal.add(panelImagen);
        panelPrincipal.add(panelLogin);
        add(panelPrincipal);
    }

    private void configurarEventos() {
        botonEntrar.addActionListener(e -> {
            try {

                JOptionPane.showMessageDialog(this, "Felicidades sabes escribir!", "Bienvenido....", JOptionPane.INFORMATION_MESSAGE);

                UsersView vistaUsuarios = new UsersView();
                UserController controlador = new UserController(vistaUsuarios);

                controlador.loadUsers();
                
                vistaUsuarios.setVisible(true);
                this.dispose(); 
                
            } catch (Exception ex) {
                ex.printStackTrace(); 
                JOptionPane.showMessageDialog(this, "Error al cargar el sistema: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void createLogo(JPanel panelLogin) {
        JPanel panelLogo = new JPanel();
        panelLogo.setBackground(colorFondo);
        panelLogo.setLayout(new BoxLayout(panelLogo, BoxLayout.Y_AXIS));

        JLabel labelNombreRestaurante = new JLabel("Madero's");
        labelNombreRestaurante.setFont(new Font("Times", Font.BOLD, 24));
        labelNombreRestaurante.setForeground(Color.WHITE);
        labelNombreRestaurante.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLogo.add(labelNombreRestaurante);

        JLabel labelTitulo = new JLabel("Inicio de sesion");
        labelTitulo.setFont(font);
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLogo.add(labelTitulo);

        panelLogo.setBorder(BorderFactory.createEmptyBorder(10,5,5,5));
        panelLogin.add(panelLogo, BorderLayout.NORTH);
    }

    private void createForm(JPanel panelLogin) {
        JPanel panelForm = new JPanel(new GridLayout(6, 1, 5, 5));
        panelForm.setBackground(colorFondo);

        panelForm.add(crearLabel("Ingrese el correo electrónico"));
        entradaCorreo = new JTextField(20);
        entradaCorreo.setFont(font);
        panelForm.add(entradaCorreo);

        labelAdvertenciaCorreo = new JLabel("Correo es requerido");
        configurarAdvertencia(labelAdvertenciaCorreo, panelForm);

        panelForm.add(crearLabel("Ingrese la contraseña"));
        entradaContrasena = new JPasswordField(20);
        entradaContrasena.setFont(font);
        panelForm.add(entradaContrasena);

        labelAdvertenciaContrasena = new JLabel("Contrasena es requerido");
        configurarAdvertencia(labelAdvertenciaContrasena, panelForm);

        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelLogin.add(panelForm, BorderLayout.CENTER);
    }

    private JLabel crearLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(font);
        l.setForeground(Color.WHITE);
        return l;
    }

    private void configurarAdvertencia(JLabel l, JPanel p) {
        l.setFont(miniFont);
        l.setForeground(Color.WHITE);
        l.setVisible(false);
        p.add(l);
    }

    private void createButtons(JPanel panelLogin) {
        JPanel panelButtons = new JPanel();
        panelButtons.setBackground(colorFondo);
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));

        botonEntrar = new JButton("Entrar");
        botonEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonEntrar.setFont(font);
        botonEntrar.setBackground(colorBoton);
        botonEntrar.setForeground(Color.WHITE);
        botonEntrar.setFocusPainted(false);
        panelButtons.add(botonEntrar);

        panelButtons.add(Box.createVerticalStrut(10));

        botonRegistrar = new JLabel("Registrar nuevo empleado");
        botonRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRegistrar.setFont(miniFont);
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelButtons.add(botonRegistrar);

        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        panelLogin.add(panelButtons, BorderLayout.SOUTH);
    }

	public JTextField getEntradaCorreo() {
		return entradaCorreo;
	}

	public void setEntradaCorreo(JTextField entradaCorreo) {
		this.entradaCorreo = entradaCorreo;
	}

	public JPasswordField getEntradaContrasena() {
		return entradaContrasena;
	}

	public void setEntradaContrasena(JPasswordField entradaContrasena) {
		this.entradaContrasena = entradaContrasena;
	}
	
	

	public JLabel getLabelAdvertenciaCorreo() {
		return labelAdvertenciaCorreo;
	}

	public void setLabelAdvertenciaCorreo(JLabel labelAdvertenciaCorreo) {
		this.labelAdvertenciaCorreo = labelAdvertenciaCorreo;
	}

	public JLabel getLabelAdvertenciaContrasena() {
		return labelAdvertenciaContrasena;
	}

	public void setLabelAdvertenciaContrasena(JLabel labelAdvertenciaContrasena) {
		this.labelAdvertenciaContrasena = labelAdvertenciaContrasena;
	}

	public JButton getBotonEntrar() {
		return botonEntrar;
	}

	public void setBotonEntrar(JButton botonEntrar) {
		this.botonEntrar = botonEntrar;
	}

	public JLabel getBotonRegistrar() {
		return botonRegistrar;
	}

	public void setBotonRegistrar(JLabel botonRegistrar) {
		this.botonRegistrar = botonRegistrar;
	}

    // Getters
    
    
}