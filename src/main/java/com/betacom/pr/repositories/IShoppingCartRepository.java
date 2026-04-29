package com.betacom.pr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.betacom.pr.models.ShoppingCart;
import com.betacom.pr.models.User;

import java.util.List;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>{
	
	List<ShoppingCart> findAllByUserOrder_Id(Integer userOrderId);
	
	@Query("SELECT sc FROM ShoppingCart sc WHERE sc.userOrder.user.userName = ?1 AND sc.userOrder.status = 'PENDING'")
	List<ShoppingCart> findActiveCartByUser(String userName);
	
	List<ShoppingCart> findByUserAndUserOrderIsNull(User user);
	
	List<ShoppingCart> findByUser_UserNameAndUserOrderIsNull(String userName);

}
