package com.faculdade.sistema_inova_empresa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.faculdade.sistema_inova_empresa.entities.Avaliacao;
import com.faculdade.sistema_inova_empresa.services.AvaliacaoService;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

  @Autowired
  private AvaliacaoService avaliacaoService;

  @PostMapping("/avaliar")
  public ResponseEntity<Avaliacao> avaliarIdeia(@RequestParam Long juradoId, @RequestParam Long ideiaId,
      @RequestParam int nota) {
    Avaliacao avaliacao = avaliacaoService.avaliarIdeia(juradoId, ideiaId, nota);
    return ResponseEntity.ok(avaliacao);
  }

  @GetMapping("/media/{ideiaId}")
  public ResponseEntity<Double> calcularMedia(@PathVariable Long ideiaId) {
    Double media = avaliacaoService.mediaDasAvalicoes(ideiaId);
    return ResponseEntity.ok(media);
  }

  @GetMapping("/melhores/{eventoId}")
  public ResponseEntity<List<?>> selecionarMelhoresIdeias(@PathVariable Long eventoId) {
    return ResponseEntity.ok(avaliacaoService.selecaoDasMelhoresIdeias(eventoId));
  }
}
