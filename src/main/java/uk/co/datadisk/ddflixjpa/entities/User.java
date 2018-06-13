package uk.co.datadisk.ddflixjpa.entities;

import lombok.*;
import uk.co.datadisk.ddflixjpa.entities.film.Film;
import uk.co.datadisk.ddflixjpa.entities.film.Wishlist;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"wishlist", "shippingAddresses"})
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_shipping_address")
    private Address default_shipping_address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_billing_address")
    private Address default_billing_address;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name="user_shipping_address",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="address_id")}
    )
    private Set<Address> shippingAddresses = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("wishedOn DESC")
    private List<Wishlist> wishlists = new ArrayList<>();

    public void addShippingAddress(Address shippingAddress) { this.shippingAddresses.add(shippingAddress);}
    public void removeShippingAddress(Address shippingAddress) { this.shippingAddresses.remove(shippingAddress);}

    public void addFilmToWishList(Film film) {
        if(!checkFilmInWishlist(film)) {
          wishlists.add(new Wishlist(this, film));
        } else {
            System.out.println("You already have " + film.getTitle() + " in your wishlist");
        }
    }

    public void removeFilmFromWishlist(Film film) {
        if(checkFilmInWishlist(film)){
          wishlists.remove(new Wishlist(this, film));
        } else {
            System.out.println("You don't have " + film.getTitle() + " in your wishlist");
        }
    }

    public boolean checkFilmInWishlist(Film film) {
        return wishlists.contains(new Wishlist(this, film));
    }

    // check the @OrderBy above
    public List<Wishlist> getSortedWishlistDesc(){
        return wishlists.stream().sorted(Comparator.comparing(Wishlist::getWishedOn).reversed()).collect(Collectors.toList());
    }

    // check the @OrderBy above
    public List<Wishlist> getSortedWishlistAsc(){
        return wishlists.stream().sorted(Comparator.comparing(Wishlist::getWishedOn)).collect(Collectors.toList());
    }
}