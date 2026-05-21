package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.User;

public class UserRepository {

    // 1. GUARDAR USUARIO (Inserta en ambas tablas usando Transacciones)
    public void save(User user) {
        String sqlUsuario = "INSERT INTO usuarios (nombre, correo, password_hash, rol) VALUES (?, ?, ?, ?)";
        String sqlInfo = "INSERT INTO usuarios_informacion (usuarios_id, fechaNacimiento, curp, telefono, NSS, "
                + "estadoCivil, genero, descripcionFunciones, tipoContrato, turno, alergiasConocidas, "
                + "contactoEmergencia, tipoDeSangre, banco, numeroCuenta, sueldo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Iniciamos transacción

            // Insertar en la tabla principal 'usuarios' y recuperar el ID generado
            try (PreparedStatement pstUser = connection.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                pstUser.setString(1, user.getNombre());
                pstUser.setString(2, user.getCorreo());
                pstUser.setString(3, user.getContrasena()); // Ya debe venir hasheada con jbcrypt
                pstUser.setString(4, user.getRol());
                
                pstUser.executeUpdate();

                try (ResultSet generatedKeys = pstUser.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generadoId = generatedKeys.getInt(1);

                        // Insertar en la tabla secundaria 'usuarios_informacion' usando el ID obtenido
                        try (PreparedStatement pstInfo = connection.prepareStatement(sqlInfo)) {
                            pstInfo.setInt(1, generadoId);
                            pstInfo.setString(2, user.getFechaNacimiento());
                            pstInfo.setString(3, user.getCurp());
                            pstInfo.setString(4, user.getTelefono());
                            pstInfo.setString(5, user.getNSS());
                            pstInfo.setString(6, user.getEstadoCivil());
                            pstInfo.setString(7, user.getGenero());
                            pstInfo.setString(8, user.getDescripcionFunciones());
                            pstInfo.setString(9, user.getTipoContrato());
                            pstInfo.setString(10, user.getTurno());
                            pstInfo.setString(11, user.getAlergiasConocidas());
                            pstInfo.setString(12, user.getContactoEmergencia());
                            pstInfo.setString(13, user.getTipoDeSangre());
                            pstInfo.setString(14, user.getBanco());
                            pstInfo.setString(15, user.getNumeroCuenta());
                            pstInfo.setString(16, user.getSueldo());

                            pstInfo.executeUpdate();
                        }
                    } else {
                        throw new SQLException("Error al obtener el ID del usuario generado.");
                    }
                }
            }

            connection.commit(); // Si todo salió bien, guardamos los cambios de forma permanente
            System.out.println("Usuario e información extra guardados correctamente.");

        } catch (SQLException ex) {
            if (connection != null) {
                try { connection.rollback(); } catch (SQLException e) { e.printStackTrace(); } // Deshacer cambios si falla
            }
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    // 2. OBTENER USUARIOS 
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM usuarios u INNER JOIN usuarios_informacion ui ON u.id = ui.usuarios_id";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                // Datos de la tabla 'usuarios'
                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setCorreo(rs.getString("correo"));
                user.setContrasena(rs.getString("password_hash"));
                user.setRol(rs.getString("rol"));

                // Datos de la tabla 'usuarios_informacion'
                user.setFechaNacimiento(rs.getString("fechaNacimiento"));
                user.setCurp(rs.getString("curp"));
                user.setTelefono(rs.getString("telefono"));
                user.setNSS(rs.getString("NSS"));
                user.setEstadoCivil(rs.getString("estadoCivil"));
                user.setGenero(rs.getString("genero"));
                user.setDescripcionFunciones(rs.getString("descripcionFunciones"));
                user.setTipoContrato(rs.getString("tipoContrato"));
                user.setTurno(rs.getString("turno"));
                user.setAlergiasConocidas(rs.getString("alergiasConocidas"));
                user.setContactoEmergencia(rs.getString("contactoEmergencia"));
                user.setTipoDeSangre(rs.getString("tipoDeSangre"));
                user.setBanco(rs.getString("banco"));
                user.setNumeroCuenta(rs.getString("numeroCuenta"));
                user.setSueldo(rs.getString("sueldo"));

                users.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    
    public boolean delete(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setInt(1, id);
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Usuario y su información extra eliminados.");
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    // CTUALIZAR USUARIO (Modifica ambas tablas usando el ID del usuario)
    public boolean update(User updatedUser) {
    	boolean completado = false;
        String sqlUsuario = "UPDATE usuarios SET nombre = ?, correo = ?,password_hash = ?, rol = ? WHERE id = ?";
        String sqlInfo = "UPDATE usuarios_informacion SET fechaNacimiento = ?, curp = ?, telefono = ?, NSS = ?, "
                + "estadoCivil = ?, genero = ?, descripcionFunciones = ?, tipoContrato = ?, turno = ?, "
                + "alergiasConocidas = ?, contactoEmergencia = ?, tipoDeSangre = ?, banco = ?, numeroCuenta = ?, "
                + "sueldo = ? WHERE usuarios_id = ?";

        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Iniciamos transacción

            // Actualizar tabla 'usuarios'
            try (PreparedStatement pstUser = connection.prepareStatement(sqlUsuario)) {
                pstUser.setString(1, updatedUser.getNombre());
                pstUser.setString(2, updatedUser.getCorreo());
                pstUser.setString(3, updatedUser.getContrasena());
                pstUser.setString(4, updatedUser.getRol());
                pstUser.setInt(5, updatedUser.getId());
                pstUser.executeUpdate();
            }

            // Actualizar tabla 'usuarios_informacion'
            try (PreparedStatement pstInfo = connection.prepareStatement(sqlInfo)) {
                pstInfo.setString(1, updatedUser.getFechaNacimiento());
                pstInfo.setString(2, updatedUser.getCurp());
                pstInfo.setString(3, updatedUser.getTelefono());
                pstInfo.setString(4, updatedUser.getNSS());
                pstInfo.setString(5, updatedUser.getEstadoCivil());
                pstInfo.setString(6, updatedUser.getGenero());
                pstInfo.setString(7, updatedUser.getDescripcionFunciones());
                pstInfo.setString(8, updatedUser.getTipoContrato());
                pstInfo.setString(9, updatedUser.getTurno());
                pstInfo.setString(10, updatedUser.getAlergiasConocidas());
                pstInfo.setString(11, updatedUser.getContactoEmergencia());
                pstInfo.setString(12, updatedUser.getTipoDeSangre());
                pstInfo.setString(13, updatedUser.getBanco());
                pstInfo.setString(14, updatedUser.getNumeroCuenta());
                pstInfo.setString(15, updatedUser.getSueldo());
                pstInfo.setInt(16, updatedUser.getId()); // En base a usuarios_idq
                pstInfo.executeUpdate();
            }

            connection.commit(); // Confirmamos los cambios en ambas tablas
            System.out.println("Cambios guardados en la base de datos.");
            completado = true;
            
        } catch (SQLException ex) {
            if (connection != null) {
                try { connection.rollback(); } catch (SQLException e) { e.printStackTrace(); }
            }
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }

        return completado;
    }
}