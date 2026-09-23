package com.miniproject.category.entitites;

import com.miniproject.category.ActiveStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="travel_plan")
public class TravelPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false)
	private String planName;

	private String description;

	private BigInteger minimumBudget;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus;

    @CreationTimestamp
   private LocalDateTime createdDate;

    @UpdateTimestamp
   private LocalDateTime modifiedDate;

    private String created_by;

    private String modified_by;



}
