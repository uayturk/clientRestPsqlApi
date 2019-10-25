package com.ufuk.clientRestPsqlApi.model;


import lombok.Getter;

/*@Entity
@Table(name="TBL_TRANSACTIONTYPE")*/
@Getter
public enum TransactionType {
  DR,
  CR
}

