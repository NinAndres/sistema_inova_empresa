package com.faculdade.sistema_inova_empresa.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faculdade.sistema_inova_empresa.entities.Eventos;
import com.faculdade.sistema_inova_empresa.entities.Ideias;
import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.entities.Votacao;
import com.faculdade.sistema_inova_empresa.repositories.EventosRepository;
import com.faculdade.sistema_inova_empresa.repositories.IdeiasRepository;
import com.faculdade.sistema_inova_empresa.repositories.UsuarioRepository;
import com.faculdade.sistema_inova_empresa.repositories.VotacaoRepository;

@Service
public class VotacaoService {
  @Autowired
  private IdeiasRepository ideiasRepository;

  @Autowired
  private VotacaoRepository votacaoRepository;

  @Autowired
  private EventosRepository eventosRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  public void votar(Long usuarioId, Long ideiaId, Long eventoId) {
    Eventos evento = eventosRepository.findById(eventoId)
        .orElseThrow(() -> new RuntimeException("Evento nao encontrado"));

    Ideias ideia = ideiasRepository.findById(ideiaId)
        .orElseThrow(() -> new RuntimeException("Ideia nao encontrada"));

    LocalDateTime now = LocalDateTime.now();
    if (now.isBefore(evento.getDataInicio()) || now.isAfter(evento.getDataFim())) {
      throw new RuntimeException("O periodo de votocao esta fechado");
    }

    boolean votou = votacaoRepository.existsByUsuarioIdAndEventoId(usuarioId, eventoId);

    if (votou) {
      throw new RuntimeException("Usuario ja votou nesse evento");
    }

    Usuario usuario = usuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

    Votacao votacao = new Votacao();
    votacao.setUsuario(usuario);
    votacao.setEvento(evento);
    votacao.setIdeia(ideia);

    votacaoRepository.save(votacao);
  }

  public List<Ideias> obterTop10Ideias(Long eventoId) {
    List<Ideias> ideias = ideiasRepository.findByEventoId(eventoId);

    ideias.sort(Comparator.comparingInt(this::contarVotos).reversed());

    return ideias.stream().limit(10).collect(Collectors.toList());
  }

  private int contarVotos(Ideias ideia) {
    return votacaoRepository.countByIdeiaId(ideia.getId());
  }

}
