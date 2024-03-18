/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.pagamentofuncionarios.repositories;

/**
 *
 * @author Mateus Merçon
 */
import com.api.pagamentofuncionarios.models.FuncionarioModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.MockitoAnnotations;

class FuncionarioRepositoryTest {

    @Mock
    FuncionarioRepository funcionarioRepository;

    @InjectMocks
    FuncionarioModel funcionarioModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve verificar se um funcionário existe pelo CPF")
    void existsByCpfTest() {
        String cpf = "123.456.789-00";
        Mockito.when(funcionarioRepository.existsByCpf(cpf)).thenReturn(true);

        boolean exists = funcionarioRepository.existsByCpf(cpf);

        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("Deve encontrar um funcionário pelo CPF")
    void findByCpfTest() {
        String cpf = "123.456.789-00";
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        funcionarioModel.setCpf(cpf);

        Mockito.when(funcionarioRepository.findByCpf(cpf)).thenReturn(Optional.of(funcionarioModel));

        Optional<FuncionarioModel> foundFuncionario = funcionarioRepository.findByCpf(cpf);

        Assertions.assertTrue(foundFuncionario.isPresent());
        Assertions.assertEquals(funcionarioModel, foundFuncionario.get());
    }
}
