package utilidades;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Hashea una contraseña
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // Verifica una contraseña con el hash almacenado
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
