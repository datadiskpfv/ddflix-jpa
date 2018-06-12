package uk.co.datadisk.ddflixjpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"addresses"})
@Entity
@Table(name = "city")
public class City extends AbstractDomainClass {

  @NotNull
  @Column(name = "city")
  private String city;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "county_id")
  private County county;

  @OneToMany(mappedBy = "city")
  private Set<Address> addresses = new HashSet<>();

  @Override
  public String toString(){
    return city;
  }

}
