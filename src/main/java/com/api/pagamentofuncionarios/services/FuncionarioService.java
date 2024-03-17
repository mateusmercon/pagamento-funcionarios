/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.pagamentofuncionarios.services;

import com.api.pagamentofuncionarios.models.FuncionarioModel;
import com.api.pagamentofuncionarios.repositories.FuncionarioRepository;
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
}
