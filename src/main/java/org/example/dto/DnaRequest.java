package org.example.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.validation.ValidDnaSequence;

@Data
public class DnaRequest {

    @NotNull(message = "DNA sequence cannot be null.")
    @NotEmpty(message = "DNA sequence cannot be empty.")
    @ValidDnaSequence
    private String[] dna;
}
