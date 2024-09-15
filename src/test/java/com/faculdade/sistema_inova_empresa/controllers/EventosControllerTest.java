package com.faculdade.sistema_inova_empresa.controllers;

import com.faculdade.sistema_inova_empresa.entities.Eventos;
import com.faculdade.sistema_inova_empresa.services.EventosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventosControllerTest {

  @InjectMocks
  private EventosController eventosController;

  @Mock
  private EventosService eventosService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCriarEvento() {
    Long usuarioId = 1L;
    Eventos evento = new Eventos();
    evento.setNome("Evento Teste");

    when(eventosService.criarEventos(any(Eventos.class), eq(usuarioId))).thenReturn(evento);

    ResponseEntity<Eventos> response = eventosController.criarEvento(evento, usuarioId);

    assertEquals(ResponseEntity.ok(evento), response);
    verify(eventosService, times(1)).criarEventos(any(Eventos.class), eq(usuarioId));
  }

  @Test
  public void testSelecionarJurados() {
    Long eventoId = 1L;
    Long usuarioId = 1L;
    List<Long> juradoIds = Arrays.asList(2L, 3L);
    Eventos evento = new Eventos();
    evento.setNome("Evento Teste");

    when(eventosService.selecionarJurados(eq(eventoId), eq(juradoIds), eq(usuarioId))).thenReturn(evento);

    ResponseEntity<Eventos> response = eventosController.selecionarJurados(eventoId, juradoIds, usuarioId);

    assertEquals(ResponseEntity.ok(evento), response);
    verify(eventosService, times(1)).selecionarJurados(eq(eventoId), eq(juradoIds), eq(usuarioId));
  }

  @Test
  public void testGetAllEventos() {
    Eventos evento1 = new Eventos();
    evento1.setNome("Evento 1");
    Eventos evento2 = new Eventos();
    evento2.setNome("Evento 2");

    List<Eventos> eventosList = Arrays.asList(evento1, evento2);
    when(eventosService.getAllEventos()).thenReturn(eventosList);

    ResponseEntity<List<Eventos>> response = eventosController.getAllEventos();

    assertEquals(ResponseEntity.ok(eventosList), response);
    verify(eventosService, times(1)).getAllEventos();
  }

  @Test
  public void testGetEventoById() {
    Long eventoId = 1L;
    Eventos evento = new Eventos();
    evento.setNome("Evento Teste");

    Optional<Eventos> optionalEvento = Optional.of(evento);
    when(eventosService.getEventosById(eventoId)).thenReturn(optionalEvento);

    ResponseEntity<Optional<Eventos>> response = eventosController.getEventoById(eventoId);

    assertEquals(ResponseEntity.ok(optionalEvento), response);
    verify(eventosService, times(1)).getEventosById(eventoId);
  }

  @Test
  public void testAtualizarEvento() {
    Long eventoId = 1L;
    Eventos evento = new Eventos();
    evento.setNome("Evento Atualizado");

    when(eventosService.updateEventos(eq(eventoId), any(Eventos.class))).thenReturn(evento);

    ResponseEntity<Eventos> response = eventosController.atualizarEvento(eventoId, evento);

    assertEquals(ResponseEntity.ok(evento), response);
    verify(eventosService, times(1)).updateEventos(eq(eventoId), any(Eventos.class));
  }

  @Test
  public void testDeletarEvento() {
    Long eventoId = 1L;

    ResponseEntity<Void> response = eventosController.deletarEvento(eventoId);

    assertEquals(ResponseEntity.noContent().build(), response);
    verify(eventosService, times(1)).deleteEventos(eventoId);
  }
}
