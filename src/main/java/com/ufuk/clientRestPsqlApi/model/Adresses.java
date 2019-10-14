package com.ufuk.clientRestPsqlApi.model;

import java.util.HashSet;
import java.util.Set;
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

@Getter
@Setter
@ToString
@Entity
@Table(name="TBL_ADDRESSES")
public class Adresses {

 /* @ManyToOne
  @JoinColumn(name="clientId",referencedColumnName = "clientId")*/
  //private Set<Client> client;

  @Id
  @GeneratedValue
  @Column(name="addressId",updatable=false,nullable=false)
  private Long addressId;

  @NotNull
  @Column(name="addressLine1")
  private String addressLine1;

  @NotNull
  @Column(name="addressLine2")
  private String addressLine2;

  @NotNull
  @Column(name="city")
  private String city;

  @NotNull
  @Column(name="country")
  private String country;


}
