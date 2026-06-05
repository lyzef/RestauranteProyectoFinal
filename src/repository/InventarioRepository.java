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
import models.EstructuraReceta;
import models.MovimientoInventario;
import models.MovimientoInventario.tipoMovimiento;
import models.User;

public class InventarioRepository {
	
	public int saveComponente(ComponenteIngredienteReceta componente) throws Exception {
	    String sqlComponente = "INSERT INTO componentes (nombre, es_receta, tipo_componente, unidad_medida, "
	            + "costo_unitario, calorias_por_unidad, stock_minimo_bloqueo, stock_minimo_alerta, "
	            + "disponibilidad_manual, es_inventariable) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sqlComponente, java.sql.Statement.RETURN_GENERATED_KEYS)) {
	        
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
	        
	        try (java.sql.ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                long idGenerado = generatedKeys.getLong(1);
	                return (int) idGenerado; 
	            } else {
	                throw new SQLException("Error al guardar componente, no se obtuvo el ID generado.");
	            }
	        }
	        
	    } catch (SQLException e) {
	        throw new Exception("Componente NO guardado en database ... " + e.getMessage(), e);
	    }
	}
	
	public List<ComponenteIngredienteReceta> getComponentes() throws Exception {
		String sql = "SELECT id, nombre, es_receta, tipo_componente, unidad_medida, "
	               + "costo_unitario, calorias_por_unidad, stock_actual, stock_minimo_bloqueo, "
	               + "stock_minimo_alerta, disponibilidad_manual, es_inventariable "
	               + "FROM componentes";
		return ejecutarConsultaComponentes(sql);
	}
	
	public ComponenteIngredienteReceta getComponenteById(int id) throws Exception {
	    String sql = "SELECT id, nombre, es_receta, tipo_componente, unidad_medida, "
	               + "costo_unitario, calorias_por_unidad, stock_actual, stock_minimo_bloqueo, "
	               + "stock_minimo_alerta, disponibilidad_manual, es_inventariable "
	               + "FROM componentes WHERE id = ?";
	    
	    List<ComponenteIngredienteReceta> resultados = ejecutarConsultaComponentesConParametro(sql, id);
	    
	    if (resultados.isEmpty()) {
	        throw new Exception("No se encontró el componente con ID: " + id);
	    }
	    
	    return resultados.get(0);
	}
	
	public void  updateComponente(ComponenteIngredienteReceta componente) throws Exception {
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
	    } catch (SQLException e) {
	        throw new Exception("Error al actualizar el componente ... " + e.getMessage(), e);
	    }		
	
	}

	public List<ComponenteIngredienteReceta> getRecetas() throws Exception {
		String sql = "SELECT id, nombre, es_receta, tipo_componente, unidad_medida, "
	               + "costo_unitario, calorias_por_unidad, stock_actual, stock_minimo_bloqueo, "
	               + "stock_minimo_alerta, disponibilidad_manual, es_inventariable "
	               + "FROM componentes";
		return ejecutarConsultaComponentes(sql);
	}

	public int getItemsConBajoStock() throws Exception {
		 String sql = "SELECT * FROM componentes WHERE stock_actual < stock_minimo_alerta";
		 return ejecutarConsultaComponentes(sql).size();
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

	public void saveEstructuraReceta(int parentId, List<EstructuraReceta> ingredientes) throws Exception {
	    
	    String sqlDelete = "DELETE FROM estructura_receta WHERE parent_id = ?";
	    String sqlInsert = "INSERT INTO estructura_receta (parent_id, child_id, cantidad, es_opcional) VALUES (?, ?, ?, ?)";
	    
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        
	        connection.setAutoCommit(false);

	        try (PreparedStatement stmtDelete = connection.prepareStatement(sqlDelete);
	             PreparedStatement stmtInsert = connection.prepareStatement(sqlInsert)) {

	            stmtDelete.setInt(1, parentId);
	            stmtDelete.executeUpdate();

	            for (EstructuraReceta ingrediente : ingredientes) {
	                stmtInsert.setInt(1, parentId); 
	                stmtInsert.setInt(2, ingrediente.getChild_id());
	                stmtInsert.setDouble(3, ingrediente.getCantidad());
	                stmtInsert.setBoolean(4, ingrediente.isEs_opcional());
	                
	                stmtInsert.addBatch(); // Empaqueta el insert
	            }
	            
	            stmtInsert.executeBatch();

	            connection.commit();

	        } catch (SQLException e) {
	        	//Si no jalo se reinica los cambios

	            connection.rollback(); 
	            e.printStackTrace();
	            throw new Exception("Error al ejecutar las consultas. Aplicando Rollback...");
	            
	        } finally {
	            // Regresamos a estado original
	            connection.setAutoCommit(true);
	        }

	    } catch (SQLException e) {
	    	throw new Exception("Error al guardar el componente");
	    }
	}
	
	public void eliminarEstructuraReceta(int parentId) throws Exception {
	    
	    String sqlDelete = "DELETE FROM estructura_receta WHERE parent_id = ?";

	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement stmtDelete = connection.prepareStatement(sqlDelete)) {

	        stmtDelete.setInt(1, parentId);
	        
	        int filasAfectadas = stmtDelete.executeUpdate();
	        
	        System.out.println("Se eliminaron " + filasAfectadas + " ingredientes de la receta.");

	    } catch (SQLException e) {
	    	throw new Exception("Error al eliminar la estructura receta");
	    }
	}
	
	public List<EstructuraReceta> getTodasLasEstructuras() {
	    
	    List<EstructuraReceta> listaCompleta = new ArrayList<>();
	    String sql = "SELECT parent_id, child_id, cantidad, es_opcional FROM estructura_receta";

	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        
	        while (rs.next()) {
	            int parent = rs.getInt("parent_id");
	            int child = rs.getInt("child_id");
	            double cantidad = rs.getDouble("cantidad");
	            boolean opcional = rs.getBoolean("es_opcional");
	            
	            EstructuraReceta ingrediente = new EstructuraReceta(parent, child, cantidad, opcional);
	            listaCompleta.add(ingrediente);
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al cargar la tabla completa de estructura_receta...");
	        e.printStackTrace();
	    }

	    return listaCompleta;
	}
	
	public int saveMovimientoInventario(MovimientoInventario movimiento) throws Exception {
		 	//System.out.println("Llamada al repo desde:");
		    //Thread.dumpStack();
		 
		    String sqlComponente = "INSERT INTO movimientos_inventario " +
                    "(componente_id, tipo_movimiento, cantidad, costo_movimiento, motivo) " +
                    "VALUES (?, ?, ?, ?, ?)";
		    try (Connection connection = DatabaseConnection.getConnection();
		         PreparedStatement ps = connection.prepareStatement(sqlComponente, Statement.RETURN_GENERATED_KEYS)) {
		    	
		        ps.setInt(1, movimiento.getComponente_id());
		        ps.setString(2,movimiento.getTipo_movimiento().toString());
		        ps.setDouble(3, movimiento.getCantidad());
		        ps.setDouble(4, movimiento.getCosto_movimiento()); 
		        ps.setString(5, movimiento.getMotivo());

		        ps.executeUpdate();
		        
		        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                long idGenerado = generatedKeys.getLong(1);
		                return (int) idGenerado; 
		            } else {
		                throw new SQLException("Error al guardar componente, no se obtuvo el ID generado.");
		            }
		        }
		        
		    } catch (SQLException e) {
		        throw new Exception("Componente NO guardado en database ... " + e.getMessage(), e);
		    }
	}
	 
	 //Mandamos todo un lote de instruccion, en caso que de error por falta de stock o similar 
	 //se retiran los cambios
	 public void saveMovimientosDeInventario(List<MovimientoInventario> movimientos) throws Exception {
		    String sql = "INSERT INTO movimientos_inventario (componente_id, tipo_movimiento, cantidad, costo_movimiento, motivo) VALUES (?, ?, ?, ?, ?)";
		    
		    Connection conn = DatabaseConnection.getConnection(); 
		    
		    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {		        
		        conn.setAutoCommit(false);
		        
		        try {
		            for (MovimientoInventario mov : movimientos) {
		                pstmt.setInt(1, mov.getComponente_id());
		                pstmt.setString(2, mov.getTipo_movimiento().name()); 
		                pstmt.setDouble(3, mov.getCantidad());
		                pstmt.setDouble(4, mov.getCosto_movimiento());
		                pstmt.setString(5, mov.getMotivo());
		                
		                pstmt.addBatch();
		            }
		            
		            pstmt.executeBatch();
		
		            conn.commit();
		            
		        } catch (SQLException e) {
		            // Deshacer
		            conn.rollback();
		            throw new Exception("Error en guardado de varios movimientos (Rollback): " + e.getMessage(), e);
		        } finally {
		            conn.setAutoCommit(true);
		        }
		        
		    } catch (SQLException e) {
		        throw new Exception("Error al preparar la conexión o la consulta: " + e.getMessage(), e);
	    }
	}
	 
	 /*
	  * Permite una conexion personalizada
	  * 
	  * Usado para venta de producto
	  */
	 public void saveMovimientosDeInventario(List<MovimientoInventario> movimientos, Connection conn) throws Exception {
	        String sql = "INSERT INTO movimientos_inventario (componente_id, tipo_movimiento, cantidad, costo_movimiento, motivo) VALUES (?, ?, ?, ?, ?)";
	        
	        // Usamos la conexión recibida por parámetro. Solo el PreparedStatement se auto-cierra.
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {		        
	            
	            for (MovimientoInventario mov : movimientos) {
	                pstmt.setInt(1, mov.getComponente_id());
	                pstmt.setString(2, mov.getTipo_movimiento().name()); 
	                pstmt.setDouble(3, mov.getCantidad());
	                pstmt.setDouble(4, mov.getCosto_movimiento());
	                pstmt.setString(5, mov.getMotivo());
	                
	                pstmt.addBatch();
	            }
	            pstmt.executeBatch();
	            
	        } catch (SQLException e) {
	            throw new Exception("Error al guardar movimientos de inventario en batch: " + e.getMessage(), e);
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
