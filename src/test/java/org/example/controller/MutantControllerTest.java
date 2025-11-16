package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.DnaRequest;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    @Test
    @DisplayName("POST /mutant should return 200 OK for mutant DNA")
    void checkMutant_MutantDna_Returns200() throws Exception {
        when(mutantService.isMutant(any())).thenReturn(true);
        DnaRequest request = new DnaRequest();
        request.setDna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant should return 403 Forbidden for human DNA")
    void checkMutant_HumanDna_Returns403() throws Exception {
        when(mutantService.isMutant(any())).thenReturn(false);
        DnaRequest request = new DnaRequest();
        request.setDna(new String[]{"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"});

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /mutant should return 400 Bad Request for invalid DNA")
    void checkMutant_InvalidDna_Returns400() throws Exception {
        DnaRequest request = new DnaRequest();
        request.setDna(new String[]{"ATGC", "CAGT", "TTA"}); // Not square

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
