package com.example.Product.Specification;

import com.example.Product.Entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ProductSpecification {
    public static Specification<Product> getSpec(String productname, BigDecimal minPrice, BigDecimal maxPrice) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicate = new ArrayList<>();
            if (productname != null && !productname.isEmpty()) {
                predicate.add(criteriaBuilder.equal(root.get("name"), productname));
            }


            if (minPrice != null) {

                predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }


            if (maxPrice != null) {
                predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }


            return criteriaBuilder.and(predicate.toArray(predicate.toArray(new Predicate[0])));
        });
    }
}
