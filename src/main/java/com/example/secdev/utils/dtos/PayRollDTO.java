package com.example.secdev.utils.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.YearMonth;
import java.util.Objects;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayRollDTO {
    @NotBlank
    private String employee;
    @NotNull
    @JsonFormat(pattern = "MM-yyyy")
    private YearMonth period;
    @NotNull
    @Min(value = 0)
    private Long salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayRollDTO that = (PayRollDTO) o;
        return getEmployee().equals(that.getEmployee()) && getPeriod().equals(that.getPeriod());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(employee);
        result = 31 * result + Objects.hashCode(period);
        return result;
    }
}
