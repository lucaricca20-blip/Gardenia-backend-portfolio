package com.betacom.pr.models;

import java.util.ArrayList;
import java.util.List;

import com.betacom.pr.enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name="\"user\"")
public class User {
	
	@Id
	private String userName;
	
	@Column (length = 100, nullable = false)
	private String firstName;
	
	@Column (length = 100, nullable = false)
	private String lastName;
	
	@Column (length = 100, nullable = false, unique = true)
	private String email;
	
	@Column (length = 100, nullable = false)
	private String phone;
	
	@Column (length = 100, nullable = false)
	private String password;
	
	@Column (length = 100, nullable = false)
	private Roles role;

	@Column(nullable = false)
	private Boolean enabled = false;
	@ManyToMany
    @JoinTable(
        name = "user_address",
        joinColumns = @JoinColumn(name = "user_name"),
        inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses = new ArrayList<>();
}
