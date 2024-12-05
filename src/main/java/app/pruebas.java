package app;

import bd.Conection;
import java.awt.List;
import java.util.ArrayList;
import bd.UserDetails;

public class pruebas {

	public static void main(String[] args) {
		Conection conectObject = new Conection();

		ArrayList<UserDetails> usuarios = conectObject.obtenerDetallesUsuarios();

//		String userName = "profe2";
//		String nuevaContraseña = "4321";
//
//		// Llamada al método para cambiar la contraseña
//		boolean exito = conectObject.cambiarContraseña(userName, nuevaContraseña);
//
//		if (exito) {
//			System.out.println("La contraseña fue cambiada exitosamente.");
//		} else {
//			System.out.println("Hubo un error al cambiar la contraseña.");
//		}

		// Imprimir los resultados obtenidos
		for (UserDetails usuario : usuarios) {
			System.out.println("Usuario: " + usuario.getUserName());
			System.out.println("Password: " + usuario.getContraseña());
			System.out.println("Rol: " + usuario.getNameRol());
			System.out.println("Encrypt: " + usuario.isEncrypt());
			System.out.println("Nombre: " + usuario.getName());
			System.out.println("-----------------------------");
		}
	}

}
