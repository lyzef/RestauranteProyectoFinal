package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import config.DatabaseConnection;
import models.User;

public class LoginRepository {

	public User login(String email, String password) {
		
		/*String sql = "SELECT id, email, password FROM users WHERE email = '" 
				+ email + "' AND password = '" + password + "'";*/
		
		String sql = "SELECT id, email, password FROM users WHERE email = ? AND password = ?";
		
		try (
			//Creando conexion
			Connection conn = DatabaseConnection.getConnection();
			//Envia la estructura de la consulta 
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			stmt.setString(1, email); // Toma el primer '?' y coloca el email de forma segura.
			stmt.setString(2, password); // Toma el segundo '?' y coloca la contraseña de forma segura.
			
			//Resultados de la busqueda en una tabla
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setCorreo(rs.getString("email"));
				
				return user;
			}
			
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
}







