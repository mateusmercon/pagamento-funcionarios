/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.pagamentofuncionarios.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 *
 * @author Mateus Merçon
 */
public class FuncionarioDto {

    @NotBlank(message = "O CPF não pode estar em vazio")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido, use o formato XXX.XXX.XXX-XX")
    private String cpf;

    @Size(max = 256, message = "O nome deve ter no máximo 256 caracteres")
    private String nome;

    private LocalDate dataDeNascimento;

    @Size(max = 11, message = "O telefone deve ter no máximo 11 dígitos")
    private String telefone;

    @Size(max = 512, message = "O endereço deve ter no máximo 512 caracteres")
    private String endereco;

    @NotNull(message = "O salário não pode estar em vazio")
    @PositiveOrZero(message = "O salário não pode ser negativo")
    private BigDecimal salario;

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

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

}
