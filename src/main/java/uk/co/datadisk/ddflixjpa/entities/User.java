package uk.co.datadisk.ddflixjpa.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "email", unique=true)
    private String email;
}