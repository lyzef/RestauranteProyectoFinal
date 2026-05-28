package models;

public class Estructura_receta {
	private int parent_id;
	private int child_id;
	private double cantidad;
	private boolean es_opcional;
	
	public Estructura_receta() {
		
	}

	public Estructura_receta(int parent_id, int child_id, double cantidad, boolean es_opcional) {
		super();
		this.parent_id = parent_id;
		this.child_id = child_id;
		this.cantidad = cantidad;
		this.es_opcional = es_opcional;
	}

	public int getParent_id() {
		return parent_id;
	}

	public int getChild_id() {
		return child_id;
	}

	public double getCantidad() {
		return cantidad;
	}

	public boolean isEs_opcional() {
		return es_opcional;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public void setChild_id(int child_id) {
		this.child_id = child_id;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public void setEs_opcional(boolean es_opcional) {
		this.es_opcional = es_opcional;
	}
	
	
}
