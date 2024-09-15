package com.faculdade.sistema_inova_empresa.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.faculdade.sistema_inova_empresa.entities.Eventos;
import com.faculdade.sistema_inova_empresa.entities.Ideias;
import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.entities.Votacao;
import com.faculdade.sistema_inova_empresa.repositories.EventosRepository;
import com.faculdade.sistema_inova_empresa.repositories.IdeiasRepository;
import com.faculdade.sistema_inova_empresa.repositories.UsuarioRepository;
import com.faculdade.sistema_inova_empresa.repositories.VotacaoRepository;

@SpringBootTest
public class VotacaoServiceTest {

  @Mock
  private IdeiasRepository ideiasRepository;

  @Mock
  private VotacaoRepository votacaoRepository;

  @Mock
  private EventosRepository eventosRepository;

  @Mock
  private UsuarioRepository usuarioRepository;

  @InjectMocks
  private VotacaoService votacaoService;

  public VotacaoServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testVotarComSucesso() {
    Long usuarioId = 1L;
    Long ideiaId = 1L;
    Long eventoId = 1L;

    Usuario usuario = new Usuario();
    usuario.setId(usuarioId);

    Ideias ideia = new Ideias();
    ideia.setId(ideiaId);

    Eventos evento = new Eventos();
    evento.setId(eventoId);
    evento.setDataInicio(LocalDateTime.now().minusDays(1));
    evento.setDataFim(LocalDateTime.now().plusDays(1));

    when(eventosRepository.findById(eventoId)).thenReturn(Optional.of(evento));
    when(ideiasRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));
    when(votacaoRepository.existsByUsuarioIdAndEventoId(usuarioId, eventoId)).thenReturn(false);
    when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

    votacaoService.votar(usuarioId, ideiaId, eventoId);

    verify(votacaoRepository, times(1)).save(any(Votacao.class));
  }

  @Test
  public void testVotarForaDoPeriodo() {
    Long usuarioId = 1L;
    Long ideiaId = 1L;
    Long eventoId = 1L;

    Eventos evento = new Eventos();
    evento.setId(eventoId);
    evento.setDataInicio(LocalDateTime.now().minusDays(2));
    evento.setDataFim(LocalDateTime.now().minusDays(1));

    when(eventosRepository.findById(eventoId)).thenReturn(Optional.of(evento));
    when(ideiasRepository.findById(ideiaId)).thenReturn(Optional.of(new Ideias()));
    when(votacaoRepository.existsByUsuarioIdAndEventoId(usuarioId, eventoId)).thenReturn(false);
    when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(new Usuario()));

    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      votacaoService.votar(usuarioId, ideiaId, eventoId);
    });

    assertEquals("O periodo de votocao esta fechado", thrown.getMessage());
  }

  @Test
  public void testUsuarioJaVotou() {
    Long usuarioId = 1L;
    Long ideiaId = 1L;
    Long eventoId = 1L;

    Eventos evento = new Eventos();
    evento.setId(eventoId);
    evento.setDataInicio(LocalDateTime.now().minusDays(1));
    evento.setDataFim(LocalDateTime.now().plusDays(1));

    when(eventosRepository.findById(eventoId)).thenReturn(Optional.of(evento));
    when(ideiasRepository.findById(ideiaId)).thenReturn(Optional.of(new Ideias()));
    when(votacaoRepository.existsByUsuarioIdAndEventoId(usuarioId, eventoId)).thenReturn(true);
    when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(new Usuario()));

    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      votacaoService.votar(usuarioId, ideiaId, eventoId);
    });

    assertEquals("Usuario ja votou nesse evento", thrown.getMessage());
  }

  @Test
  public void testObterTop10Ideias() {
    Long eventoId = 1L;

    Ideias ideia1 = new Ideias();
    ideia1.setId(1L);
    Ideias ideia2 = new Ideias();
    ideia2.setId(2L);

    List<Ideias> ideias = Arrays.asList(ideia1, ideia2);

    when(ideiasRepository.findByEventoId(eventoId)).thenReturn(ideias);
    when(votacaoRepository.countByIdeiaId(1L)).thenReturn(5);
    when(votacaoRepository.countByIdeiaId(2L)).thenReturn(10);

    List<Ideias> topIdeias = votacaoService.obterTop10Ideias(eventoId);

    assertNotNull(topIdeias);
    assertEquals(2, topIdeias.size());
    assertEquals(2L, topIdeias.get(0).getId());
  }
}
