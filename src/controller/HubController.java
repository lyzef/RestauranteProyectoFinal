package controller;

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
import views.Admin.MenuAdminView;

public class HubController {
	Hub view;
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
	private PlatilloService menuService;
	
	public HubController(Hub hub) {
		this.view = hub;
		addListeners();
		crearServicios();
		showDashboard();
	}
	
	public void crearServicios() {
		componenteService = new ComponenteService();
		estructuraRecetaService = new EstructuraRecetaService();
		categoriaService = new CategoriaService();
		
		calculoRecetaService = new CalculoRecetaService(componenteService, estructuraRecetaService);
		inventarioService = new InventarioService(componenteService, estructuraRecetaService);
		menuService = new PlatilloService(categoriaService);
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
		view.showView(Hub.DASHBOARD);
	}
	
	private void showUsers() {
		//Evita crear otro user controller
		if(userController == null) {
			userController = new UserController(view.getUserPanel());
		}
		view.showView(Hub.USERS);
		
	}
	
	private void showInventory() {
		if(inventarioController == null) {
			inventarioController = new InventarioController(view.getInventarioPanel(),componenteService,inventarioService);
		}
		
		//Cargar datos
		view.showView(Hub.INVENTORY);
		
	}
	
	private void showRecipes() {
		if(recipeController == null) {
			recipeController = new RecipeController(view.getRecipePanel(),estructuraRecetaService,componenteService,calculoRecetaService);
		}
		view.showView(Hub.RECIPE);
	}
	
	private void showMenu() {
		if(menuController == null) {
			menuController = new MenuAdminController(view.getMenuAdminPanel(),categoriaService,menuService,
					componenteService);
		}
		view.showView(Hub.MENU);
	}

}