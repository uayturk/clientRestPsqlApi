package com.ufuk.clientRestPsqlApi.model;

import javax.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public enum TransactionType {
  DR,
  CR
}
