package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    @Override
    public StatsResponse getStats() {
        long mutantCount = dnaRecordRepository.countByIsMutant(true);
        long humanCount = dnaRecordRepository.countByIsMutant(false);

        double ratio = 0.0;
        if (humanCount > 0) {
            ratio = (double) mutantCount / humanCount;
        }

        return new StatsResponse(mutantCount, humanCount, ratio);
    }
}
