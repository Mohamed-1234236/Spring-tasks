package com.example.Product.ModelMapper;

import com.example.Product.Entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    @Autowired
    private ModelMapper modelMapper;
    public Product convertToEntity(ProductRequest productRequest){
        return modelMapper.map(productRequest,Product.class);
    }
    public ProductResponse convertToDto(Product product){
        return modelMapper.map(product,ProductResponse.class);
    }
}
