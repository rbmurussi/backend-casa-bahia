package com.backendcasabahia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Entity
@Table(name = "vendedor")
public class VendedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = ".*(OUT|CLT|PJ)$", message = "A matrícula deve terminar com 'OUT', 'CLT' ou 'PJ'")
    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private String nome;

    private LocalDate dataNascimento;

    @Column(nullable = false, unique = true)
    private String outCltCnpj;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String tipoContratacao;

    @Column(nullable = false)
    private Long filialId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "filial_id", nullable = false)
//    private FilialEntity filial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getOutCltCnpj() {
        return outCltCnpj;
    }

    public void setOutCltCnpj(String outCltCnpj) {
        this.outCltCnpj = outCltCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoContratacao() {
        return tipoContratacao;
    }

    public void setTipoContratacao(String tipoContratacao) {
        this.tipoContratacao = tipoContratacao;
    }

    public Long getFilialId() {
        return filialId;
    }

    public void setFilialId(Long filialId) {
        this.filialId = filialId;
    }
}