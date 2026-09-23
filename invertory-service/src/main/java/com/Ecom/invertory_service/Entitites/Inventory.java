package com.Ecom.invertory_service.Entitites;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.Generated;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer availableQuantity=0;

    @Column(nullable = false)
    private Integer reservedQuantity=0;
    @Column(nullable = false)
    private Integer reorderLevel=5;

    @Version
    private Long version;
}
