package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private StatsServiceImpl statsService;

    @Test
    @DisplayName("Should calculate stats correctly")
    void getStats_CalculatesCorrectly() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse stats = statsService.getStats();

        assertEquals(40, stats.getCountMutantDna());
        assertEquals(100, stats.getCountHumanDna());
        assertEquals(0.4, stats.getRatio());
    }

    @Test
    @DisplayName("Should handle division by zero when no humans are found")
    void getStats_NoHumans_HandlesDivisionByZero() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse stats = statsService.getStats();

        assertEquals(10, stats.getCountMutantDna());
        assertEquals(0, stats.getCountHumanDna());
        assertEquals(0.0, stats.getRatio());
    }
}
