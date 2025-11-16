package org.example.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    private static final int MIN_SEQUENCES_FOR_MUTANT = 2;

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length < SEQUENCE_LENGTH) {
            return false;
        }

        final int n = dna.length;
        int sequenceCount = 0;

        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            if (dna[i] == null || dna[i].length() != n) {
                return false;
            }
            matrix[i] = dna[i].toCharArray();
        }

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                    }
                }
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                    }
                }
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDescending(matrix, row, col)) {
                        sequenceCount++;
                    }
                }
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAscending(matrix, row, col)) {
                        sequenceCount++;
                    }
                }

                if (sequenceCount >= MIN_SEQUENCES_FOR_MUTANT) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        char first = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row][col + i] != first) {
                return false;
            }
        }
        return true;
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        char first = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row + i][col] != first) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonalDescending(char[][] matrix, int row, int col) {
        char first = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row + i][col + i] != first) {
                return false;
            }
        }
        return true;
    }



    private boolean checkDiagonalAscending(char[][] matrix, int row, int col) {
        char first = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row - i][col + i] != first) {
                return false;
            }
        }
        return true;
    }
}
