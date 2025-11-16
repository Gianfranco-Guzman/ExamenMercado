package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
@Tag(name = "Mutant API", description = "Endpoints for mutant detection and statistics.")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @Operation(summary = "Checks a DNA sequence to determine if it belongs to a mutant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The DNA belongs to a mutant."),
            @ApiResponse(responseCode = "403", description = "The DNA belongs to a human."),
            @ApiResponse(responseCode = "400", description = "The provided DNA sequence is invalid.")
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@RequestBody DnaRequest request) {
        boolean isMutant = mutantService.isMutant(request.getDna());
        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @Operation(summary = "Retrieves statistics of the DNA verification process.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully.")
    })
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = statsService.getStats();
        return ResponseEntity.ok(stats);
    }
}
