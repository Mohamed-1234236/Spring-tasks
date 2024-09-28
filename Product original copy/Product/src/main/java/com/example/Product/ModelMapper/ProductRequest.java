package com.example.Product.ModelMapper;
import com.example.Product.Auditor.Auditable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductRequest extends Auditable<String> {

    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name should not be null")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @NotNull(message = "Description should not be null")
    private String description;

    @NotNull(message = "Price should not be null")
    private BigDecimal price;

    @NotNull(message = "Date should not be null")
    @PastOrPresent(message = "Create date must be in the past or present")
    private LocalDateTime createDate;

}

