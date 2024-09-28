package com.example.Product.BulkOperations;

import com.example.Product.ModelMapper.ProductRequest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BulkProductRequest {
    private List<ProductRequest> products;
}
