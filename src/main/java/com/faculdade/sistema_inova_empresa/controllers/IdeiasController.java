package com.faculdade.sistema_inova_empresa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.faculdade.sistema_inova_empresa.entities.Ideias;
import com.faculdade.sistema_inova_empresa.services.IdeiasService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ideias")
public class IdeiasController {

  @Autowired
  private IdeiasService ideiasService;

  @PostMapping("/criar")
  public ResponseEntity<Ideias> criarIdeia(@RequestBody Ideias ideia) {
    Ideias novaIdeia = ideiasService.createIdeias(ideia);
    return ResponseEntity.ok(novaIdeia);
  }

  @GetMapping
  public ResponseEntity<List<Ideias>> getTodasIdeias() {
    return ResponseEntity.ok(ideiasService.getAllIdeias());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Ideias>> getIdeiaById(@PathVariable Long id) {
    return ResponseEntity.ok(ideiasService.getIdeiasById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Ideias> atualizarIdeia(@PathVariable Long id, @RequestBody Ideias ideia) {
    return ResponseEntity.ok(ideiasService.updateIdeias(id, ideia));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarIdeia(@PathVariable Long id) {
    ideiasService.deleteIdeias(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/distribuir")
  public ResponseEntity<Void> distribuirIdeias(@RequestParam Long eventoId) {
    ideiasService.distribuirIdeias(eventoId);
    return ResponseEntity.noContent().build();
  }
}
