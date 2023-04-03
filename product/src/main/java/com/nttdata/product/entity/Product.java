package com.nttdata.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data @AllArgsConstructor @NoArgsConstructor
@Document(collection="products")
public class Product {
    @Id
    @NotNull
    @NotEmpty(message = "ID can't be empty")
    private String id;
    private String type;
    private String name;
    private String numberAccount;
}
