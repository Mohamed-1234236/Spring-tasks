package com.example.Product.Service;

import com.example.Product.BulkOperations.BulkInsertResponse;
import com.example.Product.BulkOperations.BulkProductRequest;
import com.example.Product.BulkOperations.ErrorResponse;
import com.example.Product.Exceptions.InvalidPriceRange;
import com.example.Product.ModelMapper.ProductRequest;
import com.example.Product.ModelMapper.ProductResponse;
import com.example.Product.Exceptions.ProductNameExisted;
import com.example.Product.Exceptions.ProductNotFound;
import com.example.Product.Entity.Product;
import com.example.Product.ModelMapper.ProductMapper;
import com.example.Product.Repository.ProductRepository;
import com.example.Product.Specification.ProductSpecification;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final ProductMapper productMapper;


    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    public Page<Product> getAllProducts(Integer page, Integer size, String sort) {
        log.debug("Fetching all products with page: {}, size: {}, sort: {}", page, size, sort);
        sort = sort.replace(" ", "");
        String[] parts = sort.split(",");
        if (parts[1].equals("asc")) {
            log.info("Fetching data was successful");
            return productRepository.findAll(PageRequest.of(page, size, Sort.by(parts[0]).ascending()));
        } else if (parts[1].equals("desc")) {
            log.info("Fetching data was successful");
            return productRepository.findAll(PageRequest.of(page, size, Sort.by(parts[0]).descending()));
        } else {
            log.error("Error fetching products: Sorting type not specified");
            throw new RuntimeException("Sorting type not specified");
        }
    }

    public ProductResponse getProductById(long id) {
        log.debug("Fetching product by id: {}", id);
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.error("Product not found with id: {}", id);
            return new ProductNotFound("Product not found with id: " + id);

        });
        log.info("Fetching product by id: {} was successful", id);
        return productMapper.convertToDto(product);
    }

    public List<Product> getProductbySpecification(String name, BigDecimal Minprice, BigDecimal Maxprice) {

        return productRepository.findAll(ProductSpecification.getSpec(name, Minprice, Maxprice));
    }

    public Product createProduct(@Valid ProductRequest productRequest) {
        log.debug("Checking if name existed before creating new product");
        Optional<Product> existingProductName = productRepository.findByName(productRequest.getName());
        if (existingProductName.isPresent()) {
            log.error("Failed to create product as product with name {} existed", productRequest.getName());
            throw new ProductNameExisted("Name existed");
        }
        Product product = productMapper.convertToEntity(productRequest);
        log.info("Product with name {} was successfully created.", product.getName());
        return productRepository.save(product);
    }


    public void updateProduct(long id, @Valid ProductRequest updatedProduct) {
        log.debug("Checking if Name existed before updating product info");
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Optional<Product> existingProductName = productRepository.findByName(updatedProduct.getName());
            if (existingProductName.isPresent()) {
                log.error("Failed to update product as product with name {} existed", updatedProduct.getName());
                throw new ProductNameExisted("Name existed");
            }
            Product product = existingProduct.get();
            log.info("Product with id: {} has been updated", id);
            productRepository.save(product);
        } else {
            log.error("Failed to update product as the product with id: {} not found ", id);
            throw new ProductNotFound("Product not found with id: " + id);
        }
    }

    public void deleteProduct(long id) {
        log.debug("Deleting the product with id: {}", id);
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            log.info("Deleting the product with id: {} was succesful", id);
            productRepository.deleteById(id);
        } else {
            log.error("Failed to delete the product with id: {} as id not found", id);
            throw new ProductNotFound("Product not found with id: " + id);
        }
    }

    public BulkInsertResponse deleteProducts(List<Long> ids) {
        log.debug("Initializing the attributes and methods of BulkInsertResponse object class for deleting a number of products");

        BulkInsertResponse response = new BulkInsertResponse();
        response.setSuccessfulDeletes(0);
        response.setFailedDeletes(new ArrayList<>());

        for (Long id : ids) {
            try {
                log.debug("Deleting the product with id: {}", id);
                deleteProduct(id);
                response.setSuccessfulDeletes(response.getSuccessfulDeletes() + 1);
            } catch (ProductNotFound e) {
                log.error("Failed to delete the product as error : {} occurred", e.getMessage());
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setId(id);
                errorResponse.setError(e.getMessage());
                response.getFailedDeletes().add(errorResponse);
            } catch (Exception e) {
                log.error("Failed to delete the product as error : {} occurred", e.getMessage());
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setId(id);
                errorResponse.setError("Validation failed: " + e.getMessage());
                response.getFailedDeletes().add(errorResponse);
            }
        }
        return response;
    }


    public BulkInsertResponse createProducts(BulkProductRequest bulkProductRequest) {

        log.debug("Initializing the attributes and methods of BulkInsertResponse object class");
        BulkInsertResponse response = new BulkInsertResponse();
        response.setSuccessfulInserts(0);
        response.setFailedInserts(new ArrayList<>());


        for (ProductRequest productRequest : bulkProductRequest.getProducts()) {
            try {
                log.debug("Creating the product with name: {}", productRequest.getName());
                createProduct(productRequest);
                response.setSuccessfulInserts(response.getSuccessfulInserts() + 1);
            } catch (ProductNameExisted | InvalidPriceRange e) {
                log.error("Failed to create the product with name : {} as error : {} occurred",productRequest.getName(), e.getMessage());
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setError("Failed to create the product with name : "+productRequest.getName()+" as "+e.getMessage());
                response.getFailedInserts().add(errorResponse);
            } catch (Exception e) {
                log.error("Failed to create the product as error : {} occurred", e.getMessage());
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setId(0L);
                errorResponse.setError("Validation failed: " + e.getMessage());
                response.getFailedInserts().add(errorResponse);
            }
        }
        return response;
    }
}
