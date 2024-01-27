package com.machertecnologia.crudmarcher.usuario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo não pode ser vazio")
    @Column(nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "O campo não pode ser vazio")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O campo não pode ser vazio")
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "O campo não pode ser vazio")
    @Embedded
    private Endereco endereco;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRegistro status;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @Column(nullable = false, updatable = false)
    private String usuarioCriacao;
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;
    private String usuarioAtualizacao;
    private LocalDateTime dataRemocao;
    private String usuarioRemocao;

    @NotBlank(message = "O campo não pode ser vazio")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credencial_id")
    private CredencialUsuario credencialUsuario;

    public Usuario() {}

    public Usuario(Long id, String cpf, String nome, LocalDate dataNascimento, Endereco endereco, StatusRegistro status, LocalDateTime dataCriacao, String usuarioCriacao, CredencialUsuario credencialUsuario) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.usuarioCriacao = usuarioCriacao;
        this.credencialUsuario = credencialUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public StatusRegistro getStatus() {
        return status;
    }

    public void setStatus(StatusRegistro status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getUsuarioCriacao() {
        return usuarioCriacao;
    }

    public void setUsuarioCriacao(String usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getUsuarioAtualizacao() {
        return usuarioAtualizacao;
    }

    public void setUsuarioAtualizacao(String usuarioAtualizacao) {
        this.usuarioAtualizacao = usuarioAtualizacao;
    }

    public LocalDateTime getDataRemocao() {
        return dataRemocao;
    }

    public void setDataRemocao(LocalDateTime dataRemocao) {
        this.dataRemocao = dataRemocao;
    }

    public String getUsuarioRemocao() {
        return usuarioRemocao;
    }

    public void setUsuarioRemocao(String usuarioRemocao) {
        this.usuarioRemocao = usuarioRemocao;
    }

    public CredencialUsuario getCredencialUsuario() {
        return credencialUsuario;
    }

    public void setCredencialUsuario(CredencialUsuario credencialUsuario) {
        this.credencialUsuario = credencialUsuario;
    }
}