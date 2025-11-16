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
    @DisplayName("Should return true for a mutant with horizontal and vertical sequences")
    void isMutant_HorizontalAndVertical_ReturnsTrue() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for a human with only one sequence")
    void isMutant_OnlyOneSequence_ReturnsFalse() {
        String[] dna = {"AAAA", "CTGT", "TTAT", "AGAC"};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for a human with no sequences")
    void isMutant_NoSequences_ReturnsFalse() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for non-square matrix")
    void isMutant_NonSquareMatrix_ReturnsFalse() {
        String[] dna = {"ATGC", "CAGT", "TTA"};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for invalid characters")
    void isMutant_InvalidCharacters_ReturnsFalse() {
        String[] dna = {"ATGC", "CAGX", "TTAT", "AGAC"};
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Should return false for small matrix")
    void isMutant_SmallMatrix_ReturnsFalse() {
        String[] dna = {"ATG", "CAG", "TTA"};
        assertFalse(mutantDetector.isMutant(dna));
    }
    
    @Test
    @DisplayName("Should return true for a mutant with diagonal sequences")
    void isMutant_DiagonalSequences_ReturnsTrue() {
        String[] dna = {"AGTCGA", "GATGET", "TTAAGT", "AGAAGG", "CGCCTA", "TCACTG"};
        assertTrue(mutantDetector.isMutant(dna));
    }
}
