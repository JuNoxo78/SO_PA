package app;

import bd.Conection;

public class prueba {
    public static void main(String[] args) {
        int rango = 27; // El rango de números (1 a 27)
        int numeroOriginal = 20; // Número a cifrar
        int desplazamiento = 45; // Desplazamiento
        
        // Cifrar
        int numeroCifrado = cifrar(numeroOriginal, desplazamiento, rango);
        System.out.println("Número cifrado: " + numeroCifrado);

        // Descifrar
        int numeroDescifrado = descifrar(numeroCifrado, desplazamiento, rango);
        System.out.println("Número descifrado: " + numeroDescifrado);
    }

    // Método para cifrar
    public static int cifrar(int numero, int desplazamiento, int rango) {
        return ((numero - 1 + desplazamiento) % rango) + 1;
    }

    // Método para descifrar
    public static int descifrar(int numeroCifrado, int desplazamiento, int rango) {
        return ((numeroCifrado - 1 - desplazamiento % rango + rango) % rango) + 1;
    }
}
