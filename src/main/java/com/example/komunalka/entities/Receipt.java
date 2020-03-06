package com.example.komunalka.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "komunalka_receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate created;
    private Integer electric_last;
    private Integer electric_current;
    private Integer water_last;
    private Integer water_current;
    private Integer gas_last;
    private Integer gas_current;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer electric_diff;
    private Integer water_diff;
    private Integer gas_diff;

    private Double electric_sum;
    private Double water_sum;
    private Double gas_sum;
    private Double sdpt_sum;
    private Double garbage_sum;
    private Double security_sum;

    private Double total_sum;

    public void calculate() {
        Tarif tarif = user.getTarif();
        electric_diff = electric_current - electric_last;
        water_diff = water_current - water_last;
        gas_diff = gas_current - gas_last;

        electric_sum = BigDecimal.valueOf(tarif.getElectric() * electric_diff).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        water_sum = BigDecimal.valueOf(tarif.getWater() * water_diff).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        gas_sum = BigDecimal.valueOf(tarif.getGas() * gas_diff).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (user.getFloor() >= 2) {
            sdpt_sum = BigDecimal.valueOf(tarif.getSdtp_high() * user.getHouse_space()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else {
            sdpt_sum = BigDecimal.valueOf(tarif.getSdpt_low() * user.getHouse_space()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        garbage_sum = BigDecimal.valueOf(tarif.getGarbage() * user.getResidents()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        security_sum = BigDecimal.valueOf(tarif.getSecurity()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        total_sum = BigDecimal.valueOf(electric_sum + water_sum + gas_sum + sdpt_sum + garbage_sum + security_sum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}