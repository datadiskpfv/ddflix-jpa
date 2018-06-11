package uk.co.datadisk.ddflixjpa.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"counties"})
@Entity
@Table(name = "country")
public class Country extends AbstractDomainClass {

  @NotNull
  @Column(name = "country", unique = true)
  private String country;

  @OneToMany(mappedBy="country", fetch = FetchType.EAGER)
  private Set<County> counties = new HashSet<>();

  @Override
  public String toString(){
    return country;
  }
}