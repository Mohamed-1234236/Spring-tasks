package com.example.Product.Controller;
import com.example.Product.BulkOperations.BulkInsertResponse;
import com.example.Product.BulkOperations.BulkProductRequest;
import com.example.Product.ModelMapper.ProductRequest;
import com.example.Product.ModelMapper.ProductResponse;
import com.example.Product.Entity.Product;
import com.example.Product.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController

public class ProductController {
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public Page<Product> getProducts(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sort", required = false) String sort) {
        return productService.getAllProducts(page, size, sort);
    }


    @GetMapping("/api/products/specify")
    public List<Product> getProductsbySpecification(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "Minprice", required = false) BigDecimal Minprice,
            @RequestParam(value = "Maxprice", required = false) BigDecimal Maxprice) {
        return productService.getProductbySpecification(name, Minprice, Maxprice);
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable  long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/api/products")
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
        return new ResponseEntity<>("Saved successfully", HttpStatus.CREATED);
    }

    @PostMapping("/api/products/bulk")
    public ResponseEntity<BulkInsertResponse> createBulkProducts(@RequestBody BulkProductRequest bulkProductRequest) {
        BulkInsertResponse response = productService.createProducts(bulkProductRequest);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/api/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") long id,
                                                @Valid @RequestBody ProductRequest productRequest)
             {

        productService.updateProduct(id, productRequest);
        return new ResponseEntity<>("User with Id " + id + " has been successfully updated", HttpStatus.CREATED);
    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long id)  {

        productService.deleteProduct(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }


    @DeleteMapping("/api/products/bulk")
    public ResponseEntity<BulkInsertResponse> deleteBulkProducts(@RequestBody List<Long> ids) {
        BulkInsertResponse response = productService.deleteProducts(ids);
        return ResponseEntity.ok(response);
    }


}
