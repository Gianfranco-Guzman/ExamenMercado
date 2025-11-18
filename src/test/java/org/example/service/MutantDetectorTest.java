package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector mutantDetector;

    @BeforeEach
    void setUp() {
        mutantDetector = new MutantDetector();
    }

    @Test
    @DisplayName("Should return true for a mutant with horizontal and diagonal sequences")
    void testMutantWithHorizontalAndDiagonalSequences() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return true for a mutant with vertical sequences")
    void testMutantWithVerticalSequences() {
        String[] dna = {"AAAAGA", "CAGTGC", "TTATGT", "AGAAGG", "CACCTA", "TCACTG"};
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return true for a mutant with multiple horizontal sequences")
    void testMutantWithMultipleHorizontalSequences() {
        String[] dna = {"TTTTGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return true for a mutant with both ascending and descending diagonals")
    void testMutantWithBothDiagonals() {
        String[] dna = {
            "GCGTGA",
            "CACGAC",
            "TTAAGT",
            "AGACGG",
            "GCGTCA",
            "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return true for a large DNA matrix")
    void testMutantWithLargeDna() {
        String[] dna = {
            "ATGCGAATGC",
            "CAGTGCCAGT",
            "TTATGTTTAT",
            "AGAAGGATAA",
            "CCCCTACCCC",
            "TCACTGTCAC",
            "ATGCGAATGC",
            "CAGTGCCAGT",
            "TTATGTTTAT",
            "AGAAGGATAA"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return true when all characters are the same")
    void testMutantAllSameCharacter() {
        String[] dna = {
            "AAAAAA",
            "AAAAAA",
            "AAAAAA",
            "AAAAAA",
            "AAAAAA",
            "AAAAAA"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for a human with only one sequence")
    void testNotMutantWithOnlyOneSequence() {
        String[] dna = {"AAAA", "CTGT", "TTAT", "AGAC"};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for a human with no sequences")
    void testNotMutantWithNoSequences() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for a 4x4 matrix with no sequences")
    void testNotMutantSmallDna() {
        String[] dna = {
            "ATGC",
            "CAGT",
            "TTAT",
            "AGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for null DNA")
    void testNotMutantWithNullDna() {
        assertFalse(mutantDetector.isMutant(null));
    }

    @Test
    @DisplayName("Should return false for empty DNA")
    void testNotMutantWithEmptyDna() {
        String[] dna = {};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for a non-square matrix")
    void testNotMutantWithNonSquareDna() {
        String[] dna = {"ATGC", "CAGT", "TTA"};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for invalid characters")
    void testNotMutantWithInvalidCharacters() {
        String[] dna = {"ATGC", "CAGX", "TTAT", "AGAC"};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for a null row")
    void testNotMutantWithNullRow() {
        String[] dna = {"ATGC", null, "TTAT", "AGAC"};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for a matrix smaller than 4x4")
    void testNotMutantWithTooSmallDna() {
        String[] dna = {"ATG", "CAG", "TTA"};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for sequences longer than four")
    void testNotMutantWithSequenceLongerThanFour() {
        String[] dna = {
            "AAAAA",
            "CTGTG",
            "TTATG",
            "AGACG",
            "TCGTC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return true for a mutant with a diagonal in the corner")
    void testMutantDiagonalInCorner() {
        String[] dna = {
            "ATGCGA",
            "CATTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }
}
