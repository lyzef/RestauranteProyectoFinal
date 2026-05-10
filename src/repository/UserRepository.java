package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import models.User;

public class UserRepository {
    private final String rutaArchivoJson = "src/assets/files/users.json"; 
    private final Gson gson;

    public UserRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    public void save(User nuevoUsuario) {
        List<User> listaActual = getAllUsers();
        listaActual.add(nuevoUsuario);
        saveAll(listaActual);
    }
    public List<User> getAllUsers() {
        File archivo = new File(rutaArchivoJson);
        
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(
                new FileInputStream(archivo), StandardCharsets.UTF_8))) {

            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> lista = gson.fromJson(lector, listType);
            
            return (lista != null) ? lista : new ArrayList<>();
            
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAll(List<User> listaParaGuardar) {
        try (BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(rutaArchivoJson), StandardCharsets.UTF_8))) {
            
            gson.toJson(listaParaGuardar, escritor);
            
        } catch (IOException e) {
            System.err.println("Error al escribir archivo JSON: " + e.getMessage());
        }
    }
}