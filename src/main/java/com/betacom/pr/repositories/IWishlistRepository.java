package com.betacom.pr.repositories;

import com.betacom.pr.models.Wishlist;
import com.betacom.pr.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IWishlistRepository extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findByUser_UserName(String userName);
    Optional<Wishlist> findByUser_UserNameAndProduct_Id(String userName, Integer productId);
}