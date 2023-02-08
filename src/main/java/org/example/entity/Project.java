package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author kurstan
 * @created at 08.02.2023 2:43
 */
@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "programmers")
public class Project {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_id_generator"
    )
    @SequenceGenerator(
            name = "project_id_generator",
            sequenceName = "project_seq",
            allocationSize = 1
    )
    private Long id;
    private String name;
    private String description;
    @Column(name = "date_of_start")
    private LocalDate dateOfStart;
    @Column(name = "date_of_finish")
    private LocalDate dateOfFinish;
    private BigDecimal price;
    @ManyToMany
    private List<Programmer> programmers;

    public Project(String name, String description, LocalDate dateOfStart,
                   LocalDate dateOfFinish, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.dateOfStart = dateOfStart;
        this.dateOfFinish = dateOfFinish;
        this.price = price;
    }
}
