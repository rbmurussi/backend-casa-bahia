package com.backendcasabahia.controller;

import com.backendcasabahia.model.FilialEntity;
import com.backendcasabahia.repository.FilialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(FilialController.class)
public class FilialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilialRepository filialRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFiliais() throws Exception {
        // Dados de exemplo
        FilialEntity filialA = new FilialEntity();
        filialA.setId(1L);
        filialA.setNome("Filial A");

        FilialEntity filialB = new FilialEntity();
        filialB.setId(2L);
        filialB.setNome("Filial B");

        List<FilialEntity> filiais = Arrays.asList(filialA, filialB);

        // Mock do comportamento do repositório
        when(filialRepository.findAll()).thenReturn(filiais);

        // Requisição GET para /api/filiais/all
        mockMvc.perform(MockMvcRequestBuilders.get("/api/filiais/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(filiais.size()));
    }
}