package com.faculdade.sistema_inova_empresa.services;

import com.faculdade.sistema_inova_empresa.entities.Eventos;
import com.faculdade.sistema_inova_empresa.entities.Ideias;
import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.entities.enums.RoleStatus;
import com.faculdade.sistema_inova_empresa.repositories.EventosRepository;
import com.faculdade.sistema_inova_empresa.repositories.IdeiasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class IdeiasServiceTest {

  @InjectMocks
  private IdeiasService ideiasService;

  @Mock
  private IdeiasRepository ideiasRepository;

  @Mock
  private EventosRepository eventosRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCreateIdeias() {
    Ideias ideia = new Ideias();
    ideia.setNome("Nova Ideia");

    when(ideiasRepository.save(ideia)).thenReturn(ideia);

    Ideias createdIdeia = ideiasService.createIdeias(ideia);

    assertNotNull(createdIdeia);
    assertEquals("Nova Ideia", createdIdeia.getNome());
    verify(ideiasRepository, times(1)).save(ideia);
  }

  @Test
  public void testGetAllIdeias() {
    Ideias ideia1 = new Ideias();
    ideia1.setNome("Ideia 1");
    Ideias ideia2 = new Ideias();
    ideia2.setNome("Ideia 2");
    List<Ideias> ideiasList = Arrays.asList(ideia1, ideia2);

    when(ideiasRepository.findAll()).thenReturn(ideiasList);

    List<Ideias> result = ideiasService.getAllIdeias();

    assertEquals(2, result.size());
    verify(ideiasRepository, times(1)).findAll();
  }

  @Test
  public void testGetIdeiasById() {
    Long ideiaId = 1L;
    Ideias ideia = new Ideias();
    ideia.setId(ideiaId);

    when(ideiasRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));

    Optional<Ideias> result = ideiasService.getIdeiasById(ideiaId);

    assertTrue(result.isPresent());
    assertEquals(ideiaId, result.get().getId());
    verify(ideiasRepository, times(1)).findById(ideiaId);
  }

  @Test
  public void testUpdateIdeias() {
    Long ideiaId = 1L;
    Ideias ideia = new Ideias();
    ideia.setId(ideiaId);
    ideia.setNome("Ideia Atualizada");

    when(ideiasRepository.existsById(ideiaId)).thenReturn(true);
    when(ideiasRepository.save(ideia)).thenReturn(ideia);

    Ideias updatedIdeia = ideiasService.updateIdeias(ideiaId, ideia);

    assertNotNull(updatedIdeia);
    assertEquals("Ideia Atualizada", updatedIdeia.getNome());
    verify(ideiasRepository, times(1)).save(ideia);
  }

  @Test
  public void testDeleteIdeias() {
    Long ideiaId = 1L;

    doNothing().when(ideiasRepository).deleteById(ideiaId);

    ideiasService.deleteIdeias(ideiaId);

    verify(ideiasRepository, times(1)).deleteById(ideiaId);
  }

  @Test
  public void testDistribuirIdeiasComSucesso() {
    Long eventoId = 1L;

    Usuario jurado1 = new Usuario();
    jurado1.setId(1L);
    jurado1.setRole(RoleStatus.JURADO);
    Usuario jurado2 = new Usuario();
    jurado2.setId(2L);
    jurado2.setRole(RoleStatus.JURADO);
    List<Usuario> jurados = new ArrayList<>(Arrays.asList(jurado1, jurado2));

    Ideias ideia1 = new Ideias();
    Ideias ideia2 = new Ideias();
    List<Ideias> ideias = new ArrayList<>(Arrays.asList(ideia1, ideia2));

    Eventos evento = new Eventos();
    evento.setJurados(jurados);
    evento.setIdeias(ideias);

    when(eventosRepository.findById(eventoId)).thenReturn(Optional.of(evento));

    ideiasService.distribuirIdeias(eventoId);

    assertEquals(2, ideia1.getEvento().getJurados().size());
    assertEquals(2, ideia2.getEvento().getJurados().size());
    verify(ideiasRepository, times(2)).save(any(Ideias.class));
  }

  @Test
  public void testDistribuirIdeiasEventoNaoEncontrado() {
    Long eventoId = 1L;

    when(eventosRepository.findById(eventoId)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
      ideiasService.distribuirIdeias(eventoId);
    });

    assertEquals("Evento nao encontrado", exception.getMessage());
  }

  @Test
  public void testDistribuirIdeiasComPoucosJurados() {
    Long eventoId = 1L;

    Usuario jurado1 = new Usuario();
    jurado1.setId(1L);
    List<Usuario> jurados = Collections.singletonList(jurado1);

    Ideias ideia1 = new Ideias();
    List<Ideias> ideias = Collections.singletonList(ideia1);

    Eventos evento = new Eventos();
    evento.setJurados(jurados);
    evento.setIdeias(ideias);

    when(eventosRepository.findById(eventoId)).thenReturn(Optional.of(evento));

    Exception exception = assertThrows(RuntimeException.class, () -> {
      ideiasService.distribuirIdeias(eventoId);
    });

    assertEquals("Evento precisa ter no minimo 2 jurados", exception.getMessage());
  }
}
