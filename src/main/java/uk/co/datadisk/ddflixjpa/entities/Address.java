package uk.co.datadisk.ddflixjpa.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"city", "users"})
@ToString(exclude = {"city", "users"})
@Entity
@Table(name = "address")
public class Address extends AbstractDomainClass {

  @NotNull
  @Column(name = "number")
  private String number;

  @NotNull
  @Column(name = "name")
  private String name;

  @NotNull
  @Column(name = "street1")
  private String street1;

  @NotNull
  @Column(name = "street2")
  private String street2;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "city_id")
  private City city;

  @NotNull
  @Column(name = "postcode")
  private String postcode;

  @ManyToMany(mappedBy = "shippingAddresses")
  private Set<User> users = new HashSet<>();
}
