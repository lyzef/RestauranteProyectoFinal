package controller;

import views.FormularioDialog;
import views.Hub;
import views.Login;
import excepciones.InvalidContraseña;
import excepciones.InvalidUser;
import models.User;
import repository.LoginRepository;
import repository.UserRepository;
import utilidades.Session;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginController {

    private Login view;
    private LoginRepository repository;

    public LoginController(Login view) {
        this.view = view;
        this.repository = new LoginRepository();
        initController();
    }

    private void initController() {
        view.getBotonEntrar().addActionListener(e -> validarLogin()
        		
        );
        
        /*
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
        */
    }

    private void reinicarMensajesError() {
        view.getLabelAdvertenciaCorreo().setVisible(false);
        view.getLabelAdvertenciaContrasena().setVisible(false);
    }

    private void validarLogin() {
        reinicarMensajesError();
        User user;
        try {
            validarCredenciales();
            user = repository.login(view.getEntradaCorreo().getText(),new String(view.getEntradaContrasena().getPassword()));
            
            if(user == null) {
            	throw new InvalidUser("Correo invalido");
    		}
            
        } catch (InvalidUser ex) {
            view.getLabelAdvertenciaCorreo().setText(ex.getMessage());
            view.getLabelAdvertenciaCorreo().setVisible(true);
            return;
        } catch (InvalidContraseña ex) {
            view.getLabelAdvertenciaContrasena().setText(ex.getMessage());
            view.getLabelAdvertenciaContrasena().setVisible(true);
            return;
        }
        
        Session.login(user);
        JOptionPane.showMessageDialog(view, "Acceso concedido", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
        
        if(Session.getRol().equals("admin")) {
        	new HubController(new Hub());
            view.dispose();
        }
        
        
        
    }

    private void validarCredenciales() throws InvalidUser, InvalidContraseña {
        String correo = view.getEntradaCorreo().getText().trim();
        String pass = new String(view.getEntradaContrasena().getPassword());

        if (correo.isEmpty()) throw new InvalidUser("Escribe el correo");
        if (pass.isEmpty()) throw new InvalidContraseña("Escribe la contraseña");
    }
}