package com.backendcasabahia.service.impl;

import com.backendcasabahia.exception.ResourceNotFoundException;
import com.backendcasabahia.model.VendedorEntity;
import com.backendcasabahia.repository.FilialRepository;
import com.backendcasabahia.repository.VendedorRepository;
import com.backendcasabahia.service.VendedorService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VendedorServiceimplTest {

    @Test
    public void testCreateVendedor() {

        VendedorEntity vendedorEntity = new VendedorEntity();
        vendedorEntity.setNome("João");
        vendedorEntity.setOutCltCnpj("33760918204");
        vendedorEntity.setEmail("joao@teste.com");
        vendedorEntity.setTipoContratacao("CLT");

        VendedorRepository vendedorRepository = mock(VendedorRepository.class);
        when(vendedorRepository.save(any(VendedorEntity.class))).thenReturn(vendedorEntity);
        VendedorService vendedorService = new VendedorServiceimpl(vendedorRepository, null);
        VendedorEntity createdVendedor = vendedorService.createVendedor(vendedorEntity);

        assertNotNull(createdVendedor);
        assertEquals("João", createdVendedor.getNome());
        assertEquals("33760918204-CLT", createdVendedor.getMatricula());

        verify(vendedorRepository, times(1)).save(any(VendedorEntity.class));
    }

    @Test
    public void testUpdateVendedor() {
        Long id = 1L;
        VendedorEntity existingVendedor = new VendedorEntity();
        existingVendedor.setId(id);
        existingVendedor.setNome("Maria");
        existingVendedor.setOutCltCnpj("00000000000000");
        existingVendedor.setEmail("maria@teste.com");
        existingVendedor.setTipoContratacao("PJ");
        existingVendedor.setFilialId(null);

        VendedorEntity vendedorEntityDetails = new VendedorEntity();
        vendedorEntityDetails.setId(id);
        vendedorEntityDetails.setNome("Maria Silva");
        vendedorEntityDetails.setOutCltCnpj("15829448000190");
        vendedorEntityDetails.setEmail("maria.silva@teste.com");
        vendedorEntityDetails.setTipoContratacao("PJ");
        vendedorEntityDetails.setFilialId(id);

        VendedorRepository vendedorRepository = mock(VendedorRepository.class);
        when(vendedorRepository.findById(id)).thenReturn(Optional.of(existingVendedor));
        when(vendedorRepository.save(any(VendedorEntity.class))).thenReturn(vendedorEntityDetails);
        FilialRepository filialRepository = mock(FilialRepository.class);
        when(filialRepository.existsById(any(Long.class))).thenReturn(true);
        VendedorService vendedorService = new VendedorServiceimpl(vendedorRepository, filialRepository);
        VendedorEntity updatedVendedor = vendedorService.updateVendedor(id, vendedorEntityDetails);

        assertNotNull(updatedVendedor);
        assertEquals("Maria Silva", updatedVendedor.getNome());
        assertEquals("maria.silva@teste.com", updatedVendedor.getEmail());

        verify(vendedorRepository, times(1)).findById(id);
        verify(vendedorRepository, times(1)).save(any(VendedorEntity.class));
    }

    @Test
    public void testGetAllVendedores() {
        VendedorEntity vendedor1 = new VendedorEntity();
        vendedor1.setId(1L);
        vendedor1.setNome("Pedro");

        VendedorEntity vendedor2 = new VendedorEntity();
        vendedor2.setId(2L);
        vendedor2.setNome("Ana");

        VendedorRepository vendedorRepository = mock(VendedorRepository.class);
        when(vendedorRepository.findAll()).thenReturn(Arrays.asList(vendedor1, vendedor2));

        VendedorService vendedorService = new VendedorServiceimpl(vendedorRepository, null);
        List<VendedorEntity> vendedores = vendedorService.getAllVendedores();

        assertNotNull(vendedores);
        assertEquals(2, vendedores.size());
        assertEquals("Pedro", vendedores.get(0).getNome());
        assertEquals("Ana", vendedores.get(1).getNome());

        verify(vendedorRepository, times(1)).findAll();
    }

    @Test
    public void testGetVendedorById() {
        Long id = 1L;
        VendedorEntity vendedor = new VendedorEntity();
        vendedor.setId(id);
        vendedor.setNome("Carlos");

        VendedorRepository vendedorRepository = mock(VendedorRepository.class);
        when(vendedorRepository.findById(id)).thenReturn(Optional.of(vendedor));

        VendedorService vendedorService = new VendedorServiceimpl(vendedorRepository, null);
        VendedorEntity foundVendedor = vendedorService.getVendedorById(id);

        assertNotNull(foundVendedor);
        assertEquals(id, foundVendedor.getId());
        assertEquals("Carlos", foundVendedor.getNome());

        verify(vendedorRepository, times(1)).findById(id);
    }

    @Test
    public void testDeleteVendedor() {
        Long id = 1L;
        VendedorEntity vendedor = new VendedorEntity();
        vendedor.setId(id);
        vendedor.setNome("Fernanda");

        VendedorRepository vendedorRepository = mock(VendedorRepository.class);
        when(vendedorRepository.findById(id)).thenReturn(Optional.of(vendedor));

        VendedorService vendedorService = new VendedorServiceimpl(vendedorRepository, null);
        vendedorService.deleteVendedor(id);

        verify(vendedorRepository, times(1)).findById(id);
        verify(vendedorRepository, times(1)).delete(any(VendedorEntity.class));
    }

    @Test
    public void testDeleteVendedorNotFound() {
        VendedorRepository vendedorRepository = mock(VendedorRepository.class);
        VendedorService vendedorService = new VendedorServiceimpl(vendedorRepository, null);
        // Teste para verificar se a exceção ResourceNotFoundException é lançada
        try {
            vendedorService.deleteVendedor(1L);
            // Se o método não lançar a exceção, o teste falhará
            assert false : "Expected ResourceNotFoundException";
        } catch (ResourceNotFoundException e) {
            // Sucesso: esperamos que a exceção seja lançada
            assert true;
        }
    }

}

