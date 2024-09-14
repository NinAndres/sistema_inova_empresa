package com.faculdade.sistema_inova_empresa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faculdade.sistema_inova_empresa.entities.Eventos;
import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.entities.enums.RoleStatus;
import com.faculdade.sistema_inova_empresa.repositories.EventosRepository;
import com.faculdade.sistema_inova_empresa.repositories.UsuarioRepository;

@Service
public class EventosService {

  @Autowired
  private EventosRepository eventosRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  public Eventos criarEventos(Eventos evento, Long usuarioId) {
    Usuario usuario = usuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    if (!usuario.getRole().equals(RoleStatus.ADMIN)) {
      throw new RuntimeException("Apenas administradores podem criar eventos");

    }
    evento.setCriador(usuario);
    return eventosRepository.save(evento);
  }

  public Eventos selecionarJurados(Long eventoId, List<Long> juradoIds, Long usuarioId) {
    Eventos evento = eventosRepository.findById(eventoId)
        .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

    Usuario admin = usuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new RuntimeException("Admin não encontrado"));

    if (!admin.getRole().equals(RoleStatus.ADMIN)) {
      throw new RuntimeException("Apenas administradores podem selecionar jurados");
    }
    List<Usuario> jurados = usuarioRepository.findAllById(juradoIds);
    for (Usuario usuario : jurados) {
      if (!usuario.getRole().equals(RoleStatus.JURADO)) {
        throw new RuntimeException("Usuario selecionado nao e um jurado");
      }
    }
    evento.setJurados(jurados);
    return eventosRepository.save(evento);
  }

  public List<Eventos> getAllEventos() {
    return eventosRepository.findAll();
  }

  public Optional<Eventos> getEventosById(Long id) {
    return eventosRepository.findById(id);
  }

  public Eventos updateEventos(Long id, Eventos evento) {
    if (eventosRepository.existsById(id)) {
      evento.setId(id);
      return eventosRepository.save(evento);
    }
    return null;
  }

  public void deleteEventos(Long id) {
    eventosRepository.deleteById(id);
  }
}
