package com.faculdade.sistema_inova_empresa.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faculdade.sistema_inova_empresa.entities.Eventos;
import com.faculdade.sistema_inova_empresa.entities.Ideias;
import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.repositories.EventosRepository;
import com.faculdade.sistema_inova_empresa.repositories.IdeiasRepository;

@Service
public class IdeiasService {

  @Autowired
  private IdeiasRepository ideiasRepository;

  @Autowired
  private EventosRepository eventosRepository;

  public Ideias createIdeias(Ideias ideia) {
    return ideiasRepository.save(ideia);
  }

  public List<Ideias> getAllIdeias() {
    return ideiasRepository.findAll();
  }

  public Optional<Ideias> getIdeiasById(Long id) {
    return ideiasRepository.findById(id);
  }

  public Ideias updateIdeias(Long id, Ideias ideia) {
    if (ideiasRepository.existsById(id)) {
      ideia.setId(id);
      return ideiasRepository.save(ideia);
    }
    return null;
  }

  public void deleteIdeias(Long id) {
    ideiasRepository.deleteById(id);
  }

  public void distribuirIdeias(Long eventoId) {
    Eventos evento = eventosRepository.findById(eventoId)
        .orElseThrow(() -> new RuntimeException("Evento nao encontrado"));

    List<Usuario> jurados = evento.getJurados();
    List<Ideias> ideias = evento.getIdeias();

    int countJurados = jurados.size();

    if (countJurados < 2) {
      throw new RuntimeException("Evento precisa ter no minimo 2 jurados");
    }

    Collections.shuffle(ideias);

    for (int i = 0; i < ideias.size(); i++) {
      Usuario jurado = jurados.get(i % jurados.size());
      ideias.get(i).getEvento().getJurados().add(jurado);
      ideiasRepository.save(ideias.get(i));

    }
  }
}
