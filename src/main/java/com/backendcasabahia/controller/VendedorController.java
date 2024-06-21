package com.backendcasabahia.controller;

import com.backendcasabahia.model.VendedorEntity;
import com.backendcasabahia.service.VendedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações relacionadas aos vendedores.
 */
@RestController
@RequestMapping("/api/vendedores")
@Tag(name = "Vendedores", description = "Endpoints para operações relacionadas aos vendedores")
public class VendedorController {

    private final VendedorService vendedorService;

    /**
     * Construtor do controlador VendedorController.
     *
     * @param vendedorService Serviço de vendedores injetado via construtor.
     */
    public VendedorController(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    /**
     * Cria um novo vendedor.
     *
     * @param vendedorEntity Objeto VendedorEntity a ser criado.
     * @return ResponseEntity contendo o vendedor criado e status HTTP 200 (OK) se criado com sucesso.
     */
    @Operation(summary = "Criar um novo vendedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PatchMapping
    public ResponseEntity<VendedorEntity> createVendedor(@RequestBody VendedorEntity vendedorEntity) {
        VendedorEntity createdVendedorEntity = vendedorService.createVendedor(vendedorEntity);
        return ResponseEntity.ok(createdVendedorEntity);
    }

    /**
     * Obtém todos os vendedores cadastrados.
     *
     * @return Lista de todos os vendedores cadastrados.
     */
    @Operation(summary = "Obter todos os vendedores cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vendedores retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public List<VendedorEntity> getAllVendedores() {
        return vendedorService.getAllVendedores();
    }

    /**
     * Obtém um vendedor pelo ID.
     *
     * @param id ID do vendedor a ser obtido.
     * @return ResponseEntity contendo o vendedor encontrado e status HTTP 200 (OK) se encontrado, ou status HTTP 404 (Not Found) se não encontrado.
     */
    @Operation(summary = "Obter um vendedor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Vendedor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VendedorEntity> getVendedorById(@PathVariable(value = "id") Long id) {
        VendedorEntity vendedorEntity = vendedorService.getVendedorById(id);
        return ResponseEntity.ok().body(vendedorEntity);
    }

    /**
     * Atualiza um vendedor existente pelo ID.
     *
     * @param id                  ID do vendedor a ser atualizado.
     * @param vendedorEntityDetails Objeto VendedorEntity com os detalhes a serem atualizados.
     * @return ResponseEntity contendo o vendedor atualizado e status HTTP 200 (OK) se atualizado com sucesso, ou status HTTP 404 (Not Found) se não encontrado.
     */
    @Operation(summary = "Atualizar um vendedor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Vendedor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VendedorEntity> updateVendedor(@PathVariable(value = "id") Long id, @RequestBody VendedorEntity vendedorEntityDetails) {
        VendedorEntity updatedVendedorEntity = vendedorService.updateVendedor(id, vendedorEntityDetails);
        return ResponseEntity.ok(updatedVendedorEntity);
    }

    /**
     * Deleta um vendedor pelo ID.
     *
     * @param id ID do vendedor a ser deletado.
     * @return ResponseEntity com mensagem de sucesso e status HTTP 200 (OK) se deletado com sucesso, ou status HTTP 404 (Not Found) se não encontrado.
     */
    @Operation(summary = "Deletar um vendedor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vendedor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVendedor(@PathVariable(value = "id") Long id) {
        vendedorService.deleteVendedor(id);
        return ResponseEntity.ok("Successful");
    }
}
