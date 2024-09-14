package com.faculdade.sistema_inova_empresa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faculdade.sistema_inova_empresa.entities.Avaliacao;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

  List<Avaliacao> findByIdeiaId(Long ideiaId);

  List<Avaliacao> findByEventoId(Long eventoId);
}
