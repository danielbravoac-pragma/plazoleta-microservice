package com.pragma.plazoleta.infrastructure.output.jpa.entity;

import com.pragma.plazoleta.domain.model.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dish")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    @Lob
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "id_restaurant", nullable = false, foreignKey = @ForeignKey(name = "fk_dish_restaurant"))
    private RestaurantEntity restaurant;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CategoryDishEntity> categories = new HashSet<>();

    @Column(nullable = false)
    private Boolean isActive;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (isActive == null) {
            isActive = Boolean.TRUE;
        }
    }
}
