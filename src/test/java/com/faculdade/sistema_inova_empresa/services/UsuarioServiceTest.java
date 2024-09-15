package com.faculdade.sistema_inova_empresa.services;

import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.entities.enums.RoleStatus;
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
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceTest {

  @InjectMocks
  private UsuarioService usuarioService;

  @Mock
  private UsuarioRepository usuarioRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCreateUsuario() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("Teste");
    when(usuarioRepository.save(usuario)).thenReturn(usuario);

    Usuario createdUsuario = usuarioService.createUsuario(usuario);

    assertNotNull(createdUsuario);
    assertEquals(1L, createdUsuario.getId());
    assertEquals("Teste", createdUsuario.getNome());
  }

  @Test
  public void testGetAllUsuarios() {
    Usuario usuario1 = new Usuario();
    usuario1.setId(1L);
    Usuario usuario2 = new Usuario();
    usuario2.setId(2L);
    List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

    when(usuarioRepository.findAll()).thenReturn(usuarios);

    List<Usuario> fetchedUsuarios = usuarioService.getAllUsuarios();

    assertEquals(2, fetchedUsuarios.size());
    verify(usuarioRepository, times(1)).findAll();
  }

  @Test
  public void testGetUsuarioById() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

    Optional<Usuario> fetchedUsuario = usuarioService.getUsuarioById(1L);

    assertTrue(fetchedUsuario.isPresent());
    assertEquals(1L, fetchedUsuario.get().getId());
    verify(usuarioRepository, times(1)).findById(1L);
  }

  @Test
  public void testUpdateUsuario() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("Nome atualizado");

    when(usuarioRepository.existsById(1L)).thenReturn(true);
    when(usuarioRepository.save(usuario)).thenReturn(usuario);

    Usuario updatedUsuario = usuarioService.updateUsuario(1L, usuario);

    assertNotNull(updatedUsuario);
    assertEquals(1L, updatedUsuario.getId());
    assertEquals("Nome atualizado", updatedUsuario.getNome());
    verify(usuarioRepository, times(1)).existsById(1L);
    verify(usuarioRepository, times(1)).save(usuario);
  }

  @Test
  public void testDeleteUsuario() {
    Long usuarioId = 1L;

    doNothing().when(usuarioRepository).deleteById(usuarioId);

    usuarioService.deleteUsuario(usuarioId);

    verify(usuarioRepository, times(1)).deleteById(usuarioId);
  }

  @Test
  public void testSetRole() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setRole(RoleStatus.COLABORADOR);

    when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
    when(usuarioRepository.save(usuario)).thenReturn(usuario);

    Usuario updatedUsuario = usuarioService.setRole(1L, RoleStatus.ADMIN);

    assertNotNull(updatedUsuario);
    assertEquals(1L, updatedUsuario.getId());
    assertEquals(RoleStatus.ADMIN, updatedUsuario.getRole());
    verify(usuarioRepository, times(1)).findById(1L);
    verify(usuarioRepository, times(1)).save(usuario);
  }
}
