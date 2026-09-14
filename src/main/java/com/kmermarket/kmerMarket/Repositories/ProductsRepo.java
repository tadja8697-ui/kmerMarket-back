package com.kmermarket.kmerMarket.Repositories;

import com.kmermarket.kmerMarket.Entities.Products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepo extends JpaRepository<Products,Long> {
    List<Products> findByUserId(Long userId);
}
