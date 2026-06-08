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
import models.Venta;
import models.DetalleVenta.EstadoCocina;
import models.Venta.tipoMetodoPago;
import models.Venta.tipoPedido;

public class VentasRepository {
    
    // Guardar una venta
    public int saveVenta(Venta venta) throws Exception {
        String sql = "INSERT INTO ventas (usuario_id, fecha_hora, total_venta, metodo_pago, tipo_pedido, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, venta.getUsuarioID());
            ps.setTimestamp(2, venta.getFechaHora());
            ps.setDouble(3, venta.getTotalVenta());
            ps.setString(4, venta.getMetodoPago().getValorDB());
            ps.setString(5, venta.getTipoPedidoVenta().getValorDB());
            ps.setString(6, venta.getEstado());
            
            ps.executeUpdate();
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al guardar venta, no se obtuvo el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new Exception("Venta NO guardada en database: " + e.getMessage(), e);
        }
    }
    
    /*
     * Guardamos con una conexion personalizada
     * * Usada en venta de productos con commit manual
     */
    public int saveVenta(Venta venta, Connection connection) throws Exception {
        String sql = "INSERT INTO ventas (usuario_id, fecha_hora, total_venta, metodo_pago, tipo_pedido, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, venta.getUsuarioID());
            ps.setTimestamp(2, venta.getFechaHora());
            ps.setDouble(3, venta.getTotalVenta());
            ps.setString(4, venta.getMetodoPago().getValorDB());
            ps.setString(5, venta.getTipoPedidoVenta().getValorDB());
            ps.setString(6, venta.getEstado());
            
            ps.executeUpdate();
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); 
                } else {
                    throw new SQLException("Error al guardar venta, no se obtuvo el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new Exception("Venta NO guardada en database: " + e.getMessage(), e);
        }
    }
    
    // Obtener venta por ID con cantidad de productos, unidades y estado de cocina
    public Venta getVentaById(int id) throws Exception {
        String sql = "SELECT v.*, u.nombre as nombre_usuario, "
                   + "COUNT(DISTINCT dv.id) as cantidad_productos, "
                   + "COALESCE(SUM(dv.cantidad), 0) as cantidad_unidades, "
                   + "MAX(dv.estado_cocina) as estado_cocina " // Trae el estado predominante/último de la cocina
                   + "FROM ventas v "
                   + "LEFT JOIN usuarios u ON v.usuario_id = u.id "
                   + "LEFT JOIN detalle_venta dv ON v.id = dv.venta_id "
                   + "WHERE v.id = ? "
                   + "GROUP BY v.id";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearVenta(rs);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar venta: " + e.getMessage(), e);
        }
        return null;
    }
    
    // Obtener todas las ventas con cantidad de productos, unidades y estado de cocina
    public List<Venta> getAllVentas() throws Exception {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT v.*, u.nombre as nombre_usuario, "
                   + "COUNT(DISTINCT dv.id) as cantidad_productos, "
                   + "COALESCE(SUM(dv.cantidad), 0) as cantidad_unidades, "
                   + "MAX(dv.estado_cocina) as estado_cocina "
                   + "FROM ventas v "
                   + "LEFT JOIN usuarios u ON v.usuario_id = u.id "
                   + "LEFT JOIN detalle_venta dv ON v.id = dv.venta_id "
                   + "GROUP BY v.id "
                   + "ORDER BY v.fecha_hora DESC";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(crearVenta(rs));
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar ventas: " + e.getMessage(), e);
        }
        return lista;
    }
    
    // Obtener ventas por rango de fechas
    public List<Venta> getVentasByFecha(Timestamp fechaInicio, Timestamp fechaFin) throws Exception {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT v.*, u.nombre as nombre_usuario, "
                   + "COUNT(DISTINCT dv.id) as cantidad_productos, "
                   + "COALESCE(SUM(dv.cantidad), 0) as cantidad_unidades, "
                   + "MAX(dv.estado_cocina) as estado_cocina "
                   + "FROM ventas v "
                   + "LEFT JOIN usuarios u ON v.usuario_id = u.id "
                   + "LEFT JOIN detalle_venta dv ON v.id = dv.venta_id "
                   + "WHERE v.fecha_hora BETWEEN ? AND ? "
                   + "GROUP BY v.id "
                   + "ORDER BY v.fecha_hora DESC";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setTimestamp(1, fechaInicio);
            ps.setTimestamp(2, fechaFin);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(crearVenta(rs));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar ventas por fecha: " + e.getMessage(), e);
        }
        return lista;
    }
    
    // Obtener ventas por usuario
    public List<Venta> getVentasByUsuario(int usuarioId) throws Exception {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT v.*, u.nombre as nombre_usuario, "
                   + "COUNT(DISTINCT dv.id) as cantidad_productos, "
                   + "COALESCE(SUM(dv.cantidad), 0) as cantidad_unidades, "
                   + "MAX(dv.estado_cocina) as estado_cocina "
                   + "FROM ventas v "
                   + "LEFT JOIN usuarios u ON v.usuario_id = u.id "
                   + "LEFT JOIN detalle_venta dv ON v.id = dv.venta_id "
                   + "WHERE v.usuario_id = ? "
                   + "GROUP BY v.id "
                   + "ORDER BY v.fecha_hora DESC";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, usuarioId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(crearVenta(rs));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar ventas por usuario: " + e.getMessage(), e);
        }
        return lista;
    }
    
    // Obtener ventas por estado de la venta
    public List<Venta> getVentasByEstado(String estado) throws Exception {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT v.*, u.nombre as nombre_usuario, "
                   + "COUNT(DISTINCT dv.id) as cantidad_productos, "
                   + "COALESCE(SUM(dv.cantidad), 0) as cantidad_unidades, "
                   + "MAX(dv.estado_cocina) as estado_cocina "
                   + "FROM ventas v "
                   + "LEFT JOIN usuarios u ON v.usuario_id = u.id "
                   + "LEFT JOIN detalle_venta dv ON v.id = dv.venta_id "
                   + "WHERE v.estado = ? "
                   + "GROUP BY v.id "
                   + "ORDER BY v.fecha_hora DESC";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, estado);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(crearVenta(rs));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar ventas por estado: " + e.getMessage(), e);
        }
        return lista;
    }
    
    // Actualizar estado de venta
    public void updateEstadoVenta(int ventaId, String estado) throws Exception {
        String sql = "UPDATE ventas SET estado = ? WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, estado);
            ps.setInt(2, ventaId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar estado de venta: " + e.getMessage(), e);
        }
    }
    
    // Cancelar venta
    public void cancelarVenta(int ventaId) throws Exception {
        updateEstadoVenta(ventaId, "CANCELADO");
    }
    
    // Obtener total de ventas del día
    public double getTotalVentasDelDia() throws Exception {
        String sql = "SELECT COALESCE(SUM(total_venta), 0) as total "
                   + "FROM ventas "
                   + "WHERE DATE(fecha_hora) = CURDATE() "
                   + "AND estado = 'PAGADO'";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar total de ventas del día: " + e.getMessage(), e);
        }
        return 0;
    }
    
    // Obtener cantidad de ventas del día
    public int getCantidadVentasDelDia() throws Exception {
        String sql = "SELECT COUNT(*) as cantidad "
                   + "FROM ventas "
                   + "WHERE DATE(fecha_hora) = CURDATE() "
                   + "AND estado = 'PAGADO'";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("cantidad");
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar cantidad de ventas del día: " + e.getMessage(), e);
        }
        return 0;
    }
    
    public double getTicketPromedioSemanaActual() throws Exception {
        String sql = "SELECT COALESCE(ROUND(AVG(total_venta), 2), 0.0) AS ticket_promedio "
                   + "FROM ventas "
                   + "WHERE estado = 'PAGADO' "
                   + "AND YEARWEEK(fecha_hora, 1) = YEARWEEK(NOW(), 1)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getDouble("ticket_promedio");
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar el ticket promedio de la semana actual: " + e.getMessage(), e);
        }
        return 0.0;
    }
    
    public double getTiempoPromedioPreparacionSemanaActual() throws Exception {
        String sql = "SELECT COALESCE(ROUND(AVG(TIMESTAMPDIFF(MINUTE, fecha_hora_inicio, fecha_hora_fin)), 2), 0.0) AS tiempo_promedio "
                   + "FROM detalle_venta "
                   + "WHERE estado_cocina = 'COMPLETADO' "
                   + "AND fecha_hora_inicio IS NOT NULL "
                   + "AND fecha_hora_fin IS NOT NULL "
                   + "AND YEARWEEK(fecha_hora_fin, 1) = YEARWEEK(NOW(), 1)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getDouble("tiempo_promedio");
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar el tiempo promedio de preparación de la semana actual: " + e.getMessage(), e);
        }
        return 0.0;
    }
    
    //Temporal este va en detalle ventas
    public int getCantidadComandasPendientes() throws Exception {
        String sql = "SELECT COUNT(*) as cantidad "
                   + "FROM detalle_venta "
                   + "WHERE estado_cocina = 'PENDIENTE'";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("cantidad");
            }
        } catch (SQLException e) {
            throw new Exception("Error al consultar la cantidad de comandas pendientes: " + e.getMessage(), e);
        }
        return 0;
    }
    
    // Método privado para crear objeto Venta desde ResultSet
    private Venta crearVenta(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setId(rs.getInt("id"));
        venta.setUsuarioID(rs.getInt("usuario_id"));
        venta.setFechaHora(rs.getTimestamp("fecha_hora"));
        venta.setTotalVenta(rs.getDouble("total_venta"));
        venta.setMetodoPago(rs.getString("metodo_pago"));
        venta.setTipoPedidoVenta(rs.getString("tipo_pedido"));
        venta.setEstado(rs.getString("estado"));

        // Atributos extras
        venta.setNombreUsuario(rs.getString("nombre_usuario"));
        venta.setCantidadProductos(rs.getInt("cantidad_productos")); // número de detalles venta
        venta.setCantidadUnidades(rs.getInt("cantidad_unidades"));   // suma total de cantidades
        
        String estadoCocinaDB = rs.getString("estado_cocina");
        if (estadoCocinaDB != null) {
            venta.setEstadoCocina(EstadoCocina.fromString(estadoCocinaDB));
        } else {
            venta.setEstadoCocina(EstadoCocina.PENDIENTE); 
        }
        
        return venta;
    }
}