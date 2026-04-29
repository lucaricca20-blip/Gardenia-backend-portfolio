package com.betacom.pr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betacom.pr.models.UserOrder;

@Repository
public interface IUserOrderRepository extends JpaRepository<UserOrder, Integer>{

    List<UserOrder> findAllByUser_UserName(String username);

}
