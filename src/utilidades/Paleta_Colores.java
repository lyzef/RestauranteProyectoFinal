package utilidades;

import java.awt.Color;

public enum Paleta_Colores {
	FONDO(new Color(15, 23, 42)),
	CONTENEDORES(new Color(30, 41, 59)),
	ACENTO_PRIMARIO(new Color(59, 130, 246)),
	TEXTO_PRINCIPAL(new Color(255,255,255)),
	TEXTO_SECUNDARIO(new Color (148, 163, 184)),
	EXITO(new Color (16, 185, 129)),
	ATENCION(new Color (245, 158, 11)),
	URGENTE(new Color(239, 68, 68 )),
	HEADER_TABLA(new Color(51, 65, 85));
	
	private final Color color;
	
	Paleta_Colores(Color color){
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
}
