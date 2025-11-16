package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MutantServiceImpl implements MutantService {

    private final MutantDetector mutantDetector;

    @Override
    public boolean isMutant(String[] dna) {
        return mutantDetector.isMutant(dna);
    }
}
