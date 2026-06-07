package controller;

import views.Login;
import views.Admin.HubFrame;
import views.AutoVenta.HubVentaFrame;
import views.Cocina.VistaCocinero;
import views.Dialog.UserFormDialog;
import excepciones.InvalidContraseña;
import excepciones.InvalidUser;
import excepciones.invalidInput;
import models.User;
import repository.LoginRepository;
import repository.UserRepository;
import services.CalculoRecetaService;
import services.CarritoService;
import services.CategoriaService;
import services.ComponenteService;
import services.EstructuraRecetaService;
import services.InventarioService;
import services.LoadService;
import services.MenuCatalogoService;
import services.PlatilloService;
import services.VentaProductoService;
import services.VentaService;
import utilidades.SessionUtilities;
import utilidades.ValidadorEntradasTexto;

import javax.swing.*;

import controller.admin.HubAdminController;
import controller.autoVenta.HubVentaController;
import controller.cocina.CocinaController;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class LoginController {

    private Login view;
    private LoginRepository repository;
    
    //Servicios
  	private ComponenteService componenteService;
  	private EstructuraRecetaService estructuraRecetaService;
  	private CalculoRecetaService calculoRecetaService;
  	private InventarioService inventarioService;
  	private VentaService ventaService;
  	private CategoriaService categoriaService;
  	private PlatilloService platilloService;
  	private VentaProductoService ventaProductoService;
  	private LoadService loadService;
  	
  	//Para venta
  	private MenuCatalogoService menuCatalogoService;
  	private CarritoService carritoService;
  	
  	
    public LoginController(Login view) {
        this.view = view;
        this.repository = new LoginRepository();
        crearServicios();
        initController();
    }
    
    public void cerrarApp() {
    	//Desloguear
    	if(SessionUtilities.isLoggedIn()) {
	    	new LoginRepository().setSesionActiva(SessionUtilities.getCurrentUser(), false);
    	}
    	view.dispose();
    }
    
    public void abrirLogin() {
    	//Desloguear
    	if(SessionUtilities.isLoggedIn()) {
	    	new LoginRepository().setSesionActiva(SessionUtilities.getCurrentUser(), false);
    	}
    	view.setVisible(true);
    	view.setEnabled(true);
    }

    private void cerrarLogin() {
    	view.getEntradaCorreo().setText("");
    	view.getEntradaContrasena().setText("");
    	view.setVisible(false);
    	view.setEnabled(false);
    }
    
    private void initController() {
        view.getBotonEntrar().addActionListener(e -> validarLogin()
        		
        );
    }

    private void reinicarMensajesError() {
        view.getLabelAdvertenciaCorreo().setVisible(false);
        view.getLabelAdvertenciaContrasena().setVisible(false);
    }
    
    public void crearServicios() {
    	//Primero los servicios padre
		componenteService = new ComponenteService();
		estructuraRecetaService = new EstructuraRecetaService();
		categoriaService = new CategoriaService();
		carritoService = new CarritoService(componenteService);
		ventaService = new VentaService();
		calculoRecetaService = new CalculoRecetaService(componenteService, estructuraRecetaService);
		inventarioService = new InventarioService(componenteService, estructuraRecetaService);
		platilloService = new PlatilloService(categoriaService);
		loadService = new LoadService(componenteService, categoriaService, estructuraRecetaService, inventarioService, platilloService);
		menuCatalogoService = new MenuCatalogoService(componenteService, platilloService, categoriaService, estructuraRecetaService);
		ventaProductoService = new VentaProductoService(inventarioService, carritoService, loadService, componenteService, ventaService);
		
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
            repository.setSesionActiva(user, true);
        } catch (InvalidUser ex) {
            view.getLabelAdvertenciaCorreo().setText(ex.getMessage());
            view.getLabelAdvertenciaCorreo().setVisible(true);
            return;
        } catch (InvalidContraseña ex) {
            view.getLabelAdvertenciaContrasena().setText(ex.getMessage());
            view.getLabelAdvertenciaContrasena().setVisible(true);
            return;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(
                    view, 
                    e.getMessage(), 
                    "Error", 
                    JOptionPane.WARNING_MESSAGE
                );
        	return;
		}
        
        SessionUtilities.login(user);
        JOptionPane.showMessageDialog(view, "Acceso concedido", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
        
        if(SessionUtilities.getRol().equals("admin")) {
        	new HubAdminController(new HubFrame(), this, componenteService, estructuraRecetaService,
        			calculoRecetaService, inventarioService, categoriaService, platilloService,ventaService);
        	cerrarLogin();
        } else if(SessionUtilities.getRol().equals("cajero")) {
        	new HubVentaController(new HubVentaFrame(),menuCatalogoService,carritoService,ventaProductoService,categoriaService,this);
        	cerrarLogin();
        } else if(SessionUtilities.getRol().equals("cocinero")) {
        	new CocinaController(new VistaCocinero(), ventaService);
        	cerrarLogin();
        }else {
        	JOptionPane.showMessageDialog(
                    view, 
                    "Acceso desconocido", 
                    "Error", 
                    JOptionPane.WARNING_MESSAGE
                );
        	return;
        }
        
    }

    private void validarCredenciales() throws InvalidUser, InvalidContraseña {
        String correo = view.getEntradaCorreo().getText().trim();
        String pass = new String(view.getEntradaContrasena().getPassword());
        
        try {
			ValidadorEntradasTexto.validarContenido(correo, "CORREO");
		} catch (invalidInput e) {
			throw new InvalidUser("Escribe el correo");
		}
        
        if (pass.isEmpty()) throw new InvalidContraseña("Escribe la contraseña");
    }
}