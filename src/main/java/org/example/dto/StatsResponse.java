package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response body containing the statistics of DNA verifications.")
public class StatsResponse {

    @Schema(description = "Total count of mutant DNA sequences verified.", example = "40")
    @JsonProperty("count_mutant_dna")
    private long countMutantDna;

    @Schema(description = "Total count of human DNA sequences verified.", example = "100")
    @JsonProperty("count_human_dna")
    private long countHumanDna;

    @Schema(description = "The ratio of mutant to human DNA verifications.", example = "0.4")
    private double ratio;
}
