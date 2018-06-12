package uk.co.datadisk.ddflixjpa.entities.film;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.co.datadisk.ddflixjpa.entities.User;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "wishedOn"})
@Entity
@Table(name = "wishlist")
public class Wishlist {

    @EmbeddedId
    private WishlistId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("filmId")
    private Film film;

    @Column(name = "wished_on")
    private Date wishedOn = new Date();

    public Wishlist(User user, Film film) {
        this.user = user;
        this.film = film;
        this.id = new WishlistId(user.getId(), film.getId());
    }
}