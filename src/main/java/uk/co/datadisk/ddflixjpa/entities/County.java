package uk.co.datadisk.ddflixjpa.entities;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"cities"})
@Entity
@Table(name = "county")
public class County extends AbstractDomainClass {

  @NotNull
  @Column(name = "county")
  private String county;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "country_id")
  private Country country;

  @OneToMany(mappedBy="county")
  private Set<City> cities = new HashSet<>();

  @Override
  public String toString(){
    return county;
  }

}
