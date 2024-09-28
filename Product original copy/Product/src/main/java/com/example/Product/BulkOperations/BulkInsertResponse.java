package com.example.Product.BulkOperations;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class BulkInsertResponse {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer successfulInserts;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ErrorResponse> failedInserts;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer successfulDeletes;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ErrorResponse> failedDeletes;

}
