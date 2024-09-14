package com.faculdade.sistema_inova_empresa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faculdade.sistema_inova_empresa.entities.Ideias;

@Repository
public interface IdeiasRepository extends JpaRepository<Ideias, Long> {

  List<Ideias> findByEventoId(Long eventoId);

}
