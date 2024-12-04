package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class conection {
	Connection conectar = null;

	String usuario = "usersql";
	String contraseña = "root";
	String bd = "SO_PA-BD";
	String ip = "localhost";
	String puerto = "1433";

	String cadena = "jdbc:sqlserver://" + ip + ":" + puerto + "/" + bd;

	public Connection establecerConexion() {
		try {
			String cadena = "jdbc:sqlserver://localhost:" + puerto + ";" + "databaseName=" + bd;
			conectar = DriverManager.getConnection(cadena, usuario, contraseña);
			JOptionPane.showMessageDialog(null, "Se conectó correctamente con la base de datos.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos. Error:" + e.toString());
		}

		return conectar;
	}
}
