package utilidades;

import java.awt.Font;

public class AppFont {
	
private static Font base = new Font("Arial", Font.PLAIN, 14);
	
	
	public static Font normal() {
		return base.deriveFont(14f);
	}
	
	public static Font bold() {
		return base.deriveFont(Font.BOLD, 14f);
	}
	
	public static Font small() {
		return base.deriveFont(10f);
	}
	
	public static Font title() {
		return base.deriveFont(Font.BOLD, 20f);
	}
}
