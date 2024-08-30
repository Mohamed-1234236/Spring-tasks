package com.example.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor(staticName = "bulid")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class Product {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

//    public Product(long id, String name , String description , BigDecimal price  , LocalDateTime createDate ) {
//        this.id = id;
//        this.createDate = createDate;
//        this.name = name;
//        this.description = description;
//        this.price = price;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "Name Should not be null")
    private String name;
    @NotNull(message = "Descrption should not be null")
    private String description;
    @NotNull(message = "Price should not be null")
    private BigDecimal price;
    @NotNull(message = "Date should not be null")
    private LocalDateTime createDate;

}
