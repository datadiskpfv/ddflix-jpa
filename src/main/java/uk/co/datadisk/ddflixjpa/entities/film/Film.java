package uk.co.datadisk.ddflixjpa.entities.film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.datadisk.ddflixjpa.entities.AbstractDomainClass;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "film")
public class Film extends AbstractDomainClass {

    @NotNull
    private String title;

    @OneToMany(
            mappedBy = "film",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Wishlist> users = new ArrayList<>();
}
