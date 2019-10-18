package com.ufuk.clientRestPsqlApi.model;


import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@ToString
@Entity
@Table(name="TBL_ACCOUNT")
@AllArgsConstructor
@NoArgsConstructor
public class Account {

  @Id
  @GeneratedValue
  private Long accountId;

  @NotNull
  @Column(name="balance_status")
  private BalanceStatus balanceStatus;

  @NotNull
  @Column(name="type")
  private Type type;

  @NotNull
  @Column(name="balance")
  private BigDecimal balance = BigDecimal.ZERO;//From a code quality perspective, using BigDecimal.ZERO is preferable to new BigDecimal(0)
                                               // as you avoid the extra instantiation and having a literal in your code.

  @ManyToOne(cascade= CascadeType.ALL)//1 client can have multiple account. Thats why we used @ManyToOne annotation here.
  @JoinColumn(name = "clientAccountId", nullable = false)
  @NotNull
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Client client;

}
