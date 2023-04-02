package com.nttdata.movement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementMobileWallet {
    private String type;
    private Double amount;
    private LocalDateTime date;
}
