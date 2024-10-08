package com.faculdade.sistema_inova_empresa.entities;

import java.util.List;

import com.faculdade.sistema_inova_empresa.entities.enums.RoleStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_usuarios")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private String email;
  private String senha;

  @Enumerated(EnumType.STRING)
  private RoleStatus role;

  @OneToMany(mappedBy = "criador")
  @JsonIgnore
  private List<Eventos> eventos;

  @ManyToOne
  @JoinColumn(name = "ideia_id")
  private Ideias ideia;

  @ManyToMany(mappedBy = "jurados")
  @JsonIgnore
  private List<Eventos> eventosJurados;

  @OneToMany(mappedBy = "jurado")
  @JsonIgnore
  private List<Avaliacao> avaliacoesFeitas;
}
