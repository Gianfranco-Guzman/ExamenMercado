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

        double ratio;
        // Se calcula la proporción de mutantes sobre humanos.
        // Se evita la división por cero si no hay registros de humanos.
        if (humanCount > 0) {
            ratio = (double) mutantCount / humanCount;
        } else {
            // Si no hay humanos, el ratio es 0 a menos que haya mutantes, en cuyo caso es infinito.
            // Por simplicidad y consistencia, se puede devolver el número de mutantes o simplemente 0.
            // Devolver 0 si no hay humanos es una opción segura.
            ratio = 0.0;
        }

        return new StatsResponse(mutantCount, humanCount, ratio);
    }
}
