package com.backendcasabahia.service;


import com.backendcasabahia.model.VendedorEntity;

import java.util.List;

public interface VendedorService {


    VendedorEntity createVendedor(VendedorEntity vendedorEntity);

    List<VendedorEntity> getAllVendedores();

    VendedorEntity getVendedorById(Long id);

    VendedorEntity updateVendedor(Long id, VendedorEntity vendedorEntityDetails);

    void deleteVendedor(Long id);
}