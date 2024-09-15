package com.faculdade.sistema_inova_empresa.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.faculdade.sistema_inova_empresa.entities.Ideias;
import com.faculdade.sistema_inova_empresa.services.VotacaoService;

@SpringBootTest
public class VotacaoControllerTest {

  @InjectMocks
  private VotacaoController votacaoController;

  @Mock
  private VotacaoService votacaoService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testVotar() {
    Long usuarioId = 1L;
    Long ideiaId = 1L;
    Long eventoId = 1L;

    doNothing().when(votacaoService).votar(usuarioId, ideiaId, eventoId);

    ResponseEntity<Void> response = votacaoController.votar(usuarioId, ideiaId, eventoId);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(votacaoService, times(1)).votar(usuarioId, ideiaId, eventoId);
  }

  @Test
  public void testObterTop10Ideias() {
    Long eventoId = 1L;

    Ideias ideia1 = new Ideias();
    ideia1.setId(1L);
    Ideias ideia2 = new Ideias();
    ideia2.setId(2L);

    List<Ideias> ideias = Arrays.asList(ideia1, ideia2);

    when(votacaoService.obterTop10Ideias(eventoId)).thenReturn(ideias);

    ResponseEntity<List<Ideias>> response = votacaoController.obterTop10Ideias(eventoId);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(2, response.getBody().size());
    assertEquals(1L, response.getBody().get(0).getId());
    assertEquals(2L, response.getBody().get(1).getId());
    verify(votacaoService, times(1)).obterTop10Ideias(eventoId);
  }
}
