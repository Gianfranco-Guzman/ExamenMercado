package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Cuerpo de la respuesta que contiene las estadísticas de las verificaciones de ADN.")
public class StatsResponse {

    @Schema(description = "Conteo total de secuencias de ADN mutante verificadas.", example = "40")
    @JsonProperty("count_mutant_dna")
    private long countMutantDna;

    @Schema(description = "Conteo total de secuencias de ADN humano verificadas.", example = "100")
    @JsonProperty("count_human_dna")
    private long countHumanDna;

    @Schema(description = "La proporción de verificaciones de ADN mutante a humano.", example = "0.4")
    private double ratio;
}
