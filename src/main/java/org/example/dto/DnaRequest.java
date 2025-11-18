package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.validation.ValidDnaSequence;

@Data
@Schema(description = "Objeto de solicitud para la verificación de secuencias de ADN.")
public class DnaRequest {

    @NotNull(message = "La secuencia de ADN no puede ser nula.")
    @ValidDnaSequence
    @Schema(description = "Un array de strings que representa la matriz de la secuencia de ADN (NxN). " +
            "Cada string es una fila y debe contener únicamente los caracteres A, T, C, G.",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]",
            required = true)
    private String[] dna;
}
