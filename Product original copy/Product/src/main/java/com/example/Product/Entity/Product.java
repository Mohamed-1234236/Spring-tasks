package com.example.Product.Entity;
import com.example.Product.Auditor.Auditable;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class Product extends Auditable<String> {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id Should not be null")
    private long id;

    @NotBlank(message = "Name is manditory")
    @NotNull(message = "Name Should not be null")
    private String name;


    @NotBlank(message = "Description is manditory")
    @NotNull(message = "Descrption should not be null")
    private String description;



    @NotNull(message = "Price should not be null")
    private BigDecimal price;


    @NotNull(message = "Date should not be null")
    @PastOrPresent(message = "Create date must be in the past or present")
    private LocalDateTime createDate;


}
