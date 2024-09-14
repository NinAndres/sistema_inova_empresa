package com.faculdade.sistema_inova_empresa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.faculdade.sistema_inova_empresa.entities.Ideias;
import com.faculdade.sistema_inova_empresa.services.VotacaoService;

import java.util.List;

@RestController
@RequestMapping("/votacoes")
public class VotacaoController {

  @Autowired
  private VotacaoService votacaoService;

  @PostMapping("/votar")
  public ResponseEntity<Void> votar(@RequestParam Long usuarioId, @RequestParam Long ideiaId,
      @RequestParam Long eventoId) {
    votacaoService.votar(usuarioId, ideiaId, eventoId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/top10/{eventoId}")
  public ResponseEntity<List<Ideias>> obterTop10Ideias(@PathVariable Long eventoId) {
    return ResponseEntity.ok(votacaoService.obterTop10Ideias(eventoId));
  }
}
