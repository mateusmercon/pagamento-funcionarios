/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.pagamentofuncionarios.controllers;

import com.api.pagamentofuncionarios.dtos.FuncionarioDto;
import com.api.pagamentofuncionarios.models.FuncionarioModel;
import com.api.pagamentofuncionarios.services.FuncionarioService;
import com.api.pagamentofuncionarios.utils.CpfUtils;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mateus Merçon
 */
@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @Operation(description = "Cadastra um novo funcionário")
    @PostMapping
    public ResponseEntity<Object> cadastraFuncionario(@RequestBody @Valid FuncionarioDto funcionarioDto) {
        try {
            if (funcionarioService.existsByCpf(funcionarioDto.getCpf())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Este CPF já se encontra associado a outro funcionário!");
            }
            FuncionarioModel funcionarioModel = new FuncionarioModel();
            BeanUtils.copyProperties(funcionarioDto, funcionarioModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.save(funcionarioModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    @Operation(description = "Busca a lista completa de funcionários")
    @GetMapping
    public ResponseEntity<Object> buscaFuncionarios() {
        try {
            List<FuncionarioModel> funcionarios = funcionarioService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(funcionarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar funcionários: " + e.getMessage());
        }
    }

    @Operation(description = "Busca um funcionário por id")
    @GetMapping("/id/{id}")
    public ResponseEntity<Object> buscaFuncionarioPorId(@PathVariable UUID id) {
        try {
            Optional<FuncionarioModel> funcionarioOptional = funcionarioService.findById(id);
            if (funcionarioOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(funcionarioOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar funcionário: " + e.getMessage());
        }
    }

    @Operation(description = "Busca um funcionário por CPF")
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Object> buscaFuncionarioPorCPF(@PathVariable String cpf) {
        try {
            if (!CpfUtils.validarCpf(cpf)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido, use o formato XXX.XXX.XXX-XX");
            }
            Optional<FuncionarioModel> funcionarioOptional = funcionarioService.findByCpf(cpf);
            if (funcionarioOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(funcionarioOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar funcionário: " + e.getMessage());
        }
    }

    @Operation(description = "Atualiza o salário de um funcionário pelo CPF")
    @PutMapping("/{cpf}")
    public ResponseEntity<Object> atualizaSalario(@PathVariable String cpf) {
        try {
            if (!CpfUtils.validarCpf(cpf)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido, use o formato XXX.XXX.XXX-XX");
            }
            Optional<FuncionarioModel> funcionarioOptional = funcionarioService.findByCpf(cpf);
            if (funcionarioOptional.isPresent()) {
                FuncionarioModel funcionarioModel = funcionarioOptional.get();
                return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.atualizaSalario(funcionarioModel));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar salário do funcionário: " + e.getMessage());
        }
    }

    @Operation(description = "Calcula o imposto de renda de um funcionário pelo CPF")
    @GetMapping("/impostoderenda/{cpf}")
    public ResponseEntity<Object> calculaImpostoDeRenda(@PathVariable String cpf) {
        try {
            if (!CpfUtils.validarCpf(cpf)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido, use o formato XXX.XXX.XXX-XX");
            }
            Optional<FuncionarioModel> funcionarioOptional = funcionarioService.findByCpf(cpf);
            if (funcionarioOptional.isPresent()) {
                FuncionarioModel funcionarioModel = funcionarioOptional.get();
                return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.calculaImpostoDeRenda(funcionarioModel));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao calcular imposto de renda do funcionário: " + e.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
