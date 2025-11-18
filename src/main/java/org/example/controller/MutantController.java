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
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "API de Mutantes", description = "API para detectar mutantes y proveer estadísticas de verificación de ADN.")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    /**
     * Redirige la ruta raíz ("/") a la página de la interfaz de Swagger.
     * @return Una redirección a /swagger-ui.html.
     */
    @GetMapping("/")
    public RedirectView redirectToSwagger() {
        return new RedirectView("/swagger-ui.html");
    }

    @PostMapping("/mutant")
    @Operation(summary = "Determina si una secuencia de ADN pertenece a un mutante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La secuencia de ADN pertenece a un mutante."),
            @ApiResponse(responseCode = "403", description = "La secuencia de ADN pertenece a un humano."),
            @ApiResponse(responseCode = "400", description = "La secuencia de ADN provista es inválida.")
    })
    public ResponseEntity<Void> isMutant(@Validated @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.isMutant(request.getDna());
        return isMutant ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/stats")
    @Operation(summary = "Recupera las estadísticas de las verificaciones de ADN.")
    @ApiResponse(responseCode = "200", description = "Estadísticas recuperadas exitosamente.")
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}
