package com.nttdata.movement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    private String id;
    private String sourceAccount;
    private String destinationAccount;
    private Double amount;
    private LocalDateTime date;
}
