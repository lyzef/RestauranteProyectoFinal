package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Platillo;

public class PlatillosRepository {
    
    public int savePlatillo(Platillo platillo) throws Exception {
        String sql = "INSERT INTO platillos (componente_id, categoria_id, descripcion, imagen_url, precio_venta) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, 
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, platillo.getComponenteId());
            ps.setInt(2, platillo.getCategoriaId());
            ps.setString(3, platillo.getDescripcion());
            ps.setString(4, platillo.getImagenUrl());
            ps.setDouble(5, platillo.getPrecioVenta());
            
            ps.executeUpdate();
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al guardar platillo, no se obtuvo el ID generado.");
                }
            }
            
        } catch (SQLException e) {
            throw new Exception("Platillo NO guardado en database: " + e.getMessage(), e);
        }
    }
    
    public void updatePlatillo(Platillo platillo) throws Exception {
        String sql = "UPDATE platillos SET "
                + "componente_id = ?, "
                + "categoria_id = ?, "
                + "descripcion = ?, "
                + "imagen_url = ?, "
                + "precio_venta = ? "
                + "WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, platillo.getComponenteId());
            ps.setInt(2, platillo.getCategoriaId());
            ps.setString(3, platillo.getDescripcion());
            ps.setString(4, platillo.getImagenUrl());
            ps.setDouble(5, platillo.getPrecioVenta());
            ps.setInt(6, platillo.getId());

            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new Exception("No se encontró el platillo con ID: " + platillo.getId());
            }
        } catch (SQLException e) {
            throw new Exception("Error al actualizar el platillo: " + e.getMessage(), e);
        }
    }
    
    public void deletePlatillo(int id) throws Exception {
        String sql = "DELETE FROM platillos WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new Exception("No se encontró el platillo con ID: " + id);
            }
            
        } catch (SQLException e) {
            throw new Exception("Error al eliminar el platillo: " + e.getMessage(), e);
        }
    }
    
    public Platillo getPlatilloById(int id) throws Exception {
        String sql = "SELECT * FROM platillos WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearPlatilloPorRs(rs);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar platillo: " + e.getMessage(), e);
        }
        
        return null;
    }
    
    public List<Platillo> getPlatillos() throws Exception {
        List<Platillo> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre as categoria_nombre, "
                + "comp.nombre as componente_nombre "
                + "FROM platillos p "
                + "INNER JOIN categorias c ON p.categoria_id = c.id "
                + "INNER JOIN componentes comp ON p.componente_id = comp.id";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Platillo platillo = crearPlatilloPorRs(rs);
                // Datos adicionales de los joins
                platillo.setCategoriaNombre(rs.getString("categoria_nombre"));
                platillo.setComponenteNombre(rs.getString("componente_nombre"));
                lista.add(platillo);
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar platillos: " + e.getMessage(), e);
        }
        
        return lista;
    }
    
    //Ayuda a crear platillos
    private Platillo crearPlatilloPorRs(ResultSet rs) throws SQLException {
        Platillo platillo = new Platillo();
        platillo.setId(rs.getInt("id"));
        platillo.setComponenteId(rs.getInt("componente_id"));
        platillo.setCategoriaId(rs.getInt("categoria_id"));
        platillo.setDescripcion(rs.getString("descripcion"));
        platillo.setImagenUrl(rs.getString("imagen_url"));
        platillo.setPrecioVenta(rs.getDouble("precio_venta"));
        return platillo;
    }
}
