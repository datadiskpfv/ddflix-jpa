package uk.co.datadisk.ddflixjpa.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public void addShippingAddress(Address shippingAddress) { this.shippingAddresses.add(shippingAddress);}
    public void removeShippingAddress(Address shippingAddress) { this.shippingAddresses.remove(shippingAddress);}

}