package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.enums.Status;

import java.time.LocalDate;
import java.util.List;

/**
 * @author kurstan
 * @created at 08.02.2023 2:42
 */
@Entity
@Table(name = "programmers")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "projects")
public class Programmer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "programmer_id_generator"
    )
    @SequenceGenerator(
            name = "programmer_id_generator",
            sequenceName = "programmer_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    private String email;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private Status status;
    @ManyToMany(mappedBy = "programmers")
    private List<Project> projects;
    @OneToOne
    private Address address;

    public Programmer(String fullName, String email, LocalDate dateOfBirth, Status status) {
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
    }
}
