package com.example.Product;

import com.example.Product.Exceptions.ProductNotFound;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController

public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public List<Product> GetProducts(){
return productService.getAllProducts();
    }

    @GetMapping("api/products/")
    public Optional<Product> getProductById(@RequestParam("id") long id) throws ProductNotFound {
        return productService.getProductById(id);
    }

    @PostMapping("/api/products")
    public Product add(@RequestBody @Valid Product product){
        return productService.createProduct(product);


    }


    @PutMapping("/api/products/")
    public void UpdateProduct(@RequestParam("id") long id ,@RequestBody @Valid Product product) throws ProductNotFound {
        productService.updateProduct(id, product);
    }

    @DeleteMapping("/api/products/")
    public void Delete(@RequestParam("id")  long id) throws ProductNotFound{
        productService.deleteProduct(id);
    }







}
