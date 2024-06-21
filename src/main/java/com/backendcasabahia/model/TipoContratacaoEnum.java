package com.backendcasabahia.model;

import com.backendcasabahia.validation.DocumentValidator;

public enum TipoContratacaoEnum {
    OUT("Outsourcing") {
        @Override
        public boolean isValid(String outCltPj) {
            return outCltPj.matches("\\d+");
        }
    },
    CLT("CLT") {
        @Override
        public boolean isValid(String outCltPj) {
            return DocumentValidator.isValidCPF(outCltPj);
        }
    },
    PJ("Pessoa Jurídica") {
        @Override
        public boolean isValid(String outCltPj) {
            return DocumentValidator.isValidCNPJ(outCltPj);
        }
    };

    private final String descricao;

    TipoContratacaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public abstract boolean isValid(String outCpfCnpj);
}
