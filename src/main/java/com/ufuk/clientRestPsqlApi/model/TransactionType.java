package com.ufuk.clientRestPsqlApi.model;


import lombok.Getter;

/*@Entity
@Table(name="TBL_TRANSACTIONTYPE")*/
@Getter
public enum TransactionType {
  DR,
  CR
}

/*
@Getter
@Setter
@ToString
@Entity
@Table(name="TBL_TRANSACTION_TYPE")
public class TransactionType{
  @Id
  @GeneratedValue
  @Column(name = "id",updatable=false,nullable=false)
  private String id;

  @Column(name = "lastUpdatedDate")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdatedDate;
}
*/
