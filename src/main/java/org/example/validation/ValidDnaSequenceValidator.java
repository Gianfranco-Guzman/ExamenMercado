package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final Pattern PATRON_ADN_VALIDO = Pattern.compile("^[ATCG]+$");

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        // La secuencia no puede ser nula y debe tener un tamaño mínimo de 4x4.
        if (dna == null || dna.length < 4) {
            return false;
        }

        final int n = dna.length;
        for (String fila : dna) {
            // Cada fila debe tener el mismo largo que el número de filas (matriz NxN).
            // Además, cada fila debe contener únicamente los caracteres permitidos (A, T, C, G).
            if (fila == null || fila.length() != n || !PATRON_ADN_VALIDO.matcher(fila).matches()) {
                return false;
            }
        }
        // Si todas las validaciones pasan, la secuencia de ADN es válida.
        return true;
    }
}
