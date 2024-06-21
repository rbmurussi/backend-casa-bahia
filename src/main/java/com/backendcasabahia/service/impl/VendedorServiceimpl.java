package com.backendcasabahia.service.impl;

import com.backendcasabahia.exception.ResourceNotFoundException;
import com.backendcasabahia.model.TipoContratacaoEnum;
import com.backendcasabahia.model.VendedorEntity;
import com.backendcasabahia.repository.FilialRepository;
import com.backendcasabahia.repository.VendedorRepository;
import com.backendcasabahia.service.VendedorService;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Implementação do serviço para operações relacionadas aos vendedores.
 */
@Service
public class VendedorServiceimpl implements VendedorService {

    private final VendedorRepository vendedorRepository;
    private final FilialRepository filialRepository;

    /**
     * Construtor do serviço VendedorServiceimpl.
     *
     * @param vendedorRepository Repositório de vendedores injetado via construtor.
     * @param filialRepository   Repositório de filiais injetado via construtor.
     */
    public VendedorServiceimpl(VendedorRepository vendedorRepository, FilialRepository filialRepository) {
        this.vendedorRepository = vendedorRepository;
        this.filialRepository = filialRepository;
    }

    /**
     * Cria um novo vendedor.
     *
     * @param vendedorEntity Objeto VendedorEntity a ser criado.
     * @return VendedorEntity criado.
     * @throws ValidationException Se houver erro de validação nos dados do vendedor.
     */
    public VendedorEntity createVendedor(VendedorEntity vendedorEntity) {
        validateVendedor(vendedorEntity);
        String matricula = generateMatricula(vendedorEntity.getOutCltCnpj(), vendedorEntity.getTipoContratacao());
        vendedorEntity.setMatricula(matricula);
        return vendedorRepository.save(vendedorEntity);
    }

    /**
     * Atualiza um vendedor existente pelo ID.
     *
     * @param id                  ID do vendedor a ser atualizado.
     * @param vendedorEntityDetails Objeto VendedorEntity com os detalhes a serem atualizados.
     * @return VendedorEntity atualizado.
     * @throws ResourceNotFoundException Se o vendedor ou a filial não forem encontrados.
     * @throws ValidationException       Se houver erro de validação nos dados do vendedor.
     */
    public VendedorEntity updateVendedor(Long id, VendedorEntity vendedorEntityDetails) {
        VendedorEntity vendedorEntity = vendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor not found for this id :: " + id));

        if(!filialRepository.existsById(vendedorEntityDetails.getFilialId()))
            throw new ResourceNotFoundException("Filial not found for this id :: " + vendedorEntityDetails.getFilialId());

        vendedorEntity.setNome(vendedorEntityDetails.getNome());
        vendedorEntity.setDataNascimento(vendedorEntityDetails.getDataNascimento());
        vendedorEntity.setOutCltCnpj(vendedorEntityDetails.getOutCltCnpj());
        vendedorEntity.setEmail(vendedorEntityDetails.getEmail());
        vendedorEntity.setTipoContratacao(vendedorEntityDetails.getTipoContratacao());
        vendedorEntity.setFilialId(vendedorEntityDetails.getFilialId());
        vendedorEntity.setMatricula(generateMatricula(vendedorEntity.getOutCltCnpj(), vendedorEntity.getTipoContratacao()));

        validateVendedor(vendedorEntity);

        return vendedorRepository.save(vendedorEntity);
    }

    /**
     * Retorna todos os vendedores cadastrados.
     *
     * @return Lista de todos os vendedores.
     */
    public List<VendedorEntity> getAllVendedores() {
        return vendedorRepository.findAll();
    }

    /**
     * Retorna um vendedor pelo ID.
     *
     * @param id ID do vendedor a ser obtido.
     * @return VendedorEntity correspondente ao ID.
     * @throws ResourceNotFoundException Se o vendedor não for encontrado.
     */
    public VendedorEntity getVendedorById(Long id) {
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor not found for this id :: " + id));
    }

    /**
     * Deleta um vendedor pelo ID.
     *
     * @param id ID do vendedor a ser deletado.
     * @throws ResourceNotFoundException Se o vendedor não for encontrado.
     */
    public void deleteVendedor(Long id) {
        VendedorEntity vendedorEntity = vendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor not found for this id :: " + id));

        vendedorRepository.delete(vendedorEntity);
    }

    /**
     * Valida os dados de um vendedor.
     *
     * @param vendedorEntity Objeto VendedorEntity a ser validado.
     * @throws ValidationException Se houver erro de validação nos dados do vendedor.
     */
    private void validateVendedor(VendedorEntity vendedorEntity) {
        // Validação do nome
        if (vendedorEntity.getNome() == null || vendedorEntity.getNome().isEmpty()) {
            throw new ValidationException("O nome é obrigatório.");
        }

        // Validação do tipo de contratação
        String tipoContratacao = vendedorEntity.getTipoContratacao();
        if (tipoContratacao == null || tipoContratacao.isEmpty()) {
            throw new ValidationException("O tipo de contratação é obrigatório.");
        }
        if (!isValidTipoContratacao(tipoContratacao)) {
            throw new ValidationException("O tipo de contratação é inválido.");
        }

        // Validação do CPF/CNPJ
        String outCltCnpj = vendedorEntity.getOutCltCnpj();
        if (outCltCnpj == null || outCltCnpj.isEmpty()) {
            throw new ValidationException("O OUT, CLT ou PJ é obrigatório.");
        }
        if (!isValidCltPj(outCltCnpj, tipoContratacao)) {
            throw new ValidationException("O OUT, CLT ou PJ é inválido.");
        }

        // Validação do e-mail
        String email = vendedorEntity.getEmail();
        if (email == null || email.isEmpty()) {
            throw new ValidationException("O e-mail é obrigatório.");
        }
        if (!isValidEmail(email)) {
            throw new ValidationException("O e-mail é inválido.");
        }
    }

    /**
     * Valida se o OUT, CPF ou CNPJ é válido de acordo com o tipo de contratação.
     *
     * @param outCltPj      OUT, CPF ou CNPJ a ser validado.
     * @param tipoContratacao Tipo de contratação (OUT, CLT, PJ).
     * @return true se o CPF/CNPJ for válido, false caso contrário.
     */
    private boolean isValidCltPj(String outCltPj, String tipoContratacao) {
        return TipoContratacaoEnum.valueOf(tipoContratacao).isValid(outCltPj);
    }

    /**
     * Valida se o e-mail possui um formato válido.
     *
     * @param email E-mail a ser validado.
     * @return true se o e-mail for válido, false caso contrário.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    /**
     * Valida se o tipo de contratação é válido.
     *
     * @param tipoContratacao Tipo de contratação a ser validado.
     * @return true se o tipo de contratação for válido, false caso contrário.
     */
    private boolean isValidTipoContratacao(String tipoContratacao) {
        for(TipoContratacaoEnum tipoContratacaoEnum : TipoContratacaoEnum.values()) {
            if(tipoContratacaoEnum.name().matches(tipoContratacao)) return true;
        }
        return false;
    }

    /**
     * Gera a matrícula do vendedor com base no OUT, CPF ou CNPJ e tipo de contratação.
     *
     * @param outCltCnpj      OUT, CPF ou CNPJ do vendedor.
     * @param tipoContratacao Tipo de contratação (OUT, CLT, PJ).
     * @return Matrícula gerada.
     */
    private String generateMatricula(String outCltCnpj, String tipoContratacao) {
        return outCltCnpj + "-" + tipoContratacao;
    }
}
