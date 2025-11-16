package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.validation.ValidDnaSequence;

@Data
@Schema(description = "Request body containing the DNA sequence to be analyzed.")
public class DnaRequest {

    @Schema(description = "An array of strings representing the DNA sequence matrix (NxN).",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]",
            required = true)
    @NotNull(message = "DNA sequence cannot be null.")
    @NotEmpty(message = "DNA sequence cannot be empty.")
    @ValidDnaSequence
    private String[] dna;
}
