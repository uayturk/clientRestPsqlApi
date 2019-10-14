package com.ufuk.clientRestPsqlApi.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="TBL_TRANSACTION")
public class Transaction {

  @Id
  @GeneratedValue
  @Column(name="transactionId",updatable=false,nullable=false)
  private Long transactionId;

  @NotNull
  @Column(name="debit_account")
  private String debitAccount;

  @NotNull
  @Column(name="credit_account")
  private String creditAccount;

  @NotNull
  @Column(name="amount")
  private String amount;

  @NotNull
  @Column(name="message")
  private String message;

  @NotNull
  @Column(name="date_created")
  private String dateCreated;

}
