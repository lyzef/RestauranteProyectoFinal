package controller;

import views.Login;
import views.Hub;
import views.formulario.FormularioRegistro;
import excepciones.InvalidContraseña;
import excepciones.InvalidUser;
import models.User;
import repository.UserRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginController {

    private Login view;
    private UserRepository repo;

    public LoginController(Login view) {
        this.view = view;
        this.repo = new UserRepository();
        initController();
    }

    private void initController() {
        view.getBotonEntrar().addActionListener(e -> validarLogin()
        		
        );

        view.getBotonRegistrar().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                view.getBotonRegistrar().setForeground(Color.black);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                view.getBotonRegistrar().setForeground(new Color(170, 204, 0));
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                registro();
            }
        });
    }

    private void registro() {
        new FormularioController(new FormularioRegistro());
        view.dispose();
    }

    private void reinicarMensajesError() {
        view.getLabelAdvertenciaCorreo().setVisible(false);
        view.getLabelAdvertenciaContrasena().setVisible(false);
    }

    private void validarLogin() {
        reinicarMensajesError();
        try {
            validarCredenciales();
            JOptionPane.showMessageDialog(view, "Acceso concedido", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
            new HubController(new Hub());
            view.dispose();
        } catch (InvalidUser ex) {
            view.getLabelAdvertenciaCorreo().setText(ex.getMessage());
            view.getLabelAdvertenciaCorreo().setVisible(true);
        } catch (InvalidContraseña ex) {
            view.getLabelAdvertenciaContrasena().setText(ex.getMessage());
            view.getLabelAdvertenciaContrasena().setVisible(true);
        }
        
        
    }

    private void validarCredenciales() throws InvalidUser, InvalidContraseña {
        String correo = view.getEntradaCorreo().getText().trim();
        String pass = new String(view.getEntradaContrasena().getPassword());

        if (correo.isEmpty()) throw new InvalidUser("Escribe el correo");
        if (pass.isEmpty()) throw new InvalidContraseña("Escribe la contraseña");

        List<User> usuarios = repo.getAllUsers();
        boolean encontrado = false;
        for (User u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new InvalidUser("Usuario no encontrado");
        }
    }
}