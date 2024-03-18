/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.pagamentofuncionarios.services;

import com.api.pagamentofuncionarios.models.FuncionarioModel;
import com.api.pagamentofuncionarios.repositories.FuncionarioRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        try {
            return funcionarioRepository.save(funcionarioModel);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao salvar o funcionário no banco de dados", e);
        }
    }

    public List<FuncionarioModel> findAll() {
        try {
            return funcionarioRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao buscar lista de funcionários", e);
        }
    }

    public Optional<FuncionarioModel> findById(UUID id) {
        try {
            return funcionarioRepository.findById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao buscar funcionário", e);
        }
    }

    public Optional<FuncionarioModel> findByCpf(String cpf) {
        try {
            return funcionarioRepository.findByCpf(cpf);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao buscar funcionário", e);
        }
    }

    public boolean existsByCpf(String cpf) {
        try {
            return funcionarioRepository.existsByCpf(cpf);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao verificar funcionário", e);
        }
    }

    public Object atualizaSalario(FuncionarioModel funcionarioModel) {
        try {
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

            Locale.setDefault(Locale.US);

            return ("CPF: " + funcionarioModel.getCpf()
                    + "\nNovo salário: " + String.format("%.2f", novoSalario)
                    + "\nReajuste ganho: " + String.format("%.2f", reajuste)
                    + "\nEm percentual: " + String.format("%.0f", reajustePorcentagem) + "%");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o salário do funcionário", e);
        }
    }

    public Object calculaImpostoDeRenda(FuncionarioModel funcionarioModel) {
        try {
            BigDecimal salario = funcionarioModel.getSalario();

            if (salario.compareTo(BigDecimal.ZERO) >= 0 && salario.compareTo(BigDecimal.valueOf(2000)) <= 0) {
                String impostoDeRenda = "Isento";
                return ("CPF: " + funcionarioModel.getCpf()
                        + "\n" + impostoDeRenda);
            }

            BigDecimal impostoDeRenda;

            if (salario.compareTo(BigDecimal.valueOf(2000)) > 0 && salario.compareTo(BigDecimal.valueOf(3000)) <= 0) {
                impostoDeRenda = salario.subtract(BigDecimal.valueOf(2000)).multiply(BigDecimal.valueOf(0.08));
            } else if (salario.compareTo(BigDecimal.valueOf(3000)) > 0 && salario.compareTo(BigDecimal.valueOf(4500)) <= 0) {
                impostoDeRenda = salario.subtract(BigDecimal.valueOf(3000)).multiply(BigDecimal.valueOf(0.18)).add(BigDecimal.valueOf(80));
            } else {
                impostoDeRenda = salario.subtract(BigDecimal.valueOf(4500)).multiply(BigDecimal.valueOf(0.28)).add(BigDecimal.valueOf(350));
            }

            Locale.setDefault(Locale.US);

            return ("CPF: " + funcionarioModel.getCpf()
                    + "\nImposto de Renda: R$ " + String.format("%.2f", impostoDeRenda));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular o imposto de renda do funcionário", e);
        }
    }

}
