package org.example.service;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class MutantDetector {

    private static final int LONGITUD_SECUENCIA = 4;
    private static final int MIN_SECUENCIAS_PARA_MUTANTE = 2;
    private static final Pattern PATRON_ADN_VALIDO = Pattern.compile("^[ATCG]+$");

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length < LONGITUD_SECUENCIA) {
            return false;
        }

        final int n = dna.length;
        int contadorSecuencias = 0;

        char[][] matriz = new char[n][];
        for (int i = 0; i < n; i++) {
            if (dna[i] == null || dna[i].length() != n || !PATRON_ADN_VALIDO.matcher(dna[i]).matches()) {
                return false;
            }
            matriz[i] = dna[i].toCharArray();
        }

        for (int fila = 0; fila < n; fila++) {
            for (int col = 0; col < n; col++) {
                // Se verifica en las 4 direcciones desde la celda actual.
                if (col <= n - LONGITUD_SECUENCIA) {
                    if (verificarHorizontal(matriz, fila, col)) {
                        contadorSecuencias++;
                    }
                }
                if (fila <= n - LONGITUD_SECUENCIA) {
                    if (verificarVertical(matriz, fila, col)) {
                        contadorSecuencias++;
                    }
                }
                if (fila <= n - LONGITUD_SECUENCIA && col <= n - LONGITUD_SECUENCIA) {
                    if (verificarDiagonalDescendente(matriz, fila, col)) {
                        contadorSecuencias++;
                    }
                }
                if (fila >= LONGITUD_SECUENCIA - 1 && col <= n - LONGITUD_SECUENCIA) {
                    if (verificarDiagonalAscendente(matriz, fila, col)) {
                        contadorSecuencias++;
                    }
                }

                // Optimización: si ya se encontraron suficientes secuencias, se retorna true.
                if (contadorSecuencias >= MIN_SECUENCIAS_PARA_MUTANTE) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean verificarHorizontal(char[][] matriz, int fila, int col) {
        // Evita el doble conteo, asegurando que este es el inicio de una secuencia.
        if (col > 0 && matriz[fila][col - 1] == matriz[fila][col]) {
            return false;
        }
        char primerCaracter = matriz[fila][col];
        for (int i = 1; i < LONGITUD_SECUENCIA; i++) {
            if (matriz[fila][col + i] != primerCaracter) {
                return false;
            }
        }
        return true;
    }

    private boolean verificarVertical(char[][] matriz, int fila, int col) {
        // Evita el doble conteo, asegurando que este es el inicio de una secuencia.
        if (fila > 0 && matriz[fila - 1][col] == matriz[fila][col]) {
            return false;
        }
        char primerCaracter = matriz[fila][col];
        for (int i = 1; i < LONGITUD_SECUENCIA; i++) {
            if (matriz[fila + i][col] != primerCaracter) {
                return false;
            }
        }
        return true;
    }

    private boolean verificarDiagonalDescendente(char[][] matriz, int fila, int col) {
        // Evita el doble conteo, asegurando que este es el inicio de una secuencia.
        if (fila > 0 && col > 0 && matriz[fila - 1][col - 1] == matriz[fila][col]) {
            return false;
        }
        char primerCaracter = matriz[fila][col];
        for (int i = 1; i < LONGITUD_SECUENCIA; i++) {
            if (matriz[fila + i][col + i] != primerCaracter) {
                return false;
            }
        }
        return true;
    }

    private boolean verificarDiagonalAscendente(char[][] matriz, int fila, int col) {
        // Evita el doble conteo, asegurando que este es el inicio de una secuencia.
        if (fila < matriz.length - 1 && col > 0 && matriz[fila + 1][col - 1] == matriz[fila][col]) {
            return false;
        }
        char primerCaracter = matriz[fila][col];
        for (int i = 1; i < LONGITUD_SECUENCIA; i++) {
            if (matriz[fila - i][col + i] != primerCaracter) {
                return false;
            }
        }
        return true;
    }
}
