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
@Table(name = "county")
public class County extends AbstractDomainClass {

  @NotNull
  @Column(name = "county")
  private String county;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "country_id")
  private Country country;

  @OneToMany(mappedBy="county")
  private Set<City> cities = new HashSet<>();

}
