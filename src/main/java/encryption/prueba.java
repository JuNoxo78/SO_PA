/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package encryption;

import static encryption.SOPA.desencriptar;
import static encryption.SOPA.encriptar;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class prueba {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Texto original: ");
		String textoOriginal = scan.nextLine();

		System.out.print("Desea encriptar o desencriptar (1. encriptar; 2. desencriptar): ");
		int op = scan.nextInt();

		if (op == 1) {
			// Encriptar
			String encriptado = encriptar(textoOriginal.trim());
			System.out.println("Texto encriptado: " + encriptado);
		} else {
			// Desencriptar
			String desencriptado = desencriptar(textoOriginal.trim());
			System.out.println("Texto desencriptado: " + desencriptado);

		}

	}

}
