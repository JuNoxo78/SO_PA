package encryption;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class SOPA {

	private static final Map<Character, String> charToSoup = new LinkedHashMap<>();
	private static final Map<String, Character> soupToChar = new LinkedHashMap<>();
	// Letras incluyendo la "ñ"
	private static final char[] letras = {
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ',
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};

	static {
		// Asignación de abreviaturas de nombres sopas a letras (a-z + ñ)
		String[] sopas = {
			"AV", "BO", "CV", "DA", "ED", "FO", "GA", "HA", "IW", "JA",
			"KR", "LE", "MI", "NO", "SÑ", "OP", "PH", "QU", "RA", "SC", "TY",
			"UD", "VI", "WT", "XA", "YU", "ZU", "ÑÑ"
		};

		// Asignación de abreviaturas a números (0-9)
		String[] sopasNumeros = {
			"ZT", "SS", "BH", "GU", "TK", "SA", "MU", "CH", "LA", "CT"
		};

		// Llenar LinkedHashMap para la asociación letra-abreviatura
		for (int i = 0; i < letras.length; i++) {
			char letra = letras[i];
			charToSoup.put(letra, sopas[i]);
			soupToChar.put(sopas[i], letra);
		}

		// Llenar LinkedHashMap para la asociación número-abreviatura
		for (int i = 0; i < 10; i++) {
			char numero = (char) ('0' + i);
			charToSoup.put(numero, sopasNumeros[i]);
			soupToChar.put(sopasNumeros[i], numero);
		}
	}

	public static String encriptar(String texto) {
		StringBuilder firstResult = new StringBuilder();

		// Fase 1: Reemplazar caracteres por abreviaturas de sopas, espacios en blanco por o, y colocar etiquetas m
		// por las mayúsculas
		for (char ch : texto.toCharArray()) {
			String sopa;

			if (ch == ' ') {
				sopa = "o";
			} else if (Character.isUpperCase(ch)) {
				sopa = "m" + charToSoup.get(Character.toLowerCase(ch));
			} else if (charToSoup.get(ch) == null) {
				sopa = String.valueOf(ch);
			} else {
				sopa = charToSoup.get(ch);
			}

			firstResult.append(sopa);
		}

		// Fase 2: Convertir las sopas a números. Las o de los espacios en blanco a O. Las m de mayúsculas
		// a M.
		StringBuilder secondResult = new StringBuilder();

		for (char letra : String.valueOf(firstResult).toCharArray()) {
			if (letra != 'm' && Character.isLetterOrDigit(letra) && letra != 'o') {
				int numero = letra - 'A' + 1;

				if (numero >= 15 && letra != 'Ñ') {
					numero += 1;
				}

				if (letra == 'Ñ') {
					numero = 15;
				}

				secondResult.append(String.format("%02d", numero));
			} else {
				if (letra == 'o') {
					letra = 'O';
				} else {
					letra = Character.toUpperCase(letra);
				}

				secondResult.append(letra);
			}
		}

		// Fase 3: Aplicar Cifrado César intercalado
		String[] grupos = secondResult.toString().split("O");
		int desplazamiento = texto.length();
		StringBuilder finalResult = new StringBuilder();

		boolean adelante = true;

		for (int i = 0; i < grupos.length; i++) {
			String grupo = grupos[i];
			String toEncrypt = "";

			for (char c : grupo.toCharArray()) {
				if (Character.isDigit(c)) {
					toEncrypt += String.valueOf(c);

					if (toEncrypt.length() == 4) {
						// Aplciar cesar, y guardar en builder finalResult, y resetar parACifrar
						int firstNum = Integer.parseInt(toEncrypt.substring(0, 2));
						int secondNum = Integer.parseInt(toEncrypt.substring(2));

						if (adelante) {
							firstNum = ((firstNum - 1 + desplazamiento) % 27) + 1;
							secondNum = ((secondNum - 1 + desplazamiento) % 27) + 1;
							adelante = false;
						} else {
							firstNum = ((firstNum - 1 - desplazamiento % 27 + 27) % 27) + 1;
							secondNum = ((secondNum - 1 - desplazamiento % 27 + 27) % 27) + 1;
							if (firstNum < 0) {
								firstNum = firstNum * -1;
							}
							if (secondNum < 0) {
								secondNum = secondNum * -1;
							}
							adelante = true;
						}

						if (firstNum == 0) {
							firstNum = 27;
						}

						if (secondNum == 0) {
							secondNum = 27;
						}

						toEncrypt = "";

						finalResult.append(String.format("%02d", firstNum));
						finalResult.append(String.format("%02d", secondNum));
					}
				} else {
					finalResult.append(c);
				}
			}

			if (i != grupos.length - 1) {
				finalResult.append("O");
			}
		}

		return finalResult.toString();
	}

	public static String desencriptar(String textoEncriptado) {
		String[] grupos = textoEncriptado.split("O");
		StringBuilder firstDecryptResult = new StringBuilder();
		int desplazamiento = grupos.length - 1;

		// Fase 1: Quitar al César
		boolean adelante = true;

		for (int i = 0; i < grupos.length; i++) {
			String grupo = grupos[i];
			String toDecrypt = "";

			for (char c : grupo.toCharArray()) {
				if (Character.isDigit(c)) {
					toDecrypt += String.valueOf(c);

					if (toDecrypt.length() == 4) {
						desplazamiento++;
						toDecrypt = "";
					}
				} else {
					if (c != 'M') {
						desplazamiento++;
					}
				}
			}
		}

		for (int i = 0; i < grupos.length; i++) {
			String grupo = grupos[i];
			String toDecrypt = "";

			for (char c : grupo.toCharArray()) {
				if (Character.isDigit(c)) {
					toDecrypt += String.valueOf(c);

					if (toDecrypt.length() == 4) {
						int firstNum = Integer.parseInt(toDecrypt.substring(0, 2));
						int secondNum = Integer.parseInt(toDecrypt.substring(2));

						if (adelante) {
							firstNum = ((firstNum - 1 - desplazamiento % 27 + 27) % 27) + 1;
							secondNum = ((secondNum - 1 - desplazamiento % 27 + 27) % 27) + 1;

							adelante = false;
						} else {
							firstNum = ((firstNum - 1 + desplazamiento) % 27) + 1;
							secondNum = ((secondNum - 1 + desplazamiento) % 27) + 1;
							adelante = true;
						}

						if (firstNum == 0) {
							firstNum = 27;
						}

						if (secondNum == 0) {
							secondNum = 27;
						}

						toDecrypt = "";

						firstDecryptResult.append(String.format("%02d", firstNum));
						firstDecryptResult.append(String.format("%02d", secondNum));
					}
				} else {
					firstDecryptResult.append(c);
				}

			}

			if (i != grupos.length - 1) {
				firstDecryptResult.append("O");
			}
		}

		// Fase 2: Convertir los números a abreviaturas de sopas. Las O de los espacios en blanco a o. Las M de mayúsculas
		// a m
		StringBuilder secondDecryptResult = new StringBuilder();
		String toDecrypt = "";

		for (char ch : String.valueOf(firstDecryptResult).toCharArray()) {
			if (Character.isDigit(ch)) {
				toDecrypt += String.valueOf(ch);

				if (toDecrypt.length() == 2) {
					char letra = letras[Integer.valueOf(toDecrypt) - 1];
					letra = Character.toUpperCase(letra);
					secondDecryptResult.append(letra);
					toDecrypt = "";
				}
			} else {
				ch = Character.toLowerCase(ch);
				secondDecryptResult.append(ch);
			}
		}

		// Fase Final: Reconstruir sopas a partir de números. Considerar que las mayúsculas están precidas por una "m", y que los espacios son "o"
		StringBuilder finalDecryptResult = new StringBuilder();
		toDecrypt = "";

		for (String grupo : secondDecryptResult.toString().split("o")) {
			int i = 0;
			String traduccion = "";

			for (char ch : grupo.toCharArray()) {
				if (ch == 'm') {
					String sRest = grupo.substring(i + 1, i + 3);
					char result = soupToChar.get(sRest);
					result = Character.toUpperCase(result);
					traduccion += String.valueOf(result);
				} else {
					if (i >= 1) {
						if (grupo.charAt(i - 1) == 'm') {
							i++;
							continue;
						} else if (i >= 2) {
							if (grupo.charAt(i - 2) == 'm') {
								i++;
								continue;
							}
						}
					}

					if (Character.isLetter(ch)) {
						toDecrypt += String.valueOf(ch);

						if (toDecrypt.length() == 2) {
							char result = soupToChar.get(toDecrypt);
							traduccion += String.valueOf(result);
							toDecrypt = "";
						}

					} else {
						traduccion += String.valueOf(ch);
					}

				}

				i++;

			}
			finalDecryptResult.append(traduccion);
			finalDecryptResult.append(" ");
		}

		return finalDecryptResult.toString().trim();
	}
}
