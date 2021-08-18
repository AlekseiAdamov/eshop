package ru.alekseiadamov.db.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "brand")
    private Set<Product> products;

    public Brand(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
