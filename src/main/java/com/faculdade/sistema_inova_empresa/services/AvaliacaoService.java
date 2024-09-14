package com.faculdade.sistema_inova_empresa.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faculdade.sistema_inova_empresa.entities.Avaliacao;
import com.faculdade.sistema_inova_empresa.entities.Ideias;
import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.repositories.AvaliacaoRepository;
import com.faculdade.sistema_inova_empresa.repositories.IdeiasRepository;
import com.faculdade.sistema_inova_empresa.repositories.UsuarioRepository;

@Service
public class AvaliacaoService {

  @Autowired
  private AvaliacaoRepository avaliacaoRepository;

  @Autowired
  private IdeiasRepository ideiasRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  public Avaliacao avaliarIdeia(Long juradoId, Long ideiaId, int nota) {
    Ideias ideia = ideiasRepository.findById(ideiaId)
        .orElseThrow(() -> new RuntimeException("Ideia nao encontrada"));

    Usuario jurado = usuarioRepository.findById(juradoId)
        .orElseThrow(() -> new RuntimeException("Jurado nao encontrado"));

    if (!ideia.getEvento().getJurados().contains(jurado)) {
      throw new RuntimeException("Esse jurado nao pode avaliar essa ideia");
    }

    if (nota < 3 || nota > 10) {
      throw new RuntimeException("A nota precisa estar entre 3 e 10");
    }

    Avaliacao avaliacao = new Avaliacao();
    avaliacao.setIdeia(ideia);
    avaliacao.setJurado(jurado);
    avaliacao.setNota(nota);

    return avaliacaoRepository.save(avaliacao);

  }

  public Double mediaDasAvalicoes(Long ideiaId) {
    Ideias ideia = ideiasRepository.findById(ideiaId)
        .orElseThrow(() -> new RuntimeException("Ideia nao encontrada"));

    if (ideia.getAvaliacoes().size() < 2) {
      throw new RuntimeException("A ideia precisa ser avaliada por 2 jurados");
    }
    double media = calcularMedia(ideia);

    return media;
  }

  public List<Ideias> selecaoDasMelhoresIdeias(Long eventoId) {
    List<Ideias> ideias = ideiasRepository.findByEventoId(eventoId);

    ideias.sort(Comparator.comparingDouble(this::calcularMedia).reversed());

    return ideias.stream().limit(10).collect(Collectors.toList());

  }

  private double calcularMedia(Ideias ideia) {
    List<Avaliacao> avaliacoes = ideia.getAvaliacoes();

    if (avaliacoes.isEmpty()) {
      return 0;
    }

    int sum = 0;
    for (Avaliacao avaliacao : avaliacoes) {
      sum += avaliacao.getNota();
    }

    double media = (double) sum / avaliacoes.size();

    return media;
  }
}
