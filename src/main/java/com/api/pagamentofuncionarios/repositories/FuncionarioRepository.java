/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.api.pagamentofuncionarios.repositories;

import com.api.pagamentofuncionarios.models.FuncionarioModel;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mateus Merçon
 */
@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, UUID> {

    boolean existsByCpf(String cpf);

    Optional<FuncionarioModel> findByCpf(String cpf);
}
