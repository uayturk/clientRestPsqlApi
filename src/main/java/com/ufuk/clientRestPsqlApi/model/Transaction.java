package com.ufuk.clientRestPsqlApi.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

  @ManyToOne(cascade= CascadeType.ALL)
  @JoinColumn(name = "debitAccountId", nullable = false)
  @NotNull
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Account debitAccount;

  @ManyToOne(cascade= CascadeType.ALL)
  @JoinColumn(name = "creditAccountId", nullable = false)
  @NotNull
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Account creditAccount;

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
