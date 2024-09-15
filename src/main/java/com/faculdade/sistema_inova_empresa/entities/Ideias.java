package com.faculdade.sistema_inova_empresa.entities;

import java.util.List;

import com.faculdade.sistema_inova_empresa.entities.enums.ImpactoStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_ideias")
public class Ideias {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;

  @Enumerated(EnumType.STRING)
  private ImpactoStatus impacto;
  private Float custo;

  @Size(max = 1000, message = "A descricao passou de 1000 caracteres")
  private String descricao;

  @ManyToOne
  @JoinColumn(name = "admin_id")
  private Usuario criador;

  @OneToMany(mappedBy = "ideia")
  @JsonIgnore
  private List<Usuario> colaboradores;

  @ManyToOne
  @JoinColumn(name = "evento_id")
  private Eventos evento;

  @OneToMany(mappedBy = "ideia")
  @JsonIgnore
  private List<Avaliacao> avaliacoes;
}
