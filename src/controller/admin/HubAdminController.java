package controller.admin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import models.User;
import repository.LoginRepository;
import repository.UserRepository;
import services.CalculoRecetaService;
import services.CategoriaService;
import services.ComponenteService;
import services.EstructuraRecetaService;
import services.InventarioService;
import services.PlatilloService;
import tableFormat.UserTableFormat;
import utilidades.Session;
import views.*;
import views.Admin.HubFrame;
import views.Admin.MenuAdminView;

public class HubAdminController {
	HubFrame view;
	Login login;
	private UserController userController;
	private InventarioController inventarioController;
	private RecipeController recipeController;
	private MenuAdminController menuController;
	
	//Servicios
	private ComponenteService componenteService;
	private EstructuraRecetaService estructuraRecetaService;
	private CalculoRecetaService calculoRecetaService;
	private InventarioService inventarioService;
	private CategoriaService categoriaService;
	private PlatilloService platilloService;
	
	
	
	public HubAdminController(HubFrame view, Login login, ComponenteService componenteService,
			EstructuraRecetaService estructuraRecetaService, CalculoRecetaService calculoRecetaService,
			InventarioService inventarioService, CategoriaService categoriaService, PlatilloService platilloService) {
		this.view = view;
		this.login = login;
		this.componenteService = componenteService;
		this.estructuraRecetaService = estructuraRecetaService;
		this.calculoRecetaService = calculoRecetaService;
		this.inventarioService = inventarioService;
		this.categoriaService = categoriaService;
		this.platilloService = platilloService;
		addListeners();
		showDashboard();
	}
	
	
	
	public void addListeners() {
		view.getBotonUsuarios().addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showUsers();
		    }
		});
		
		view.getBotonDashboard().addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showDashboard();
		    }
		});
		
		view.getBotonInventario().addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showInventory();
		    }
		});
		
		view.getBotonRecipe().addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showRecipes();
		    }
		});
		
		view.getBotonMenu().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showMenu();
		    }
		});
		
		view.getBarraNavegacion().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.abrirBarra();
		    }
		});
		
		
		view.getBotonLogOut().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Session.isLoggedIn()) {
			    	new LoginRepository().setSesionActiva(Session.getCurrentUser(), false);
		    	}
		    	view.dispose();
		    }
		});
		
		view.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	if(Session.isLoggedIn()) {
			    	new LoginRepository().setSesionActiva(Session.getCurrentUser(), false);
		    	}
		    	view.dispose();
		    }
		});		
	}
	
	private void showDashboard() {
		view.showView(HubFrame.DASHBOARD);
	}
	
	private void showUsers() {
		//Evita crear otro user controller
		if(userController == null) {
			userController = new UserController(view.getUserPanel());
		}
		view.showView(HubFrame.USERS);
		
	}
	
	private void showInventory() {
		if(inventarioController == null) {
			inventarioController = new InventarioController(view.getInventarioPanel(),componenteService,inventarioService);
		}
		
		//Cargar datos
		view.showView(HubFrame.INVENTORY);
		
	}
	
	private void showRecipes() {
		if(recipeController == null) {
			recipeController = new RecipeController(view.getRecipePanel(),estructuraRecetaService,componenteService,calculoRecetaService);
		}
		view.showView(HubFrame.RECIPE);
	}
	
	private void showMenu() {
		if(menuController == null) {
			menuController = new MenuAdminController(view.getMenuAdminPanel(),categoriaService,platilloService,
					componenteService);
		}
		view.showView(HubFrame.MENU);
	}

}