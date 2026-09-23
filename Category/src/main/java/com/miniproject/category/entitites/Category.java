package com.miniproject.category.entitites;

import com.miniproject.category.ActiveStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "category")
public class Category {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @Column(
              name = "category_name",
              nullable = false,
              length = 30
      )
      private String categoryName;

      @Enumerated(EnumType.STRING)
      @Column(
              name = "active_sw",
              nullable = false,
              length = 10
      )
      private ActiveStatus activeSw;

      @CreationTimestamp
      @Column(
              name = "created_date",
              nullable = false,
              updatable = false
      )
      private LocalDateTime createDate;

      @UpdateTimestamp
      @Column(name = "updated_date")
      private LocalDateTime updateDate;

      @Column(name = "created_by")
      private String createdBy;

      @Column(name = "updated_by")
      private String updatedBy;

      @OneToMany(
              mappedBy = "category",
              cascade = CascadeType.ALL,
              orphanRemoval = true
      )
      private List<TravelPlan> plans = new ArrayList<>();
}