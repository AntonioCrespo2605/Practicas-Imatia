package com.imatia.campusdual.appamazing.model.dao;

import com.imatia.campusdual.appamazing.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Integer> {
}
