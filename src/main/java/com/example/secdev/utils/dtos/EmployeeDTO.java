package com.example.secdev.utils.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String name;
    private String lastname;
    @JsonFormat(pattern = "MMMM-yyyy")
    private YearMonth period;
    private String salary;
}
