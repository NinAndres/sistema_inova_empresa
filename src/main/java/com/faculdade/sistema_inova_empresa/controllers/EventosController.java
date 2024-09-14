package com.faculdade.sistema_inova_empresa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.faculdade.sistema_inova_empresa.entities.Eventos;
import com.faculdade.sistema_inova_empresa.services.EventosService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eventos")
public class EventosController {

  @Autowired
  private EventosService eventosService;

  @PostMapping("/criar")
  public ResponseEntity<Eventos> criarEvento(@RequestBody Eventos evento, @RequestParam Long usuarioId) {
    Eventos novoEvento = eventosService.criarEventos(evento, usuarioId);
    return ResponseEntity.ok(novoEvento);
  }

  @PostMapping("/selecionarJurados")
  public ResponseEntity<Eventos> selecionarJurados(@RequestParam Long eventoId, @RequestBody List<Long> juradoIds,
      @RequestParam Long usuarioId) {
    Eventos evento = eventosService.selecionarJurados(eventoId, juradoIds, usuarioId);
    return ResponseEntity.ok(evento);
  }

  @GetMapping
  public ResponseEntity<List<Eventos>> getAllEventos() {
    return ResponseEntity.ok(eventosService.getAllEventos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Eventos>> getEventoById(@PathVariable Long id) {
    return ResponseEntity.ok(eventosService.getEventosById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Eventos> atualizarEvento(@PathVariable Long id, @RequestBody Eventos evento) {
    return ResponseEntity.ok(eventosService.updateEventos(id, evento));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarEvento(@PathVariable Long id) {
    eventosService.deleteEventos(id);
    return ResponseEntity.noContent().build();
  }
}
