package com.example.secdev.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.YearMonth;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayRoll {


    @Id
    @GeneratedValue
    private BigInteger id;
    private String employee;
    private YearMonth period;
    private Long salary;



}
