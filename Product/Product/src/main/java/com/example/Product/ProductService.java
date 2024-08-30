package com.example.Product;

import com.example.Product.Exceptions.ProductNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getAllProducts() {
        // Retrieving all products from the database
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id) throws ProductNotFound {
        Optional<Product> productByID=productRepository.findById(id);
        if(productByID.isPresent()) {
            return productRepository.findById(id);
        }
        else {
            throw new ProductNotFound("Product not found with id: " + id);
        }
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    void deleteProduct(long id){
        productRepository.deleteById(id);
    }

    public void updateProduct(long id, Product updatedProduct) throws ProductNotFound {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setCreateDate(updatedProduct.getCreateDate());
            productRepository.save(product); // Save the updated product
        } else {
            throw new ProductNotFound("Product not found with id: " + id);
        }
    }

    public void deleteProduct(Long id) throws ProductNotFound {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            productRepository.deleteById(id);
        }
        else {
            throw new ProductNotFound("Product not found with id: " + id);
        }
    }
}
