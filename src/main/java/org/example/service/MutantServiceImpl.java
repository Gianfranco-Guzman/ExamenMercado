package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantServiceImpl implements MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    @Override
    public boolean isMutant(String[] dna) {
        String dnaHash = calculateDnaHash(dna);

        Optional<DnaRecord> existingRecord = dnaRecordRepository.findByDnaHash(dnaHash);
        if (existingRecord.isPresent()) {
            return existingRecord.get().isMutant();
        }

        boolean isMutant = mutantDetector.isMutant(dna);

        DnaRecord newRecord = new DnaRecord(dnaHash, isMutant);
        dnaRecordRepository.save(newRecord);

        return isMutant;
    }

    private String calculateDnaHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dnaString = String.join("", dna);
            byte[] hashBytes = digest.digest(dnaString.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // En un caso real, aquí se manejaría el error de forma más robusta.
            // Por simplicidad, lanzamos una excepción en tiempo de ejecución.
            throw new RuntimeException("Error calculating SHA-256 hash", e);
        }
    }
}
