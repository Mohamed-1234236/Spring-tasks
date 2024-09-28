package com.example.Product.ModelMapper;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductResponse {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime createDate;
    private String LastModifiedBy;
    private LocalDateTime LastModifiedDate;
    private String CreatedBy;
    private LocalDateTime CreatedDate;
}

