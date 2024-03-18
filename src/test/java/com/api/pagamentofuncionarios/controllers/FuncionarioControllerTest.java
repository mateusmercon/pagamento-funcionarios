/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.pagamentofuncionarios.controllers;

/**
 *
 * @author Mateus Merçon
 */
import com.api.pagamentofuncionarios.dtos.FuncionarioDto;
import com.api.pagamentofuncionarios.models.FuncionarioModel;
import com.api.pagamentofuncionarios.services.FuncionarioService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class FuncionarioControllerTest {

    @Mock
    FuncionarioService funcionarioService;

    @InjectMocks
    FuncionarioController funcionarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve cadastrar um funcionário com sucesso")
    void cadastraFuncionarioTest() {
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        Mockito.when(funcionarioService.existsByCpf(Mockito.anyString())).thenReturn(false);
        Mockito.when(funcionarioService.save(Mockito.any(FuncionarioModel.class))).thenReturn(funcionarioModel);

        FuncionarioDto funcionarioDto = new FuncionarioDto();
        funcionarioDto.setCpf("123.456.789-00");

        ResponseEntity<Object> responseEntity = funcionarioController.cadastraFuncionario(funcionarioDto);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar conflito ao tentar cadastrar um funcionário com CPF já existente")
    void cadastraFuncionarioCpfExistenteTest() {
        String cpf = "123.456.789-00";
        FuncionarioDto funcionarioDto = new FuncionarioDto();
        funcionarioDto.setCpf(cpf);

        Mockito.when(funcionarioService.existsByCpf(cpf)).thenReturn(true);

        ResponseEntity<Object> response = funcionarioController.cadastraFuncionario(funcionarioDto);

        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assertions.assertEquals("Este CPF já se encontra associado a outro funcionário!", response.getBody());
    }

    @Test
    @DisplayName("Deve buscar a lista de funcionários com sucesso")
    void buscaFuncionariosTest() {
        List<FuncionarioModel> funcionarioList = new ArrayList<>();
        Mockito.when(funcionarioService.findAll()).thenReturn(funcionarioList);

        ResponseEntity<Object> responseEntity = funcionarioController.buscaFuncionarios();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(funcionarioList, responseEntity.getBody());
    }

    @Test
    @DisplayName("Deve buscar um funcionário por ID com sucesso")
    void buscaFuncionarioPorIdTest() {
        UUID id = UUID.randomUUID();
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        Mockito.when(funcionarioService.findById(id)).thenReturn(Optional.of(funcionarioModel));

        ResponseEntity<Object> responseEntity = funcionarioController.buscaFuncionarioPorId(id);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(funcionarioModel, responseEntity.getBody());
    }

    @Test
    @DisplayName("Deve retornar not found ao buscar um funcionário por ID que não existe")
    void buscaFuncionarioPorIdNotFoundTest() {
        UUID id = UUID.randomUUID();

        Mockito.when(funcionarioService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = funcionarioController.buscaFuncionarioPorId(id);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Funcionário não encontrado!", response.getBody());
    }

    @Test
    @DisplayName("Deve buscar um funcionário por CPF com sucesso")
    void buscaFuncionarioPorCPFTest() {
        String cpf = "123.456.789-00";
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        Mockito.when(funcionarioService.findByCpf(cpf)).thenReturn(Optional.of(funcionarioModel));

        ResponseEntity<Object> responseEntity = funcionarioController.buscaFuncionarioPorCPF(cpf);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(funcionarioModel, responseEntity.getBody());
    }

    @Test
    @DisplayName("Deve retornar not found ao buscar um funcionário por CPF que não existe")
    void buscaFuncionarioPorCPFNotFoundTest() {
        String cpf = "123.456.789-00";

        Mockito.when(funcionarioService.findByCpf(cpf)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = funcionarioController.buscaFuncionarioPorCPF(cpf);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Funcionário não encontrado!", response.getBody());
    }

    @Test
    @DisplayName("Deve retornar bad request ao tentar buscar funcionário com CPF em formato inválido")
    void buscaFuncionarioPorCPFFormatoInvalidoTest() {
        String cpf = "12345678900";
        ResponseEntity<Object> response = funcionarioController.buscaFuncionarioPorCPF(cpf);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("CPF inválido, use o formato XXX.XXX.XXX-XX", response.getBody());
    }

    @Test
    @DisplayName("Deve atualizar o salário de um funcionário com sucesso")
    void atualizaSalarioTest() {
        String cpf = "123.456.789-00";
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        Mockito.when(funcionarioService.findByCpf(cpf)).thenReturn(Optional.of(funcionarioModel));

        ResponseEntity<Object> responseEntity = funcionarioController.atualizaSalario(cpf);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar not found ao tentar atualizar o salário de um funcionário que não existe")
    void atualizaSalarioNotFoundTest() {
        String cpf = "123.456.789-00";

        Mockito.when(funcionarioService.findByCpf(cpf)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = funcionarioController.atualizaSalario(cpf);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Funcionário não encontrado!", response.getBody());
    }

    @Test
    @DisplayName("Deve retornar bad request ao tentar atualizar salário com CPF em formato inválido")
    void atualizaSalarioCPFInvalidoTest() {
        String cpf = "12345678900";
        ResponseEntity<Object> response = funcionarioController.atualizaSalario(cpf);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("CPF inválido, use o formato XXX.XXX.XXX-XX", response.getBody());
    }

    @Test
    @DisplayName("Deve calcular o imposto de renda de um funcionário com sucesso")
    void calculaImpostoDeRendaTest() {
        String cpf = "123.456.789-00";
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        Mockito.when(funcionarioService.findByCpf(cpf)).thenReturn(Optional.of(funcionarioModel));

        ResponseEntity<Object> responseEntity = funcionarioController.calculaImpostoDeRenda(cpf);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar not found ao tentar calcular o imposto de renda de um funcionário que não existe")
    void calculaImpostoDeRendaNotFoundTest() {
        String cpf = "123.456.789-00";

        Mockito.when(funcionarioService.findByCpf(cpf)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = funcionarioController.calculaImpostoDeRenda(cpf);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Funcionário não encontrado!", response.getBody());
    }

    @Test
    @DisplayName("Deve retornar bad request ao tentar calcular imposto de renda com CPF em formato inválido")
    void calculaImpostoDeRendaCPFInvalidoTest() {
        String cpf = "12345678900";
        ResponseEntity<Object> response = funcionarioController.calculaImpostoDeRenda(cpf);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("CPF inválido, use o formato XXX.XXX.XXX-XX", response.getBody());
    }
}
