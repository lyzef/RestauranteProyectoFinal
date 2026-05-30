package services;

import java.util.List;

import models.ComponenteIngredienteReceta;
import models.Estructura_receta;

public class CalculoRecetaService {

    private ComponenteService componenteService;
    private EstructuraRecetaService estructuraService;

    public CalculoRecetaService(ComponenteService compService, EstructuraRecetaService estService) {
        this.componenteService = compService;
        this.estructuraService = estService;
    }

    public double calcularCaloriasTotales(int idReceta) {
        ComponenteIngredienteReceta receta = componenteService.getComponenteById(idReceta);
        
        //Si solo es una rama devuelve sus calorias
        if (!receta.isReceta()) {
            return receta.getCaloriasPorUnidad();
        }

        double caloriasTotales = 0.0;
        
        //Si es una receta devuelve las calorias totales
        List<Estructura_receta> hijos = estructuraService.getHijosByID(idReceta);

        for (Estructura_receta hijo : hijos) {
        	// Metodo recursivo
            double caloriasHijo = calcularCaloriasTotales(hijo.getChild_id());
            
            // Calorias segun la cantidad
            caloriasTotales += (caloriasHijo * hijo.getCantidad());
        }

        return caloriasTotales;
    }
    
  //Para hijos en memoria
    public double calcularCaloriasTotales(List<Estructura_receta> hijos) {
    	double caloriasTotales = 0.0;
    	for (Estructura_receta hijo : hijos) {
        	// Metodo recursivo
            double caloriasHijo = calcularCaloriasTotales(hijo.getChild_id());
            
            // Calorias segun la cantidad
            caloriasTotales += (caloriasHijo * hijo.getCantidad());
        }

        return caloriasTotales;
    }
    
    public double calcularCostoTotal(int idReceta) {
        ComponenteIngredienteReceta receta = componenteService.getComponenteById(idReceta);
        
        //Si solo es una rama devuelve sus calorias
        if (!receta.isReceta()) {
            return receta.getCostoUnitario();
        }

        double costoTotal = 0.0;
        
        //Si es una receta devuelve las calorias totales
        List<Estructura_receta> hijos = estructuraService.getHijosByID(idReceta);

        for (Estructura_receta hijo : hijos) {
        	// Metodo recursivo
            double costoHijo = calcularCostoTotal(hijo.getChild_id());
            
            // Costo segun la cantidad
            costoTotal += (costoHijo * hijo.getCantidad());
        }

        return costoTotal;
    }
    
    //Para hijos en memoria
    public double calcularCostoTotal(List<Estructura_receta> hijos) {
    	double costoTotal = 0.0;
    	for (Estructura_receta hijo : hijos) {
        	// Metodo recursivo
            double costoHijo = calcularCostoTotal(hijo.getChild_id());
            
            // Costo segun la cantidad
            costoTotal += (costoHijo * hijo.getCantidad());
        }
        return costoTotal;
    }
}