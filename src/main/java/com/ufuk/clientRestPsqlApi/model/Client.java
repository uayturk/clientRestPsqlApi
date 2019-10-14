package com.ufuk.clientRestPsqlApi.model;


import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
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
@Table(name="TBL_CLIENT")
public class Client {

  /*@ManyToOne
  @JoinColumn(name = "address_id")
  private Adresses adresses;*/

  @Id
  @GeneratedValue //If we want the primary key value to be generated automatically for us, we can add the @GeneratedValue annotation.
  private Long clientId;

  @NotNull
  @Column(name="first_name")
  private String firstName;
  @NotNull
  @Column(name="last_name")
  private String lastName;

  @ManyToOne(cascade=CascadeType.ALL)  //Many Client can be inside one address. That's why @ManyToOne used here.
  @JoinColumn(name = "primaryAddressId", nullable = false)
  @NotNull
  @OnDelete(action = OnDeleteAction.CASCADE)//This comment prevents the "violates foreign key constraint" error when using @ManyToOne.
  private Adresses primaryAddress;

  @ManyToOne(cascade=CascadeType.ALL)
  @JoinColumn(name = "secondaryAddressId", nullable = false)
  @NotNull
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Adresses secondaryAddress;

  /*@OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
  private Set<Account> account;*/
}
