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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Mutant API", description = "API for detecting mutants and providing DNA verification statistics")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @PostMapping("/mutant")
    @Operation(summary = "Determines if a DNA sequence belongs to a mutant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The DNA sequence belongs to a mutant"),
            @ApiResponse(responseCode = "403", description = "The DNA sequence belongs to a human"),
            @ApiResponse(responseCode = "400", description = "The provided DNA sequence is invalid")
    })
    public ResponseEntity<Void> isMutant(@Validated @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.isMutant(request.getDna());
        return isMutant ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/stats")
    @Operation(summary = "Retrieves statistics of DNA verifications")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved statistics")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}
