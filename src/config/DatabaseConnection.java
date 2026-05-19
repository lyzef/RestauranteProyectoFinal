package config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static String url;
    private static String user;
    private static String password;

    // Este bloque 'static' se ejecuta UNA SOLA VEZ cuando la aplicación arranca
    static {
        try {
            Properties props = new Properties();
            InputStream input = DatabaseConnection.class.getClassLoader()
                    .getResourceAsStream("config/database.properties");
            
            if (input == null) {
                throw new RuntimeException(" No se pudo encontrar el archivo config/database.properties");
            }
            
            props.load(input);
            
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            String driver = props.getProperty("db.driver");
            
            Class.forName(driver);
            System.out.println("Configuración de base de datos cargada correctamente.");
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al inicializar la base de datos", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}








