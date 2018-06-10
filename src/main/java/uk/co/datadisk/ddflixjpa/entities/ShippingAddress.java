package uk.co.datadisk.ddflixjpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipping_address")
public class ShippingAddress extends AbstractDomainClass {

  @NotNull
  @Column(name = "shipping_address_number")
  private String shippingAddressNumber;

  @NotNull
  @Column(name = "shipping_address_name")
  private String shippingAddressName;

  @NotNull
  @Column(name = "shipping_address_street1")
  private String shippingAddressStreet1;

  @NotNull
  @Column(name = "shipping_address_street2")
  private String shippingAddressStreet2;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "shipping_address_city_id")
  private City shippingAddressCity;

  @NotNull
  @Column(name = "shipping_address_postcode")
  private String shippingAddressPostcode;

  @NotNull
  @Column(name = "default_address")
  private boolean defaultAddress;

  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;
}
