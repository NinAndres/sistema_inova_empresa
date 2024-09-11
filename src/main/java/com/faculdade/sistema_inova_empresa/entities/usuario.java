package com.faculdade.sistema_inova_empresa.entities;

import javax.management.relation.RoleStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_usuarios")
public class usuario {
  private Long id;
  private String nome;
  private String email;
  private String senha;
  private RoleStatus role;

}
