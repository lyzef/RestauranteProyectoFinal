package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import config.DatabaseConnection;
import models.User;
import utilidades.PasswordUtils;

public class LoginRepository {

public User login(String correo, String password) {
		
		String sql = "SELECT id, correo, password_hash, rol, nombre FROM usuarios WHERE correo = ?";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			
			stmt.setString(1, correo);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				
				String hashedPassword = rs.getString("password_hash");
				System.out.println(hashedPassword);
				
				//la contrasena hash de la base de datos es la misma que la ingresada por el usuario
				boolean correctPassword = PasswordUtils.checkPassword(password, hashedPassword);
				
				if(!correctPassword) 
					return null;
				
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setCorreo(correo);
				user.setNombre(rs.getString("nombre"));
				user.setRol(rs.getString("rol"));
				
				return user;
			}
			
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
}







