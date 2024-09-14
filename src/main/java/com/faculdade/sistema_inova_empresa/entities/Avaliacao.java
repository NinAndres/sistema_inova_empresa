package com.faculdade.sistema_inova_empresa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_avaliacoes")
public class Avaliacao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int nota;

  @ManyToOne
  @JoinColumn(name = "ideia_id")
  private Ideias ideia;

  @ManyToOne
  @JoinColumn(name = "jurado_id")
  private Usuario jurado;

  @ManyToOne
  @JoinColumn(name = "evento_id")
  private Eventos evento;

}
