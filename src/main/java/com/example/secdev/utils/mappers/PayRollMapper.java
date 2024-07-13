package com.example.secdev.utils.mappers;


import com.example.secdev.model.PayRoll;
import com.example.secdev.utils.dtos.PayRollDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PayRollMapper {

        public PayRollDTO toDTO(PayRoll payRoll) {
            return PayRollDTO.builder()
                    .employee(payRoll.getEmployee())
                    .period(payRoll.getPeriod())
                    .salary(payRoll.getSalary())
                    .build();
        }

        public PayRoll fromDTO(PayRollDTO payRollDTO) {
            return PayRoll.builder()
                    .employee(payRollDTO.getEmployee())
                    .period(payRollDTO.getPeriod())
                    .salary(payRollDTO.getSalary())
                    .build();
        }

        public List<PayRollDTO> toDTO(List<PayRoll> payRolls) {
            return payRolls.stream().map(this::toDTO).toList();
        }

        public List<PayRoll> fromDTO(List<PayRollDTO> payRollDTOs) {
            return payRollDTOs.stream().map(this::fromDTO).toList();
        }
}
