package com.nabinsingh34.ecommerc.repo;


import com.nabinsingh34.ecommerc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

}
