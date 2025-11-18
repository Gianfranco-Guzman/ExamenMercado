package org.example.service;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    private static final int MIN_SEQUENCES_FOR_MUTANT = 2;
    private static final Pattern VALID_DNA_PATTERN = Pattern.compile("^[ATCG]+$");

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length < SEQUENCE_LENGTH) {
            return false;
        }

        final int n = dna.length;
        int sequenceCount = 0;

        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            if (dna[i] == null || dna[i].length() != n || !VALID_DNA_PATTERN.matcher(dna[i]).matches()) {
                return false;
            }
            matrix[i] = dna[i].toCharArray();
        }

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                // Check in all 4 directions from the current cell
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
        // Prevents double counting by ensuring this is the start of a sequence
        if (col > 0 && matrix[row][col - 1] == matrix[row][col]) {
            return false;
        }
        char first = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row][col + i] != first) {
                return false;
            }
        }
        return true;
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        // Prevents double counting by ensuring this is the start of a sequence
        if (row > 0 && matrix[row - 1][col] == matrix[row][col]) {
            return false;
        }
        char first = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row + i][col] != first) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonalDescending(char[][] matrix, int row, int col) {
        // Prevents double counting by ensuring this is the start of a sequence
        if (row > 0 && col > 0 && matrix[row - 1][col - 1] == matrix[row][col]) {
            return false;
        }
        char first = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row + i][col + i] != first) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonalAscending(char[][] matrix, int row, int col) {
        // Prevents double counting by ensuring this is the start of a sequence
        if (row < matrix.length - 1 && col > 0 && matrix[row + 1][col - 1] == matrix[row][col]) {
            return false;
        }
        char first = matrix[row][col];
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (matrix[row - i][col + i] != first) {
                return false;
            }
        }
        return true;
    }
}
