package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author kurstan
 * @created at 08.02.2023 2:43
 */
@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "programmer")
public class Address {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_id_generator"
    )
    @SequenceGenerator(
            name = "address_id_generator",
            sequenceName = "address_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "region_name")
    private String regionName;
    private String street;
    @Column(name = "phone_number")
    private Integer homeNumber;
    @OneToOne(mappedBy = "address")
    private Programmer programmer;
    @ManyToOne
    private Country country;

    public Address(String regionName, String street, Integer homeNumber) {
        this.regionName = regionName;
        this.street = street;
        this.homeNumber = homeNumber;
    }
}
