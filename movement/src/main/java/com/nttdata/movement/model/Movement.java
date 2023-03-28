package com.nttdata.movement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Movement {

    private String ID;
    private String type;
    private Double amount;
    private LocalDateTime date;

}
