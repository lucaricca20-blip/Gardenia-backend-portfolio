package com.betacom.pr.repositories;

import com.betacom.pr.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUsersUserName(String userName);
}
