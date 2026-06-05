package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.DetalleVenta;
import models.DetalleVenta.EstadoCocina;
import models.DetalleVenta.Urgencia;

public class DetalleVentaRepository {
    
    // Guardar un detalle de venta
    public int saveDetalleVenta(DetalleVenta detalle) throws Exception {
        String sql = "INSERT INTO detalle_venta (venta_id, componente_id, cantidad, "
                   + "precio_unitario_aplicado, subtotal, estado_cocina, urgencia, "
                   + "fecha_hora_inicio, fecha_hora_fin) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, detalle.getVentaId());
            ps.setInt(2, detalle.getComponenteId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitarioAplicado());
            ps.setDouble(5, detalle.getSubtotal());
            ps.setString(6, detalle.getEstadoCocina().name());
            ps.setString(7, detalle.getUrgencia().name());
            ps.setTimestamp(8, detalle.getFechaHoraInicio());
            ps.setTimestamp(9, detalle.getFechaHoraFin());
            
            ps.executeUpdate();
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al guardar detalle venta, no se obtuvo el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new Exception("DetalleVenta NO guardado: " + e.getMessage(), e);
        }
    }
    
    /*
     * Permite una coneccion personalizada
     * 
     * Usado en venta de producto para commit manual
     */
    public int saveDetalleVenta(DetalleVenta detalle, Connection connection) throws Exception {
        String sql = "INSERT INTO detalle_venta (venta_id, componente_id, cantidad, "
                   + "precio_unitario_aplicado, subtotal, estado_cocina, urgencia, "
                   + "fecha_hora_inicio, fecha_hora_fin) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, detalle.getVentaId());
            ps.setInt(2, detalle.getComponenteId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitarioAplicado());
            ps.setDouble(5, detalle.getSubtotal());
            ps.setString(6, detalle.getEstadoCocina().name());
            ps.setString(7, detalle.getUrgencia().name());
            ps.setTimestamp(8, detalle.getFechaHoraInicio());
            ps.setTimestamp(9, detalle.getFechaHoraFin());
            
            ps.executeUpdate();
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al guardar detalle venta, no se obtuvo el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new Exception("DetalleVenta NO guardado: " + e.getMessage(), e);
        }
    }
    
    // Guardar múltiples detalles de venta ( por lote)
    public void saveDetallesVenta(List<DetalleVenta> detalles) throws Exception {
        String sql = "INSERT INTO detalle_venta (venta_id, componente_id, cantidad, "
                   + "precio_unitario_aplicado, subtotal, estado_cocina, urgencia, "
                   + "fecha_hora_inicio, fecha_hora_fin) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = DatabaseConnection.getConnection();
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            
            try {
                for (DetalleVenta detalle : detalles) {
                    ps.setInt(1, detalle.getVentaId());
                    ps.setInt(2, detalle.getComponenteId());
                    ps.setInt(3, detalle.getCantidad());
                    ps.setDouble(4, detalle.getPrecioUnitarioAplicado());
                    ps.setDouble(5, detalle.getSubtotal());
                    ps.setString(6, detalle.getEstadoCocina().name());
                    ps.setString(7, detalle.getUrgencia().name());
                    ps.setTimestamp(8, detalle.getFechaHoraInicio());
                    ps.setTimestamp(9, detalle.getFechaHoraFin());
                    ps.addBatch();
                }
                
                ps.executeBatch();
                connection.commit();
                
            } catch (SQLException e) {
                connection.rollback();
                throw new Exception("Error guardando detalles de venta (Rollback): " + e.getMessage(), e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new Exception("Error al preparar la consulta: " + e.getMessage(), e);
        }
    }
    
    // Obtener detalles por ID de venta
    public List<DetalleVenta> getDetallesByVentaId(int ventaId) throws Exception {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT dv.*, c.nombre as componente_nombre "
                   + "FROM detalle_venta dv "
                   + "LEFT JOIN componentes c ON dv.componente_id = c.id "
                   + "WHERE dv.venta_id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, ventaId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(crearDetalleVenta(rs));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar detalles de venta: " + e.getMessage(), e);
        }
        return lista;
    }
    
    // Obtener detalle por ID
    public DetalleVenta getDetalleVentaById(int id) throws Exception {
        String sql = "SELECT dv.*, c.nombre as componente_nombre "
                   + "FROM detalle_venta dv "
                   + "LEFT JOIN componentes c ON dv.componente_id = c.id "
                   + "WHERE dv.id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearDetalleVenta(rs);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar detalle de venta: " + e.getMessage(), e);
        }
        return null;
    }
    
    // Actualizar estado de cocina
    public void updateEstadoCocina(int detalleId, EstadoCocina estado) throws Exception {
        String sql = "UPDATE detalle_venta SET estado_cocina = ? WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, estado.name());
            ps.setInt(2, detalleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar estado de cocina: " + e.getMessage(), e);
        }
    }
    
    // Iniciar preparación (marcar EN_PROCESO con timestamp)
    public void iniciarPreparacion(int detalleId) throws Exception {
        String sql = "UPDATE detalle_venta SET estado_cocina = ?, fecha_hora_inicio = ? WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, EstadoCocina.EN_PROCESO.name());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setInt(3, detalleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al iniciar preparación: " + e.getMessage(), e);
        }
    }
    
    // Completar preparación
    public void completarPreparacion(int detalleId) throws Exception {
        String sql = "UPDATE detalle_venta SET estado_cocina = ?, fecha_hora_fin = ? WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, EstadoCocina.COMPLETADO.name());
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setInt(3, detalleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al completar preparación: " + e.getMessage(), e);
        }
    }
    
    // Obtener detalles pendientes
    public List<DetalleVenta> getDetallesPendientes() throws Exception {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT dv.*, c.nombre as componente_nombre "
                   + "FROM detalle_venta dv "
                   + "LEFT JOIN componentes c ON dv.componente_id = c.id "
                   + "WHERE dv.estado_cocina IN ('PENDIENTE', 'EN_PROCESO') "
                   + "ORDER BY dv.urgencia DESC, dv.fecha_hora_inicio ASC";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(crearDetalleVenta(rs));
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar detalles pendientes: " + e.getMessage(), e);
        }
        return lista;
    }
    
    // Usando RS
    private DetalleVenta crearDetalleVenta(ResultSet rs) throws SQLException {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setId(rs.getInt("id"));
        detalle.setVentaId(rs.getInt("venta_id"));
        detalle.setComponenteId(rs.getInt("componente_id"));
        detalle.setCantidad(rs.getInt("cantidad"));
        detalle.setPrecioUnitarioAplicado(rs.getDouble("precio_unitario_aplicado"));
        detalle.setSubtotal(rs.getDouble("subtotal"));
        detalle.setEstadoCocina(rs.getString("estado_cocina"));
        detalle.setUrgencia(rs.getString("urgencia"));
        detalle.setFechaHoraInicio(rs.getTimestamp("fecha_hora_inicio"));
        detalle.setFechaHoraFin(rs.getTimestamp("fecha_hora_fin"));
        detalle.setComponenteNombre(rs.getString("componente_nombre"));
        return detalle;
    }
}