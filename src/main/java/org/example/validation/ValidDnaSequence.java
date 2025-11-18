package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDnaSequenceValidator.class)
public @interface ValidDnaSequence {
    String message() default "Secuencia de ADN inválida: debe ser una matriz cuadrada NxN (mínimo 4x4) con solo los caracteres A, T, C, G.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
