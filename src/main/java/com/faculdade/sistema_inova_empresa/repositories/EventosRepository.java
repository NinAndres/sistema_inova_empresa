package com.faculdade.sistema_inova_empresa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faculdade.sistema_inova_empresa.entities.Eventos;

@Repository
public interface EventosRepository extends JpaRepository<Eventos, Long> {
}
