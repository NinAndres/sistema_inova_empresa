package com.faculdade.sistema_inova_empresa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.entities.enums.RoleStatus;
import com.faculdade.sistema_inova_empresa.repositories.UsuarioRepository;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository repository;

  public Usuario createUsuario(Usuario usuario) {
    return repository.save(usuario);
  }

  public List<Usuario> getAllUsuarios() {
    return repository.findAll();
  }

  public Optional<Usuario> getUsuarioById(Long id) {
    return repository.findById(id);
  }

  public Usuario updateUsuario(Long id, Usuario usuario) {
    if (repository.existsById(id)) {
      usuario.setId(id);
      return repository.save(usuario);
    }
    return null;
  }

  public void deleteUsuario(Long id) {
    repository.deleteById(id);
  }

  public Usuario setRole(Long id, RoleStatus role) {
    Optional<Usuario> usuarioOpt = repository.findById(id);
    if (usuarioOpt.isPresent()) {
      Usuario usuario = usuarioOpt.get();
      usuario.setRole(role);
      return repository.save(usuario);
    }
    return null;
  }

}
