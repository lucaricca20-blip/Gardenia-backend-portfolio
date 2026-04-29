package com.betacom.pr.Utilities;

import java.util.List;
import java.util.stream.Collectors;

import com.betacom.pr.dto.inputs.UserOrderReq;
import com.betacom.pr.dto.outputs.CategoryDTO;
import com.betacom.pr.dto.outputs.ImageDTO;
import com.betacom.pr.dto.outputs.ProductDTO;
import com.betacom.pr.dto.outputs.ShoppingCartDTO;
import com.betacom.pr.dto.outputs.SubcategoryDTO;
import com.betacom.pr.dto.outputs.UserOrderDTO;
import com.betacom.pr.dto.outputs.WishlistDTO;
import com.betacom.pr.enums.Status;
import com.betacom.pr.models.Address;
import com.betacom.pr.models.Category;
import com.betacom.pr.models.Image;
import com.betacom.pr.models.Product;
import com.betacom.pr.models.ShoppingCart;
import com.betacom.pr.models.Subcategory;
import com.betacom.pr.models.UserOrder;
import com.betacom.pr.models.Wishlist;

public class Mapper {

    // --- CATEGORIE ---
    public static CategoryDTO buildCategoryDTO(Category c) {
        if (c == null) return null;
        return CategoryDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .build();
    }

    public static List<CategoryDTO> buildCategoryDTO(List<Category> lc) {
        return lc == null ? null : lc.stream().map(Mapper::buildCategoryDTO).collect(Collectors.toList());
    }

    // --- SOTTOCATEGORIE ---
    public static SubcategoryDTO buildSubcategoryDTO(Subcategory s) {
        if (s == null) return null;
        return SubcategoryDTO.builder()
                .id(s.getId())
                .subcategoryName(s.getName())
                .categoryId((s.getCategory() == null) ? null : s.getCategory().getId())
                .build();
    }

    public static List<SubcategoryDTO> buildSubcategoryDTO(List<Subcategory> ls) {
        return ls == null ? null : ls.stream().map(Mapper::buildSubcategoryDTO).collect(Collectors.toList());
    }

    // --- IMMAGINI ---
    public static ImageDTO buildImageDTO(Image i) {
        if (i == null) return null;
        return ImageDTO.builder()
                .imageId(i.getId())
                .link(i.getLink()) 
                .productId((i.getProduct() == null) ? null : i.getProduct().getId())
                .build();
    }

    public static List<ImageDTO> buildImageDTO(List<Image> li) {
        return li == null ? null : li.stream().map(Mapper::buildImageDTO).collect(Collectors.toList());
    }

    // --- PRODOTTI ---
    public static ProductDTO buildProductDTO(Product p) {
        if (p == null) return null;        
        return ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stock(p.getStock())
                .isDeleted(p.getIsDeleted())
                .subcategoryId((p.getSubcategory() == null) ? null : p.getSubcategory().getId())
                .subcategoryName((p.getSubcategory() == null) ? null : p.getSubcategory().getName())
                .images(p.getImages() == null ? null : p.getImages().stream()
                        .map(img -> ImageDTO.builder()
                                .imageId(img.getId())
                                .link(img.getLink())
                                .productId(p.getId())
                                .build())
                        .toList())
                .build();
    }

    public static List<ProductDTO> buildProductDTO(List<Product> lp) {
        return lp == null ? null : lp.stream().map(Mapper::buildProductDTO).collect(Collectors.toList());
    }
    
    // --- CARRELLO ---
    public static ShoppingCartDTO buildShoppingCartDTO(ShoppingCart s) {
        if (s == null) return null;
        
        ShoppingCartDTO.ShoppingCartDTOBuilder builder = ShoppingCartDTO.builder()
                .id(s.getId())
                .amount(s.getAmount())
                .price(s.getPrice());

        if (s.getProduct() != null) {
            builder.idProduct(s.getProduct().getId())
                   .nome(s.getProduct().getName())
                   .productStock(s.getProduct().getStock());
            
            if (s.getProduct().getImages() != null && !s.getProduct().getImages().isEmpty()) {
                builder.immagine(s.getProduct().getImages().get(0).getLink());
            }
        }
        
        return builder.build();
    }

    public static List<ShoppingCartDTO> buildShoppingCartDTO(List<ShoppingCart> ls) {
        return ls == null ? null : ls.stream().map(Mapper::buildShoppingCartDTO).collect(Collectors.toList());
    }

    // --- ORDINI ---
    public static UserOrderDTO buildUserOrderDTO(UserOrder order) {
        if (order == null) return null;

        return UserOrderDTO.builder()
                .id(order.getId())
                .wharehouse(order.getWharehouse())
                .isPaid(order.getIsPaid())
                .userName(order.getUser() != null ? order.getUser().getUserName() : null)
                .addressId(order.getAddress() != null ? order.getAddress().getId() : null)
                .date(order.getDate())
                .totalPrice(order.getTotalPrice())
                .statusDescription(order.getStatus() != null ? order.getStatus().toString() : "PENDING")
                .items(order.getProducts() != null ? buildShoppingCartDTO(order.getProducts()) : null)
                .build();
    }

    public static List<UserOrderDTO> buildUserOrderDTO(List<UserOrder> lo) {
        return lo == null ? null : lo.stream().map(Mapper::buildUserOrderDTO).collect(Collectors.toList());
    }

    // --- ORDINI ---
    public static UserOrder buildUserOrder(UserOrderReq req) {
        if (req == null) return null;
        
        UserOrder order = new UserOrder();
        order.setWharehouse(req.getWharehouse());
        order.setIsPaid(req.getIsPaid() != null ? req.getIsPaid() : false);
        order.setDate(req.getDate());
        order.setTotalPrice(req.getTotalPrice());

        if (req.getStatus() != null) {
            try {
                order.setStatus(Status.valueOf(req.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                order.setStatus(Status.PENDING);
            }
        } else {
            order.setStatus(Status.PENDING);
        }

        return order;
    }
    
    	// --- WISHLIST ---
    public static WishlistDTO buildWishlistDTO(Wishlist w) {
        String imagePath = null;
        
        if (w.getProduct().getImages() != null && !w.getProduct().getImages().isEmpty()) {
            imagePath = w.getProduct().getImages().get(0).getLink(); 
        }

        return WishlistDTO.builder()
                .id(w.getId())
                .productId(w.getProduct().getId())
                .productName(w.getProduct().getName())
                .price(w.getProduct().getPrice())
                .productStock(w.getProduct().getStock()) 
                .immagine(imagePath)
                .build();
    }
    
    public static List<WishlistDTO> buildWishlistDTOList(List<Wishlist> list) {
        return list.stream()
                .map(Mapper::buildWishlistDTO)
                .collect(Collectors.toList());
    }
}