package com.faculdade.sistema_inova_empresa.services;

import com.faculdade.sistema_inova_empresa.entities.Eventos;
import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.entities.enums.RoleStatus;
import com.faculdade.sistema_inova_empresa.repositories.EventosRepository;
import com.faculdade.sistema_inova_empresa.repositories.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EventosServiceTest {

  @InjectMocks
  private EventosService eventosService;

  @Mock
  private EventosRepository eventosRepository;

  @Mock
  private UsuarioRepository usuarioRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCriarEventos_AdminUsuario() {
    Long usuarioId = 1L;
    Usuario admin = new Usuario();
    admin.setId(usuarioId);
    admin.setRole(RoleStatus.ADMIN);

    Eventos evento = new Eventos();
    evento.setNome("Evento Teste");

    when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(admin));
    when(eventosRepository.save(evento)).thenReturn(evento);

    Eventos createdEvento = eventosService.criarEventos(evento, usuarioId);

    assertNotNull(createdEvento);
    assertEquals("Evento Teste", createdEvento.getNome());
    assertEquals(admin, createdEvento.getCriador());
    verify(eventosRepository, times(1)).save(evento);
  }

  @Test
  public void testCriarEventos_NonAdminUsuario() {
    Long usuarioId = 1L;
    Usuario colaborador = new Usuario();
    colaborador.setId(usuarioId);
    colaborador.setRole(RoleStatus.COLABORADOR);

    Eventos evento = new Eventos();
    evento.setNome("Evento Teste");

    when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(colaborador));

    Exception exception = assertThrows(RuntimeException.class, () -> {
      eventosService.criarEventos(evento, usuarioId);
    });

    assertEquals("Apenas administradores podem criar eventos", exception.getMessage());
    verify(eventosRepository, times(0)).save(evento);
  }

  @Test
  public void testSelecionarJurados_AdminUsuario() {
    Long eventoId = 1L;
    Long adminId = 1L;
    Long juradoId = 2L;

    Usuario admin = new Usuario();
    admin.setId(adminId);
    admin.setRole(RoleStatus.ADMIN);

    Usuario jurado = new Usuario();
    jurado.setId(juradoId);
    jurado.setRole(RoleStatus.JURADO);

    Eventos evento = new Eventos();
    evento.setId(eventoId);
    evento.setNome("Evento Teste");

    when(eventosRepository.findById(eventoId)).thenReturn(Optional.of(evento));
    when(usuarioRepository.findById(adminId)).thenReturn(Optional.of(admin));
    when(usuarioRepository.findAllById(Arrays.asList(juradoId))).thenReturn(Arrays.asList(jurado));
    when(eventosRepository.save(any(Eventos.class))).thenReturn(evento);

    Eventos updatedEvento = eventosService.selecionarJurados(eventoId, Arrays.asList(juradoId), adminId);

    assertNotNull(updatedEvento);
    assertEquals(1, updatedEvento.getJurados().size());
    assertEquals(jurado, updatedEvento.getJurados().get(0));
    verify(eventosRepository, times(1)).save(evento);
  }

  @Test
  public void testSelecionarJurados_NonAdminUsuario() {
    Long eventoId = 1L;
    Long colaboradorId = 1L;

    Usuario colaborador = new Usuario();
    colaborador.setId(colaboradorId);
    colaborador.setRole(RoleStatus.COLABORADOR);

    Eventos evento = new Eventos();
    evento.setId(eventoId);
    evento.setNome("Evento Teste");

    when(eventosRepository.findById(eventoId)).thenReturn(Optional.of(evento));
    when(usuarioRepository.findById(colaboradorId)).thenReturn(Optional.of(colaborador));

    Exception exception = assertThrows(RuntimeException.class, () -> {
      eventosService.selecionarJurados(eventoId, Arrays.asList(2L), colaboradorId);
    });

    assertEquals("Apenas administradores podem selecionar jurados", exception.getMessage());
    verify(eventosRepository, times(0)).save(any(Eventos.class));
  }

  @Test
  public void testGetAllEventos() {
    Eventos evento1 = new Eventos();
    evento1.setId(1L);
    Eventos evento2 = new Eventos();
    evento2.setId(2L);
    List<Eventos> eventosList = Arrays.asList(evento1, evento2);

    when(eventosRepository.findAll()).thenReturn(eventosList);

    List<Eventos> result = eventosService.getAllEventos();

    assertEquals(2, result.size());
    verify(eventosRepository, times(1)).findAll();
  }

  @Test
  public void testGetEventosById() {
    Long eventoId = 1L;
    Eventos evento = new Eventos();
    evento.setId(eventoId);

    when(eventosRepository.findById(eventoId)).thenReturn(Optional.of(evento));

    Optional<Eventos> result = eventosService.getEventosById(eventoId);

    assertTrue(result.isPresent());
    assertEquals(eventoId, result.get().getId());
    verify(eventosRepository, times(1)).findById(eventoId);
  }

  @Test
  public void testUpdateEventos() {
    Long eventoId = 1L;
    Eventos evento = new Eventos();
    evento.setId(eventoId);
    evento.setNome("Nome Atualizado");

    when(eventosRepository.existsById(eventoId)).thenReturn(true);
    when(eventosRepository.save(evento)).thenReturn(evento);

    Eventos updatedEvento = eventosService.updateEventos(eventoId, evento);

    assertNotNull(updatedEvento);
    assertEquals("Nome Atualizado", updatedEvento.getNome());
    verify(eventosRepository, times(1)).save(evento);
  }

  @Test
  public void testDeleteEventos() {
    Long eventoId = 1L;

    doNothing().when(eventosRepository).deleteById(eventoId);

    eventosService.deleteEventos(eventoId);

    verify(eventosRepository, times(1)).deleteById(eventoId);
  }
}
