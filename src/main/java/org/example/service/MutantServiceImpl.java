package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.exception.DnaHashCalculationException;
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
        // Se calcula el hash del ADN para identificarlo de forma única.
        String dnaHash = calculateDnaHash(dna);

        // Se busca en la base de datos si este ADN ya fue verificado.
        Optional<DnaRecord> registroExistente = dnaRecordRepository.findByDnaHash(dnaHash);
        if (registroExistente.isPresent()) {
            // Si existe, se retorna el resultado almacenado para evitar recalcular.
            return registroExistente.get().isMutant();
        }

        // Si no existe, se realiza el análisis para determinar si es mutante.
        boolean isMutant = mutantDetector.isMutant(dna);

        // Se crea un nuevo registro con el resultado y se guarda en la base de datos.
        DnaRecord nuevoRegistro = new DnaRecord(dnaHash, isMutant);
        dnaRecordRepository.save(nuevoRegistro);

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
            // Este error no debería ocurrir si el algoritmo SHA-256 está disponible en el JRE.
            throw new DnaHashCalculationException("Error al calcular el hash SHA-256 del ADN.", e);
        }
    }
}
