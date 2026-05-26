package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.ComponenteIngredienteReceta;
import models.MovimientoInventario;
import models.MovimientoInventario.tipoMovimiento;
import models.User;

public class InventarioRepository {
	
	public void saveComponente(ComponenteIngredienteReceta componente) throws Exception {
	    String sqlComponente = "INSERT INTO componentes (nombre, es_receta, tipo_componente, unidad_medida, "
	            + "costo_unitario, calorias_por_unidad, stock_minimo_bloqueo, stock_minimo_alerta, "
	            + "disponibilidad_manual, es_inventariable) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    // Las conexiones y statements se declaran dentro de los paréntesis del try
	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sqlComponente)) {
	        
	        ps.setString(1, componente.getNombre());
	        ps.setBoolean(2, componente.isReceta());
	        ps.setString(3, componente.getTipoComponente());
	        ps.setString(4, componente.getUnidadMedida().name()); 
	        ps.setDouble(5, componente.getCostoUnitario());
	        ps.setDouble(6, componente.getCaloriasPorUnidad());
	        ps.setDouble(7, componente.getStockMinimoBloqueo());
	        ps.setDouble(8, componente.getStockMinimoAlerta());
	        ps.setBoolean(9, componente.isDisponibilidadManual());
	        ps.setBoolean(10, componente.isInventariable());

	        ps.executeUpdate();
	        
	    } catch (SQLException e) {
	        throw new Exception("Componente NO guardado en database ... " + e.getMessage(), e);
	    }
	}
	
	public List<ComponenteIngredienteReceta> getComponentes() throws Exception {
		String sql = "SELECT id, nombre, es_receta, tipo_componente, unidad_medida, "
	               + "costo_unitario, calorias_por_unidad, stock_actual, stock_minimo_bloqueo, "
	               + "stock_minimo_alerta, disponibilidad_manual, es_inventariable, categoria_id "
	               + "FROM componentes";
		return ejecutarConsultaComponentes(sql);
	}
	
	public boolean updateComponente(ComponenteIngredienteReceta componente) throws Exception {
	    String sqlUpdate = "UPDATE componentes SET "
	            + "nombre = ?, "
	            + "es_receta = ?, "
	            + "tipo_componente = ?, "
	            + "unidad_medida = ?, "
	            + "costo_unitario = ?, "
	            + "calorias_por_unidad = ?, "
	            + "stock_minimo_bloqueo = ?, "
	            + "stock_minimo_alerta = ?, "
	            + "disponibilidad_manual = ?, "
	            + "es_inventariable = ? "
	            + "WHERE id = ?";

	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {
	        
	        ps.setString(1, componente.getNombre());
	        ps.setBoolean(2, componente.isReceta());
	        ps.setString(3, componente.getTipoComponente());
	        ps.setString(4, componente.getUnidadMedida().name());
	        ps.setDouble(5, componente.getCostoUnitario());
	        ps.setDouble(6, componente.getCaloriasPorUnidad());
	        ps.setDouble(7, componente.getStockMinimoBloqueo());
	        ps.setDouble(8, componente.getStockMinimoAlerta());
	        ps.setBoolean(9, componente.isDisponibilidadManual());
	        ps.setBoolean(10, componente.isInventariable());
	        ps.setInt(11, componente.getId());  // WHERE id = ?

	        int filasAfectadas = ps.executeUpdate();
	        
	        if (filasAfectadas == 0) {
	            throw new Exception("No se encontró el componente con ID: " + componente.getId());
	        }
	        return true;
	    } catch (SQLException e) {
	        throw new Exception("Error al actualizar el componente ... " + e.getMessage(), e);
	    }		
	
	}
	
	public void deleteComponente(int id) throws Exception {
	    String sqlDelete = "DELETE FROM componentes WHERE id = ?";

	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sqlDelete)) {
	        
	        ps.setInt(1, id);
	        
	        int filasAfectadas = ps.executeUpdate();
	        
	        if (filasAfectadas == 0) {
	            throw new Exception("No se encontró el componente con ID: " + id);
	        }
	        
	    } catch (SQLException e) {
	        throw new Exception("Error al eliminar el componente ... " + e.getMessage(), e);
	    }
	}
	
	 public int getItemsConBajoStock() throws Exception {
		 String sql = "SELECT * FROM componentes WHERE stock_actual < stock_minimo_alerta";
		 return ejecutarConsultaComponentes(sql).size();
	 }
	 
	 
	 public void saveMovimientoInventario(MovimientoInventario movimiento) throws Exception {
		    String sqlComponente = "INSERT INTO movimientos_inventario " +
                    "(componente_id, tipo_movimiento, cantidad, costo_movimiento, motivo) " +
                    "VALUES (?, ?, ?, ?, ?)";

		    // Las conexiones y statements se declaran dentro de los paréntesis del try
		    try (Connection connection = DatabaseConnection.getConnection();
		         PreparedStatement ps = connection.prepareStatement(sqlComponente)) {
		        
		        ps.setInt(1, movimiento.getComponente_id());
		        ps.setString(2,movimiento.getTipo_movimiento().toString());
		        ps.setDouble(3, movimiento.getCantidad());
		        ps.setDouble(4, movimiento.getCosto_movimiento()); 
		        ps.setString(5, movimiento.getMotivo());

		        ps.executeUpdate();
		        
		    } catch (SQLException e) {
		        throw new Exception("Componente NO guardado en database ... " + e.getMessage(), e);
		    }
	}
	 
	 public List<MovimientoInventario> getMovimientosInventario() throws Exception {
	        List<MovimientoInventario> lista = new ArrayList<>();
	        String sql = "SELECT m.*, c.nombre FROM movimientos_inventario m LEFT JOIN "
	                + "componentes c ON m.componente_id = c.id";
	        
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement ps = connection.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                lista.add(crearMovimiento(rs));
	            }
	        } catch (SQLException e) {
	            throw new Exception("Error al consultar movimiento: " + e.getMessage(), e);
	        }
	        
	        return lista;
	    }
	
	 private List<ComponenteIngredienteReceta> ejecutarConsultaComponentes(String sql) throws Exception {
	        List<ComponenteIngredienteReceta> lista = new ArrayList<>();
	        
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement ps = connection.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                lista.add(crearComponente(rs));
	            }
	        } catch (SQLException e) {
	            throw new Exception("Error al consultar componentes: " + e.getMessage(), e);
	        }
	        
	        return lista;
	    }
	 
	 
	 	//Ejemplo de uso "SELECT * FROM componentes WHERE categoria_id = ?"; donde el ? es el unico parametro
	    private List<ComponenteIngredienteReceta> ejecutarConsultaComponentesConParametro(
	            String sql, Object parametro) throws Exception {
	        List<ComponenteIngredienteReceta> lista = new ArrayList<>();
	        
	        try (Connection connection = DatabaseConnection.getConnection();
	             PreparedStatement ps = connection.prepareStatement(sql)) {

	            if (parametro instanceof String) {
	                ps.setString(1, (String) parametro);
	            } else if (parametro instanceof Integer) {
	                ps.setInt(1, (Integer) parametro);
	            }

	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    lista.add(crearComponente(rs));
	                }
	            }
	        } catch (SQLException e) {
	            throw new Exception("Error al consultar componentes: " + e.getMessage(), e);
	        }
	        
	        return lista;
	    }

	    private ComponenteIngredienteReceta crearComponente(ResultSet rs) throws SQLException {
	        ComponenteIngredienteReceta comp = new ComponenteIngredienteReceta();
	        
	        comp.setId(rs.getInt("id"));
	        comp.setNombre(rs.getString("nombre"));
	        comp.setEsReceta(rs.getBoolean("es_receta"));
	        comp.setTipoComponente(rs.getString("tipo_componente"));
	        comp.setCostoUnitario(rs.getDouble("costo_unitario"));
	        comp.setCaloriasPorUnidad(rs.getDouble("calorias_por_unidad"));
	        comp.setStockActual(rs.getDouble("stock_actual"));
	        comp.setStockMinimoBloqueo(rs.getDouble("stock_minimo_bloqueo"));
	        comp.setStockMinimoAlerta(rs.getDouble("stock_minimo_alerta"));
	        comp.setDisponibilidadManual(rs.getBoolean("disponibilidad_manual"));
	        comp.setEsInventariable(rs.getBoolean("es_inventariable"));
	        comp.setCategoriaId(rs.getInt("categoria_id"));
	        
	        String unidadStr = rs.getString("unidad_medida");
	        if (unidadStr != null) {
	            try {
	                comp.setUnidadMedida(ComponenteIngredienteReceta.Unidad.valueOf(unidadStr));
	            } catch (IllegalArgumentException e) {
	                System.err.println("Unidad de medida inválida: " + unidadStr);
	                comp.setUnidadMedida(null);
	            }
	        }
	        
	        return comp;
	    }
	    
	    private MovimientoInventario crearMovimiento (ResultSet rs) throws SQLException {
	    	MovimientoInventario comp = new MovimientoInventario();
	        
	        comp.setId(rs.getInt("id"));
	        comp.setComponente_id(rs.getInt("componente_id"));
	        comp.setComponente_nombre(rs.getString("nombre"));
	        
	        comp.setCantidad(rs.getDouble("cantidad"));
	        comp.setCosto_movimiento(rs.getDouble("costo_movimiento"));
	        comp.setMotivo(rs.getString("motivo"));
	        comp.setFecha(rs.getString("fecha_hora"));
	        
	        
            try {
    	        comp.setTipo_movimiento(tipoMovimiento.fromString(rs.getString("tipo_movimiento")));
            } catch (IllegalArgumentException e) {
                System.err.println("Tipo de movimiento invalido " + rs.getString("tipo_movimiento") );
                comp.setTipo_movimiento(null);
            }
	        
	        
	        return comp;
	    }
	
}
