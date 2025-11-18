package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.validation.ValidDnaSequence;

@Data
@Schema(description = "Request object for DNA sequence verification")
public class DnaRequest {

    @NotNull(message = "DNA sequence cannot be null")
    @ValidDnaSequence
    @Schema(description = "An array of strings representing the DNA sequence matrix (NxN). " +
            "Each string is a row and must contain only the characters A, T, C, G.",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]",
            required = true)
    private String[] dna;
}
