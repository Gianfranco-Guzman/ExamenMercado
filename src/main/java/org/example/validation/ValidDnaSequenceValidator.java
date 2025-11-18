package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final Pattern VALID_DNA_PATTERN = Pattern.compile("^[ATCG]+$");

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length < 4) {
            return false;
        }

        final int n = dna.length;
        for (String row : dna) {
            if (row == null || row.length() != n || !VALID_DNA_PATTERN.matcher(row).matches()) {
                return false;
            }
        }
        return true;
    }
}
