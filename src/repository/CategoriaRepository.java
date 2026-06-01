package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Categoria;
import models.ComponenteIngredienteReceta;
import models.MovimientoInventario;

public class CategoriaRepository {
	
	
	public int saveCategoria(Categoria categoria) throws Exception {
	    String sqlComponente = "INSERT INTO categorias (nombre,descripcion,activo) "
	    		+ "VALUES(?,?,?)";

	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sqlComponente, java.sql.Statement.RETURN_GENERATED_KEYS)) {
	        
	        ps.setString(1, categoria.getNombre());
	        ps.setString(2, categoria.getDescripcion());
	        ps.setBoolean(3, categoria.getActivo());
	        
	        ps.executeUpdate();
	        
	        try (java.sql.ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                long idGenerado = generatedKeys.getLong(1);
	                return (int) idGenerado; 
	            } else {
	                throw new SQLException("Error al guardar categoria, no se obtuvo el ID generado.");
	            }
	        }
	        
	    } catch (SQLException e) {
	        throw new Exception("categoria NO guardada en database ... " + e.getMessage(), e);
	    }
	}
	
	public void deleteCategoria(int id) throws Exception {
	    String sqlDelete = "DELETE FROM categorias WHERE id = ?";

	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement ps = connection.prepareStatement(sqlDelete)) {
	        
	        ps.setInt(1, id);
	        
	        int filasAfectadas = ps.executeUpdate();
	        
	        if (filasAfectadas == 0) {
	            throw new Exception("No se encontró la categoria con ID: " + id);
	        }
	        
	    } catch (SQLException e) {
	        throw new Exception("Error al eliminar la categoria ... " + e.getMessage(), e);
	    }
	}
	
	public void updateCategoria(Categoria categoria) throws Exception {
        String sqlUpdate = "UPDATE categorias SET "
                + "nombre = ?, "
                + "descripcion = ?, "
                + "activo = ? "
                + "WHERE id = ?"; 

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {
            
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setBoolean(3, categoria.getActivo());
            ps.setInt(4, categoria.getId());

            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new Exception("No se encontró la categoria con ID: " + categoria.getId());
            }
        } catch (SQLException e) {
            throw new Exception("Error al actualizar la categoria: " + e.getMessage(), e);
        }
    }
	
	public List<Categoria> getCategorias() throws Exception {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));
                c.setActivo(rs.getBoolean("activo"));
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar categorias: " + e.getMessage(), e);
        }
        
        return lista;
    }
	
	
	
	
}
