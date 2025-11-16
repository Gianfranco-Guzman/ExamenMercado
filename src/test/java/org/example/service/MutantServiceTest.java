package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantServiceImpl mutantService;

    @Test
    @DisplayName("Should return true and save when DNA is mutant and not in DB")
    void isMutant_MutantDnaNotInDb_ReturnsTrueAndSaves() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(true);

        boolean result = mutantService.isMutant(dna);

        assertTrue(result);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Should return from cache if DNA is already in DB")
    void isMutant_DnaInDb_ReturnsCachedResult() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DnaRecord cachedRecord = new DnaRecord("somehash", true);
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(cachedRecord));

        boolean result = mutantService.isMutant(dna);

        assertTrue(result);
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any());
    }
}
