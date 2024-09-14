package com.faculdade.sistema_inova_empresa.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_eventos")
public class Eventos {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private String descricao;
  private LocalDateTime dataInicio;
  private LocalDateTime dataFim;
  private LocalDateTime dataAvalicaoJurado;
  private LocalDateTime dataAvaliacaoPopular;

  @ManyToOne
  @JoinColumn(name = "admin_id")
  private Usuario criador;

  @ManyToMany
  @JsonIgnore
  @JoinTable(name = "tb_jurados_evento", joinColumns = @JoinColumn(name = "evento_id"), inverseJoinColumns = @JoinColumn(name = "jurado_id"))
  private List<Usuario> jurados;

  @OneToMany(mappedBy = "evento")
  @JsonIgnore
  private List<Ideias> ideias;

}
