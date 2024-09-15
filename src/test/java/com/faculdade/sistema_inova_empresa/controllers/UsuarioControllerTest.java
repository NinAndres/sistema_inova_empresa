package com.faculdade.sistema_inova_empresa.controllers;

import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.entities.enums.RoleStatus;
import com.faculdade.sistema_inova_empresa.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioControllerTest {

  @InjectMocks
  private UsuarioController usuarioController;

  @Mock
  private UsuarioService usuarioService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCriarUsuario() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("Usuario Teste");

    when(usuarioService.createUsuario(any(Usuario.class))).thenReturn(usuario);

    ResponseEntity<Usuario> response = usuarioController.criarUsuario(usuario);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(usuario, response.getBody());
    verify(usuarioService, times(1)).createUsuario(any(Usuario.class));
  }

  @Test
  public void testGetTodosUsuarios() {
    Usuario usuario1 = new Usuario();
    usuario1.setId(1L);
    usuario1.setNome("Usuario 1");

    Usuario usuario2 = new Usuario();
    usuario2.setId(2L);
    usuario2.setNome("Usuario 2");

    when(usuarioService.getAllUsuarios()).thenReturn(Arrays.asList(usuario1, usuario2));

    ResponseEntity<List<Usuario>> response = usuarioController.getTodosUsuarios();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    verify(usuarioService, times(1)).getAllUsuarios();
  }

  @Test
  public void testGetUsuarioById() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("Usuario Teste");

    when(usuarioService.getUsuarioById(1L)).thenReturn(Optional.of(usuario));

    ResponseEntity<Optional<Usuario>> response = usuarioController.getUsuarioById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().isPresent());
    assertEquals(usuario, response.getBody().get());
    verify(usuarioService, times(1)).getUsuarioById(1L);
  }

  @Test
  public void testAtualizarUsuario() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setNome("Usuario Atualizado");

    when(usuarioService.updateUsuario(anyLong(), any(Usuario.class))).thenReturn(usuario);

    ResponseEntity<Usuario> response = usuarioController.atualizarUsuario(1L, usuario);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(usuario, response.getBody());
    verify(usuarioService, times(1)).updateUsuario(anyLong(), any(Usuario.class));
  }

  @Test
  public void testDeletarUsuario() {
    doNothing().when(usuarioService).deleteUsuario(1L);

    ResponseEntity<Void> response = usuarioController.deletarUsuario(1L);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(usuarioService, times(1)).deleteUsuario(1L);
  }

  @Test
  public void testSetRole() {
    Usuario usuario = new Usuario();
    usuario.setId(1L);
    usuario.setRole(RoleStatus.ADMIN);

    when(usuarioService.setRole(anyLong(), any(RoleStatus.class))).thenReturn(usuario);

    ResponseEntity<Usuario> response = usuarioController.setRole(1L, RoleStatus.ADMIN);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(RoleStatus.ADMIN, response.getBody().getRole());
    verify(usuarioService, times(1)).setRole(anyLong(), any(RoleStatus.class));
  }
}
