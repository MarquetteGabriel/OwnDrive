package com.pamarg.fileapi.repo;

import com.pamarg.fileapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}