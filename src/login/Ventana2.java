package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.border.Border;

public class Ventana2 extends JFrame {

    public Ventana2() {
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setTitle("Formulario");
        setLocationRelativeTo(null);

        // Ícono de la ventana (asegúrate de que la ruta apunte a un archivo de imagen real)
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image icono = tk.getImage("src/image/kfc.png"); // ejemplo: un archivo kfc.png dentro de src/image
        setIconImage(icono);

        InicializarComponentes();

        setVisible(true);
    }

    public void InicializarComponentes() {
        // Paneles
        JPanel panelContenedorSuperior = new JPanel();
        JPanel panelContenedorInferior = new JPanel();
        JPanel panelContenedorCentral = new JPanel();

        // Panel superior
        JLabel lblTitulo = new JLabel("Registro - Informacion del puesto");
        lblTitulo.setFont(new Font("Times", Font.PLAIN, 17));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelContenedorSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelContenedorSuperior.add(lblTitulo);

        // Panel inferior
        JButton lblBotonRegistro = new JButton("Siguiente");
        lblBotonRegistro.setBackground(new Color(144, 224, 239));
        panelContenedorInferior.add(lblBotonRegistro);

        // Panel central - Sub paneles
        panelContenedorCentral.setLayout(new BoxLayout(panelContenedorCentral, BoxLayout.Y_AXIS));
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 20, 10, 20);
        panelContenedorCentral.setBorder(emptyBorder);

        String[] informacionPersonal = {
            "Identificacion del puesto: ", "Descripcion de funciones: ", "Perfil del puesto: ", "Condiciones laborales: ", "Ubicacion organizacional: "
        };

        for (String info : informacionPersonal) {
            JLabel lbl = new JLabel(info);
            panelContenedorCentral.add(lbl);
            JTextField txtField = new JTextField(10);
            panelContenedorCentral.add(txtField);
        }

        // Scroll
        JScrollPane scroll = new JScrollPane(panelContenedorCentral);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Añadiendo paneles
        add(scroll, BorderLayout.CENTER);
        add(panelContenedorSuperior, BorderLayout.NORTH);
        add(panelContenedorInferior, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new Ventana2();
    }
}