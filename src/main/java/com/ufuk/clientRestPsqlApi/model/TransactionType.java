package com.ufuk.clientRestPsqlApi.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
