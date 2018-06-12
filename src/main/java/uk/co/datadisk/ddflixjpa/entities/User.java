package uk.co.datadisk.ddflixjpa.entities;


import lombok.*;
import uk.co.datadisk.ddflixjpa.entities.film.Film;
import uk.co.datadisk.ddflixjpa.entities.film.Wishlist;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"wishlist"})
@Entity
@Table(name = "users")
public class User extends AbstractDomainClass {

    @Builder
    public User(Long id, String email) {
        super(id);
        this.email = email;
    }

    @NotNull
    @Column(name = "email", unique=true)
    private String email;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name="user_shipping_address",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="address_id")}
    )
    private Set<Address> shippingAddresses = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Wishlist> wishlists = new ArrayList<>();

    public void addShippingAddress(Address shippingAddress) { this.shippingAddresses.add(shippingAddress);}
    public void removeShippingAddress(Address shippingAddress) { this.shippingAddresses.remove(shippingAddress);}

    public void addFilmToWishList(Film film) {
        wishlists.add(new Wishlist(this, film));
    }
    public void removeFilmFromWishlist(Film film) {
        wishlists.remove(new Wishlist(this, film));
    }
}