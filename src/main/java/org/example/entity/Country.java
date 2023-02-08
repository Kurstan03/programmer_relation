package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author kurstan
 * @created at 08.02.2023 2:53
 */
@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "address")
public class Country {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "country_id_generator"
    )
    @SequenceGenerator(
            name = "country_id_generator",
            sequenceName = "generator_seq",
            allocationSize = 1
    )
    private Long id;
    private String country;
    private String description;
    @OneToMany(mappedBy = "country")
    private List<Address> address;

    public Country(String country, String description) {
        this.country = country;
        this.description = description;
    }
}
