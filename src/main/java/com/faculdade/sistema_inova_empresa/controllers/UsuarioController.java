package com.faculdade.sistema_inova_empresa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.entities.enums.RoleStatus;
import com.faculdade.sistema_inova_empresa.services.UsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @PostMapping("/criar")
  public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
    Usuario novoUsuario = usuarioService.createUsuario(usuario);
    return ResponseEntity.ok(novoUsuario);
  }

  @GetMapping
  public ResponseEntity<List<Usuario>> getTodosUsuarios() {
    return ResponseEntity.ok(usuarioService.getAllUsuarios());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Usuario>> getUsuarioById(@PathVariable Long id) {
    return ResponseEntity.ok(usuarioService.getUsuarioById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
    return ResponseEntity.ok(usuarioService.updateUsuario(id, usuario));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
    usuarioService.deleteUsuario(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/setRole/{id}")
  public ResponseEntity<Usuario> setRole(@PathVariable Long id, @RequestBody RoleStatus role) {
    return ResponseEntity.ok(usuarioService.setRole(id, role));
  }
}
