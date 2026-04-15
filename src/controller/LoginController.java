package controller;

import views.Login;
import views.Hub;
import views.formulario.FormularioRegistroParte1;
import excepciones.InvalidContraseña;
import excepciones.InvalidUser;
import javax.swing.*;
import java.awt.*;

public class LoginController {

    private Login view;

    public LoginController(Login view) {
        this.view = view;
        initController();
    }

    private void initController() {
    	view.getBotonEntrar().addActionListener(new java.awt.event.ActionListener() {
    	    @Override
    	    public void actionPerformed(java.awt.event.ActionEvent e) {
    	        validarLogin();
    	    }
    	});
        view.getBotonRegistrar().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent e) {
                view.getBotonRegistrar().setForeground(Color.black);
            }
            public void mouseEntered(java.awt.event.MouseEvent e) {
                view.getBotonRegistrar().setForeground(new Color(170,204,0));
            }
            public void mouseClicked(java.awt.event.MouseEvent e) {
                registro();
            }
        });
    }

    private void registro() {
        new FormularioController(new FormularioRegistroParte1());
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
            JOptionPane.showMessageDialog(
                    view,
                    "Felicidades sabes escribir!",
                    "Bienvenido....",
                    JOptionPane.INFORMATION_MESSAGE
            );
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
        if(view.getEntradaCorreo().getText().isBlank()) {
            throw new InvalidUser("Escribe el correo");
        }
        if(view.getEntradaCorreo().getText().length() < 5) {
            throw new InvalidUser("Escribe un correo valido");
        }
        if (!(view.getEntradaContrasena().getPassword().length > 0)) {
            throw new InvalidContraseña("Escribe la contraseña");
        }
    }
}

