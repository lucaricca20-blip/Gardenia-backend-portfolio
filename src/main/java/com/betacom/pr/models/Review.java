package com.betacom.pr.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String userName;
    private int rating;

    @Column(columnDefinition = "TEXT", length = 500)
    private String comment;

    @Column(name = "date", updatable = false)
    private LocalDateTime date;
}