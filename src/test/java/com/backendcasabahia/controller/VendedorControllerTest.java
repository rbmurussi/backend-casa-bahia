package com.backendcasabahia.controller;

import com.backendcasabahia.model.VendedorEntity;
import com.backendcasabahia.service.VendedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(VendedorController.class)
public class VendedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendedorService vendedorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateVendedor() throws Exception {
        VendedorEntity vendedorEntity = new VendedorEntity();
        vendedorEntity.setId(1L);
        vendedorEntity.setNome("Maria");

        when(vendedorService.createVendedor(any(VendedorEntity.class))).thenReturn(vendedorEntity);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/vendedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nome\": \"Maria\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Maria"));
    }

    @Test
    public void testGetAllVendedores() throws Exception {
        VendedorEntity vendedorA = new VendedorEntity();
        vendedorA.setId(1L);
        vendedorA.setNome("Vendedor A");

        VendedorEntity vendedorB = new VendedorEntity();
        vendedorB.setId(2L);
        vendedorB.setNome("Vendedor B");

        List<VendedorEntity> vendedores = Arrays.asList(vendedorA, vendedorB);

        when(vendedorService.getAllVendedores()).thenReturn(vendedores);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vendedores"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(vendedores.size()));
    }

    @Test
    public void testGetVendedorById() throws Exception {
        VendedorEntity vendedorEntity = new VendedorEntity();
        vendedorEntity.setId(1L);
        vendedorEntity.setNome("João");

        when(vendedorService.getVendedorById(1L)).thenReturn(vendedorEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vendedores/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("João"));
    }

    @Test
    public void testUpdateVendedor() throws Exception {
        VendedorEntity vendedorEntity = new VendedorEntity();
        vendedorEntity.setId(1L);
        vendedorEntity.setNome("Jose");

        when(vendedorService.updateVendedor(any(Long.class), any(VendedorEntity.class))).thenReturn(vendedorEntity);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/vendedores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nome\": \"Jose\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Jose"));
    }

    @Test
    public void testDeleteVendedor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vendedores/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Successful"));
    }
}
