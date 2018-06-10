package uk.co.datadisk.ddflixjpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "city")
public class City extends AbstractDomainClass {

  @NotNull
  @Column(name = "city")
  private String city;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "county_id")
  private County county;

  @OneToMany(mappedBy = "shippingAddressCity")
  private Set<ShippingAddress> shippingAddresses = new HashSet<>();

}
