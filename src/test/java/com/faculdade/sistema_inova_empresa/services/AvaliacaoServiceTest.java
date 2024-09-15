package com.faculdade.sistema_inova_empresa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.faculdade.sistema_inova_empresa.entities.Avaliacao;
import com.faculdade.sistema_inova_empresa.entities.Eventos;
import com.faculdade.sistema_inova_empresa.entities.Ideias;
import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.repositories.AvaliacaoRepository;
import com.faculdade.sistema_inova_empresa.repositories.IdeiasRepository;
import com.faculdade.sistema_inova_empresa.repositories.UsuarioRepository;

public class AvaliacaoServiceTest {

  @InjectMocks
  private AvaliacaoService avaliacaoService;

  @Mock
  private AvaliacaoRepository avaliacaoRepository;

  @Mock
  private IdeiasRepository ideiasRepository;

  @Mock
  private UsuarioRepository usuarioRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testAvaliarIdeiaComSucesso() {
    Long juradoId1 = 1L;
    Long juradoId2 = 2L;
    Long ideiaId = 1L;
    int nota1 = 8;
    int nota2 = 9;

    Eventos evento = new Eventos();
    evento.setNome("Evento teste");

    Ideias ideia = new Ideias();
    ideia.setId(ideiaId);
    ideia.setEvento(evento);

    Usuario jurado1 = new Usuario();
    jurado1.setId(juradoId1);

    Usuario jurado2 = new Usuario();
    jurado2.setId(juradoId2);

    evento.setJurados(Arrays.asList(jurado1, jurado2));

    when(ideiasRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));
    when(usuarioRepository.findById(juradoId1)).thenReturn(Optional.of(jurado1));
    when(usuarioRepository.findById(juradoId2)).thenReturn(Optional.of(jurado2));

    Avaliacao avaliacao1 = new Avaliacao();
    avaliacao1.setIdeia(ideia);
    avaliacao1.setJurado(jurado1);
    avaliacao1.setNota(nota1);

    when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacao1);

    Avaliacao resultado1 = avaliacaoService.avaliarIdeia(juradoId1, ideiaId, nota1);

    assertEquals(nota1, resultado1.getNota());
    verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));

    Avaliacao avaliacao2 = new Avaliacao();
    avaliacao2.setIdeia(ideia);
    avaliacao2.setJurado(jurado2);
    avaliacao2.setNota(nota2);

    when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacao2);

    Avaliacao resultado2 = avaliacaoService.avaliarIdeia(juradoId2, ideiaId, nota2);

    assertEquals(nota2, resultado2.getNota());
    verify(avaliacaoRepository, times(2)).save(any(Avaliacao.class));
  }

  @Test
  public void testAvaliarIdeiaJuradoNaoPertenceAoEvento() {
    Long juradoId = 1L;
    Long ideiaId = 1L;
    int nota = 8;

    Ideias ideia = new Ideias();
    ideia.setId(ideiaId);

    Usuario jurado = new Usuario();
    jurado.setId(juradoId);

    when(ideiasRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));
    when(usuarioRepository.findById(juradoId)).thenReturn(Optional.of(jurado));

    assertThrows(RuntimeException.class, () -> avaliacaoService.avaliarIdeia(juradoId, ideiaId, nota));
  }

  @Test
  public void testAvaliarIdeiaNotaForaDoIntervalo() {
    Long juradoId = 1L;
    Long ideiaId = 1L;
    int nota = 2;

    Ideias ideia = new Ideias();
    ideia.setId(ideiaId);

    Usuario jurado = new Usuario();
    jurado.setId(juradoId);

    when(ideiasRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));
    when(usuarioRepository.findById(juradoId)).thenReturn(Optional.of(jurado));

    assertThrows(RuntimeException.class, () -> avaliacaoService.avaliarIdeia(juradoId, ideiaId, nota));
  }

  @Test
  public void testMediaDasAvalicoesComSucesso() {
    Long ideiaId = 1L;

    Ideias ideia = new Ideias();
    ideia.setId(ideiaId);

    Avaliacao avaliacao1 = new Avaliacao();
    avaliacao1.setNota(7);

    Avaliacao avaliacao2 = new Avaliacao();
    avaliacao2.setNota(9);

    ideia.setAvaliacoes(Arrays.asList(avaliacao1, avaliacao2));

    when(ideiasRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));

    Double media = avaliacaoService.mediaDasAvalicoes(ideiaId);

    assertEquals(8.0, media);
  }

  @Test
  public void testMediaDasAvalicoesMenosDeDoisJurados() {
    Long ideiaId = 1L;

    Ideias ideia = new Ideias();
    ideia.setId(ideiaId);

    Avaliacao avaliacao1 = new Avaliacao();
    avaliacao1.setNota(7);

    ideia.setAvaliacoes(Arrays.asList(avaliacao1));

    when(ideiasRepository.findById(ideiaId)).thenReturn(Optional.of(ideia));

    assertThrows(RuntimeException.class, () -> avaliacaoService.mediaDasAvalicoes(ideiaId));
  }

  @Test
  public void testSelecaoDasMelhoresIdeias() {
    Long eventoId = 1L;

    Ideias ideia1 = new Ideias();
    ideia1.setId(1L);
    Ideias ideia2 = new Ideias();
    ideia2.setId(2L);
    Ideias ideia3 = new Ideias();
    ideia3.setId(3L);

    Avaliacao avaliacao1 = new Avaliacao();
    avaliacao1.setNota(9);
    Avaliacao avaliacao2 = new Avaliacao();
    avaliacao2.setNota(7);
    Avaliacao avaliacao3 = new Avaliacao();
    avaliacao3.setNota(10);

    ideia1.setAvaliacoes(Arrays.asList(avaliacao1));
    ideia2.setAvaliacoes(Arrays.asList(avaliacao2));
    ideia3.setAvaliacoes(Arrays.asList(avaliacao3));

    List<Ideias> ideias = Arrays.asList(ideia1, ideia2, ideia3);

    when(ideiasRepository.findByEventoId(eventoId)).thenReturn(ideias);

    List<Ideias> melhoresIdeias = avaliacaoService.selecaoDasMelhoresIdeias(eventoId);

    assertEquals(3, melhoresIdeias.size());
    assertEquals(3L, melhoresIdeias.get(0).getId());
    assertEquals(1L, melhoresIdeias.get(1).getId());
    assertEquals(2L, melhoresIdeias.get(2).getId());
  }
}
