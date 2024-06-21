package com.backendcasabahia.controller;

import com.backendcasabahia.model.FilialEntity;
import com.backendcasabahia.repository.FilialRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para endpoints relacionados às filiais.
 */
@RestController
@RequestMapping("/api/filiais")
@Tag(name = "Filial", description = "Endpoints para operações relacionadas às filiais")
public class FilialController {

    private final FilialRepository filialRepository;

    /**
     * Construtor do controlador FilialController.
     *
     * @param filialRepository Repositório de filiais injetado via construtor.
     */
    public FilialController(FilialRepository filialRepository) {
        this.filialRepository = filialRepository;
    }

    /**
     * Endpoint para obter todas as filiais cadastradas.
     *
     * @return Lista de objetos FilialEntity representando todas as filiais cadastradas.
     */
    @Operation(summary = "Obter todas as filiais", description = "Este endpoint retorna uma lista de todas as filiais cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de filiais retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/all")
    public List<FilialEntity> getFiliais() {
        return filialRepository.findAll();
    }
}
