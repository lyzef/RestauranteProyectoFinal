package repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import models.User;

public class UserRepository {
    private final String rutaArchivoCsv = "src/assets/files/users.csv"; 

    public UserRepository() {
    }

    /**
     * Guarda un usuario nuevo en el archivo CSV
     */
    public void save(User nuevoUsuario) {
        List<User> listaActual = getAllUsers();
        listaActual.add(nuevoUsuario);
        saveAll(listaActual);
    }

    /**
     * Obtiene la lista completa de usuarios desde el archivo CSV
     */
    public List<User> getAllUsers() {
        List<User> listaUsuarios = new ArrayList<>();
        File archivoPersonal = new File(rutaArchivoCsv);
        
        if (!archivoPersonal.exists()) {
            return listaUsuarios;
        }

        try (BufferedReader lectorArchivo = new BufferedReader(new InputStreamReader(
                new FileInputStream(archivoPersonal), StandardCharsets.UTF_8))) {
            
            String linea;

            while ((linea = lectorArchivo.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    User usuario = User.fromCSV(linea);
                    if (usuario != null) {
                        listaUsuarios.add(usuario);
                    }
                }
            }
            
        } catch (IOException errorLectura) {
            System.err.println("Error al leer el archivo CSV: " + errorLectura.getMessage());
        }
        return listaUsuarios;
    }

    public void saveAll(List<User> listaParaGuardar) {
        try (BufferedWriter escritorArchivo = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(rutaArchivoCsv), StandardCharsets.UTF_8))) {
            
            for (User u : listaParaGuardar) {
                escritorArchivo.write(u.toCSV());
                escritorArchivo.newLine();
            }
            
        } catch (IOException errorEscritura) {
            System.err.println("Error al escribir en el archivo CSV: " + errorEscritura.getMessage());
        }
    }

    public List<User> getUsers() {
        return getAllUsers();
    }
}