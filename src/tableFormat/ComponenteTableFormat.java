package tableFormat;

import java.util.Comparator;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import ca.odell.glazedlists.gui.AdvancedTableFormat;
import ca.odell.glazedlists.gui.TableFormat;
import models.ComponenteIngredienteReceta;
import models.User;

public class ComponenteTableFormat implements AdvancedTableFormat<ComponenteIngredienteReceta>{
	
	private final String[] columns = {
	        "Nombre",
	        "Tipo",
	        "Estado",
	        "Stock",
	        "Stock para bloqueo",
	        "Receta",
	        "Inventariable",
	    };
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public Object getColumnValue(ComponenteIngredienteReceta c, int column) {
        switch(column) {
            case 0: return c.getNombre();
            case 1: return c.getTipoComponente();
            case 2: return estadoComponente(c);
            case 3: return c.getStockActual() + " " + c.getUnidadMedida();
            case 4: return c.getStockMinimoBloqueo() + " " + c.getUnidadMedida();
            case 5: return c.isReceta() ? "Verdadero" : "Falso";
            case 6: return c.isInventariable() ? "Verdadero" : "Falso";
            default: return null;
        }
    }
	
	private String estadoComponente(ComponenteIngredienteReceta c) {
		if(c.isDisponibilidadManual() == false) {
			return "Bloqueado";
		}
		
		if(c.getStockActual() < c.getStockMinimoBloqueo()) {
			return "Bloqueado";
		}
		
		if(c.getStockActual() < c.getStockMinimoAlerta()) {
			return "Bajo stock";
		}
		
		return "Activo";
	}

	@Override
	public Class getColumnClass(int column) {
		switch (column) {
        case 0: return String.class;    
        case 1: return String.class;   
        case 2: return String.class;      
        case 3: return String.class;      
        case 4: return String.class;     
        case 5: return String.class;     
        case 6: return String.class;   
        default: return Object.class;
    }
	}

	@Override
	public Comparator<?> getColumnComparator(int column) {
	    switch (column) {
	        case 0: 
	            return null;
	            
	        case 1: 
	        	return null;
	            
	        case 2: 
	        	 return (Comparator<ComponenteIngredienteReceta>) (c1, c2) -> {
		                boolean c1Critico = c1.getStockActual() <= c1.getStockMinimoBloqueo();
		                boolean c2Critico = c2.getStockActual() <= c2.getStockMinimoBloqueo();
		                
		                if (c1Critico && !c2Critico) return -1; 
		                if (!c1Critico && c2Critico) return 1; 
		                return Double.compare(c1.getStockActual(), c2.getStockActual());
		            };
	            
	        case 3: 
	        	return null;
	            
	        case 4:
	            return null;
	            
	        case 5: 
	        	return null;
	            
	        default:
	            return null; 
	    }
	}
}
