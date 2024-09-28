package com.example.Product.BulkOperations;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long id;


    private String error;

}