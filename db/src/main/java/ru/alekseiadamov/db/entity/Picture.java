package ru.alekseiadamov.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "storage_uuid", nullable = false)
    private String storageUUID;

    @ManyToOne
    private Product product;

    public Picture(Long id, String name, String contentType, String storageUUID) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.storageUUID = storageUUID;
    }
}
