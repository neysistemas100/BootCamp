package com.nttdata.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("customers")
public class Customer implements Serializable {
    @Id
    @NotNull
    @NotEmpty(message = "ID can't be empty")
    private String id;
    private String type;
    private String name;
    private String number_document;
    private String cellNumberPhone;
    private String imei;
    private String mail;
    private String state;
}
