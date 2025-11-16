package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MutantController {

    private final MutantService mutantService;

    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@RequestBody DnaRequest request) {
        boolean isMutant = mutantService.isMutant(request.getDna());
        if (isMutant) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        // Lógica para obtener estadísticas irá aquí
        StatsResponse stats = new StatsResponse(0, 0, 0.0); // Placeholder
        return ResponseEntity.ok(stats);
    }
}
