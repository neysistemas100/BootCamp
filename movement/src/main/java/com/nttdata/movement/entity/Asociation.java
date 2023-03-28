package com.nttdata.movement.entity;

import com.nttdata.movement.model.Movement;
import com.nttdata.movement.model.Transfer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="asociations")
public class Asociation {
    @Id
    private String id;
    @NotNull
    @NotEmpty(message = "Id Customer can't be empty")
    private String idCustomer;
    @NotNull
    @NotEmpty(message = "Id Product can't be empty")
    private String idProduct;
    @NotNull
    @NotEmpty(message = "Number product can't be empty")
    private String numberProduct;
    private Double balance;
    private List<Movement> movements;
    private List<Transfer> transfers;
}
