package com.faculdade.sistema_inova_empresa.entities.enums;

public enum RoleStatus {
  ADMIN(1),
  COLABORADOR(2),
  AVALIADOR(3);

  private int code;

  private RoleStatus(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public static RoleStatus valueOf(int code) {
    for (RoleStatus value : RoleStatus.values()) {
      if (value.getCode() == code) {
        return value;
      }
    }
    throw new IllegalArgumentException("Invalid roleStatus code");
  }

}
