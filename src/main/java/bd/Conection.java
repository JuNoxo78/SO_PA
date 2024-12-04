package bd;

import java.sql.Connection;
import java.sql.DriverManager;
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
			JOptionPane.showMessageDialog(null, "Se conectó correctamente con la base de datos.");
		} catch (Exception e) {
			JTextArea textArea = new JTextArea("No se pudo conectar a la base de datos. Error:" + e.toString());
			textArea.setWrapStyleWord(true);
			textArea.setLineWrap(true);
			textArea.setEditable(false);

			textArea.setPreferredSize(new java.awt.Dimension(400, 150));

			JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Título del Mensaje", JOptionPane.INFORMATION_MESSAGE);
		}

		return conectar;
	}
}
