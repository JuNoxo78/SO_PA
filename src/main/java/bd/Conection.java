package bd;

import java.awt.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Conection {

	Connection conectar = null;

	String usuario = "usersql";
	String contraseña = "root";
	String bd = "SO_PA-BD";
	String ip = "localhost";
	String puerto = "1433";

	//String cadena = "jdbc:sqlserver://" + ip + ":" + puerto + "/" + bd;

	public Connection establecerConexion() {
		try {
			String cadena = "jdbc:sqlserver://" + ip + ":" + puerto + ";" + "databaseName=" + bd;
			conectar = DriverManager.getConnection(cadena, usuario, contraseña);
		} catch (Exception e) {
			System.out.println("No se pudo conectar a la base de datos. Error:" + e.toString());
		}

		return conectar;
	}
	
	public ArrayList<UserDetails> obtenerDetallesUsuarios() {
        ArrayList<UserDetails> usuarios = new ArrayList<>();

        String query = "EXEC dbo.sp_GetAllUserDetails";

        try (Connection conn = establecerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String userName = rs.getString("userName");
                String contraseña = rs.getString("contraseña");
                String nameRol = rs.getString("nameRol");
				String str = "1";
                boolean encrypt = str.equals(rs.getString("encrypt"));
                String name = rs.getString("NombrePersona");

                // Crear un objeto UserDetails y agregarlo a la lista
                usuarios.add(new UserDetails(userName, contraseña, nameRol, encrypt, name));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public boolean cambiarContraseña(String userName, String nuevaContraseña) {
        String query = "{CALL dbo.sp_CambiarContraseña(?, ?)}"; // Llamada al procedimiento almacenado

        try (Connection conn = establecerConexion(); // Usamos el método establecido para la conexión
             CallableStatement stmt = conn.prepareCall(query)) {

            // Establecemos los parámetros para el procedimiento almacenado
            stmt.setString(1, userName);
            stmt.setString(2, nuevaContraseña);

            // Ejecutamos el procedimiento
            int filasAfectadas = stmt.executeUpdate();

            // Si el número de filas afectadas es mayor que 0, la contraseña se actualizó correctamente
            if (filasAfectadas > 0) {
                System.out.println("Password cambiada correctamente.");
                return true;
            } else {
                System.out.println("No se encontro el usuario o la password no se pudo cambiar.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cambiar la contraseña: " + e.getMessage());
            return false;
        }
    }

}
