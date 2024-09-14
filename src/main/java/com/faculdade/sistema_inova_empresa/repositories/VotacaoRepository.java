package com.faculdade.sistema_inova_empresa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faculdade.sistema_inova_empresa.entities.Votacao;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {

  int countByIdeiaId(Long id);

  boolean existsByUsuarioIdAndEventoId(Long usuarioId, Long eventoId);
}