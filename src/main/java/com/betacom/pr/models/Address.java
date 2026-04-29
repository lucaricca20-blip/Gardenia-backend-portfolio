package com.betacom.pr.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table (uniqueConstraints = @UniqueConstraint(columnNames = {"country", "city", "postal_code", "street", "number"}))
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private Integer postalCode;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private Integer number;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();
    
//	@ManyToOne
//	@JoinColumn (name="address_id")
//	private List <User> userLiving;
}
