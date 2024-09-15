package com.faculdade.sistema_inova_empresa.controllers;

import com.faculdade.sistema_inova_empresa.entities.Ideias;
import com.faculdade.sistema_inova_empresa.services.IdeiasService;
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
public class IdeiasControllerTest {

  @InjectMocks
  private IdeiasController ideiasController;

  @Mock
  private IdeiasService ideiasService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCriarIdeia() {
    Ideias ideia = new Ideias();
    ideia.setNome("Ideia Teste");

    when(ideiasService.createIdeias(any(Ideias.class))).thenReturn(ideia);

    ResponseEntity<Ideias> response = ideiasController.criarIdeia(ideia);

    assertEquals(ResponseEntity.ok(ideia), response);
    verify(ideiasService, times(1)).createIdeias(any(Ideias.class));
  }

  @Test
  public void testGetTodasIdeias() {
    Ideias ideia1 = new Ideias();
    ideia1.setNome("Ideia 1");
    Ideias ideia2 = new Ideias();
    ideia2.setNome("Ideia 2");

    List<Ideias> ideiasList = Arrays.asList(ideia1, ideia2);
    when(ideiasService.getAllIdeias()).thenReturn(ideiasList);

    ResponseEntity<List<Ideias>> response = ideiasController.getTodasIdeias();

    assertEquals(ResponseEntity.ok(ideiasList), response);
    verify(ideiasService, times(1)).getAllIdeias();
  }

  @Test
  public void testGetIdeiaById() {
    Long ideiaId = 1L;
    Ideias ideia = new Ideias();
    ideia.setNome("Ideia Teste");

    Optional<Ideias> optionalIdeia = Optional.of(ideia);
    when(ideiasService.getIdeiasById(ideiaId)).thenReturn(optionalIdeia);

    ResponseEntity<Optional<Ideias>> response = ideiasController.getIdeiaById(ideiaId);

    assertEquals(ResponseEntity.ok(optionalIdeia), response);
    verify(ideiasService, times(1)).getIdeiasById(ideiaId);
  }

  @Test
  public void testAtualizarIdeia() {
    Long ideiaId = 1L;
    Ideias ideia = new Ideias();
    ideia.setNome("Ideia Atualizada");

    when(ideiasService.updateIdeias(eq(ideiaId), any(Ideias.class))).thenReturn(ideia);

    ResponseEntity<Ideias> response = ideiasController.atualizarIdeia(ideiaId, ideia);

    assertEquals(ResponseEntity.ok(ideia), response);
    verify(ideiasService, times(1)).updateIdeias(eq(ideiaId), any(Ideias.class));
  }

  @Test
  public void testDeletarIdeia() {
    Long ideiaId = 1L;

    ResponseEntity<Void> response = ideiasController.deletarIdeia(ideiaId);

    assertEquals(ResponseEntity.noContent().build(), response);
    verify(ideiasService, times(1)).deleteIdeias(ideiaId);
  }

  @Test
  public void testDistribuirIdeias() {
    Long eventoId = 1L;

    ResponseEntity<Void> response = ideiasController.distribuirIdeias(eventoId);

    assertEquals(ResponseEntity.noContent().build(), response);
    verify(ideiasService, times(1)).distribuirIdeias(eventoId);
  }
}
