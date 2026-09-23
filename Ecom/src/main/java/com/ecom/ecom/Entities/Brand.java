package com.ecom.ecom.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="brands")
@Getter
@Setter
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String brandName;

    @Column(unique = true)
    private String slug;

    private String logoUrl;

    private String websiteUrl;

    public Boolean status=true;

    @OneToMany(mappedBy = "brand",cascade=CascadeType.ALL)
    private List<Product> products;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
