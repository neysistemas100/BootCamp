package com.nttdata.movement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class Report1 {
    private String nameProduct;
    private String numberAccount;
    private Double balance;

}
