package com.ufuk.clientRestPsqlApi.model;


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

  @NotNull
  @Column(name="transaction_type")
  private TransactionType type;

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

  @Column(name="amount")
  private BigDecimal amount;

  @NotNull
  @Column(name="message")
  private String message;

  @NotNull
  @Column(name="date_created")
  private Date dateCreated;

  public Transaction(){ }

  public Transaction(TransactionType transactionType, Account debitAccount,Account creditAccount, BigDecimal amount,String message) {

    this.type = transactionType;
    this.debitAccount=debitAccount;
    this.creditAccount=creditAccount;
    this.amount=amount;
    this.message= message;
    this.dateCreated = new Date();
  }
}
