/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.pagamentofuncionarios.services;

import com.api.pagamentofuncionarios.models.FuncionarioModel;
import com.api.pagamentofuncionarios.repositories.FuncionarioRepository;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mateus Merçon
 */
@Service
public class FuncionarioService {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Transactional
    public FuncionarioModel save(FuncionarioModel funcionarioModel) {
        return funcionarioRepository.save(funcionarioModel);
    }

    public List<FuncionarioModel> findAll() {
        return funcionarioRepository.findAll();
    }

    public Optional<FuncionarioModel> findById(UUID id) {
        return funcionarioRepository.findById(id);
    }

    public Optional<FuncionarioModel> findByCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf);
    }

    public boolean existsByCpf(String cpf) {
        return funcionarioRepository.existsByCpf(cpf);
    }

    public Object atualizaSalario(FuncionarioModel funcionarioModel) {
        BigDecimal salarioAtual = funcionarioModel.getSalario();
        BigDecimal novoSalario;

        if (salarioAtual.compareTo(BigDecimal.ZERO) >= 0 && salarioAtual.compareTo(BigDecimal.valueOf(400)) <= 0) {
            novoSalario = salarioAtual.multiply(BigDecimal.valueOf(1.15));
        } else if (salarioAtual.compareTo(BigDecimal.valueOf(400)) > 0 && salarioAtual.compareTo(BigDecimal.valueOf(800)) <= 0) {
            novoSalario = salarioAtual.multiply(BigDecimal.valueOf(1.12));
        } else if (salarioAtual.compareTo(BigDecimal.valueOf(800)) > 0 && salarioAtual.compareTo(BigDecimal.valueOf(1200)) <= 0) {
            novoSalario = salarioAtual.multiply(BigDecimal.valueOf(1.1));
        } else if (salarioAtual.compareTo(BigDecimal.valueOf(1200)) > 0 && salarioAtual.compareTo(BigDecimal.valueOf(2000)) <= 0) {
            novoSalario = salarioAtual.multiply(BigDecimal.valueOf(1.07));
        } else {
            novoSalario = salarioAtual.multiply(BigDecimal.valueOf(1.04));
        }

        BigDecimal reajuste = novoSalario.subtract(salarioAtual);
        BigDecimal reajustePorcentagem = reajuste.divide(salarioAtual).multiply(BigDecimal.valueOf(100));

        funcionarioModel.setSalario(novoSalario);
        save(funcionarioModel);

        return ("CPF: " + funcionarioModel.getCpf()
                + "\nNovo salário: " + String.format("%.2f", novoSalario)
                + "\nReajuste ganho: " + String.format("%.2f", reajuste)
                + "\nEm percentual: " + String.format("%.2f", reajustePorcentagem) + "%");
    }
}
