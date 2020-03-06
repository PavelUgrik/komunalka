package com.example.komunalka.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class Tarif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double electric;
    private Double water;
    private Double gas;
    private Double security;
    private Double sdpt_low;
    private Double sdtp_high;
    private Double garbage;
    private LocalDate updated;

    public Tarif() {
        electric = 2.64;
        water = 21.95;
        gas =5.868492;
        security = 170.00;
        sdpt_low = 7.72;
        sdtp_high = 8.02;
        garbage = 0.00;
        updated = LocalDate.now();
    }

}
