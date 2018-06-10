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
@Table(name = "country")
public class Country extends AbstractDomainClass {

  @NotNull
  @Column(name = "country")
  private String country;

  @OneToMany(mappedBy="country")
  private Set<County> counties = new HashSet<>();
}