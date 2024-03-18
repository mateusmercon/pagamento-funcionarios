/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.pagamentofuncionarios.services;

/**
 *
 * @author Mateus Merçon
 */
import com.api.pagamentofuncionarios.models.FuncionarioModel;
import com.api.pagamentofuncionarios.repositories.FuncionarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;

class FuncionarioServiceTest {

    @Mock
    FuncionarioRepository funcionarioRepository;

    @InjectMocks
    FuncionarioService funcionarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve salvar um funcionário")
    void saveFuncionarioTest() {
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        Mockito.when(funcionarioRepository.save(Mockito.any(FuncionarioModel.class))).thenReturn(funcionarioModel);

        FuncionarioModel savedFuncionario = funcionarioService.save(funcionarioModel);

        Assertions.assertEquals(funcionarioModel, savedFuncionario);
    }

    @Test
    @DisplayName("Deve encontrar todos os funcionários")
    void findAllFuncionariosTest() {
        List<FuncionarioModel> funcionarioList = new ArrayList<>();
        Mockito.when(funcionarioRepository.findAll()).thenReturn(funcionarioList);

        List<FuncionarioModel> foundFuncionarios = funcionarioService.findAll();

        Assertions.assertEquals(funcionarioList, foundFuncionarios);
    }

    @Test
    @DisplayName("Deve encontrar um funcionário por ID")
    void findByIdFuncionarioTest() {
        UUID id = UUID.randomUUID();
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        Mockito.when(funcionarioRepository.findById(id)).thenReturn(Optional.of(funcionarioModel));

        Optional<FuncionarioModel> foundFuncionario = funcionarioService.findById(id);

        Assertions.assertTrue(foundFuncionario.isPresent());
        Assertions.assertEquals(funcionarioModel, foundFuncionario.get());
    }

    @Test
    @DisplayName("Deve encontrar um funcionário pelo CPF")
    void findByCpfFuncionarioTest() {
        String cpf = "12345678900";
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        Mockito.when(funcionarioRepository.findByCpf(cpf)).thenReturn(Optional.of(funcionarioModel));

        Optional<FuncionarioModel> foundFuncionario = funcionarioService.findByCpf(cpf);

        Assertions.assertTrue(foundFuncionario.isPresent());
        Assertions.assertEquals(funcionarioModel, foundFuncionario.get());
    }

    @Test
    @DisplayName("Deve verificar se um funcionário existe pelo CPF")
    void existsByCpfFuncionarioTest() {
        String cpf = "12345678900";
        Mockito.when(funcionarioRepository.existsByCpf(cpf)).thenReturn(true);

        boolean exists = funcionarioService.existsByCpf(cpf);

        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("Deve atualizar o salário de um funcionário")
    void atualizaSalarioTest() {
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        funcionarioModel.setSalario(BigDecimal.valueOf(500));
        funcionarioModel.setCpf("12345678900");

        Mockito.when(funcionarioRepository.save(Mockito.any(FuncionarioModel.class))).thenReturn(funcionarioModel);

        Object resultado = funcionarioService.atualizaSalario(funcionarioModel);

        String expectedOutput = "CPF: 12345678900\nNovo salário: 560.00\nReajuste ganho: 60.00\nEm percentual: 12%";
        Assertions.assertEquals(expectedOutput, resultado);
    }

    @Test
    @DisplayName("Deve calcular o imposto de renda de um funcionário com salário tributável")
    void calculaImpostoDeRendaTest() {
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        funcionarioModel.setSalario(BigDecimal.valueOf(3002));
        funcionarioModel.setCpf("12345678900");

        Object resultado = funcionarioService.calculaImpostoDeRenda(funcionarioModel);

        String expectedOutput = "CPF: 12345678900\nImposto de Renda: R$ 80.36";
        Assertions.assertEquals(expectedOutput, resultado);
    }
    
    @Test
    @DisplayName("Deve informar que um funcionário com salário não tributável é isento do imposto de renda")
    void calculaImpostoDeRendaIsentoTest() {
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        funcionarioModel.setSalario(BigDecimal.valueOf(1500));
        funcionarioModel.setCpf("12345678900");

        Object resultado = funcionarioService.calculaImpostoDeRenda(funcionarioModel);

        String expectedOutput = "CPF: 12345678900\nIsento";
        Assertions.assertEquals(expectedOutput, resultado);
    }

}
