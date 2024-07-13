package com.example.secdev.service;


import com.example.secdev.config.CurrentUser;
import com.example.secdev.model.PayRoll;
import com.example.secdev.model.User;
import com.example.secdev.repo.PayRollRepo;
import com.example.secdev.repo.UserRepo;
import com.example.secdev.utils.dtos.EmployeeDTO;
import com.example.secdev.utils.dtos.PayRollDTO;
import com.example.secdev.utils.dtos.StatusDTO;
import com.example.secdev.utils.mappers.PayRollMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PayRollService {
    private PayRollRepo payRollRepo;
    private UserRepo userRepo;
    private PayRollMapper payRollMapper;
    private CurrentUser currentUser;

    @Transactional
    public StatusDTO addPayments(List<PayRollDTO> paymentDtos) {
        List<PayRoll> payments = payRollMapper.fromDTO(paymentDtos);

        for (var payment : payments) {
            if (!userRepo.existsByEmailIgnoreCase(payment.getEmployee())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee with specified email not found");
            }

            if (payRollRepo.existsByEmployeeIgnoreCaseAndPeriod(payment.getEmployee(), payment.getPeriod())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Record already created");
            }

            payRollRepo.save(payment);
        }

        return new StatusDTO("", "Added successfully!");
    }

    @Transactional
    public StatusDTO updatePayment(PayRollDTO paymentDto) {
        PayRoll payment = payRollRepo
                .findByEmployeeIgnoreCaseAndPeriod(paymentDto.getEmployee(), paymentDto.getPeriod())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Record not found"));

        payment.setSalary(paymentDto.getSalary());
        payRollRepo.save(payment);

        return new StatusDTO("", "Updated successfully!");
    }

    public EmployeeDTO getCurrentEmployeeDataByPeriod(YearMonth period) {
        User currUser = currentUser
                .getCurrentUser()
                .getUserEntity();

        PayRoll payment = payRollRepo
                .findByEmployeeIgnoreCaseAndPeriod(currUser.getEmail(), period)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Record with the specified period not found"));

        return EmployeeDTO
                .builder()
                .name(currUser.getName())
                .lastname(currUser.getLastName())
                .period(payment.getPeriod())
                .salary(centsToStrDollarsCents(payment.getSalary()))
                .build();
    }

    public List<EmployeeDTO> getAllCurrentEmployeeData() {
        User currUser = currentUser
                .getCurrentUser()
                .getUserEntity();

        List<PayRoll> payments = payRollRepo.findAllByEmployeeIgnoreCaseOrderByPeriodDesc(currUser.getEmail());

        return payments
                .stream()
                .map(p -> EmployeeDTO
                        .builder()
                        .name(currUser.getName())
                        .lastname(currUser.getLastName())
                        .period(p.getPeriod())
                        .salary(centsToStrDollarsCents(p.getSalary()))
                        .build())
                .collect(Collectors.toList());
    }

    private String centsToStrDollarsCents(long cents) {
        return String.format("%d dollar(s) %d cent(s)", cents / 100, cents % 100);
    }
}
