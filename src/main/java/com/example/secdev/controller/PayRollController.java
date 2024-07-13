package com.example.secdev.controller;


import com.example.secdev.service.PayRollService;
import com.example.secdev.utils.dtos.PayRollDTO;
import com.example.secdev.utils.dtos.StatusDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.time.YearMonth;
import java.util.Calendar;
import java.util.List;


@RequestMapping
@RestController
@AllArgsConstructor
@Validated
public class PayRollController {
    private PayRollService payRollService;

    @PostMapping("/api/acct/payments")
    public StatusDTO addPayments(@RequestBody @UniqueElements List<@Valid PayRollDTO> payRolls) {
        return payRollService.addPayments(payRolls);
    }

    @PutMapping("/api/acct/payments")
    public StatusDTO updatePayments(@RequestBody @Valid PayRollDTO payRollDTO) {
        return payRollService.updatePayment(payRollDTO);
    }

    @GetMapping("/api/empl/payment")
    public Object getPayment(@RequestParam(required = false) @DateTimeFormat(pattern = "MM-yyyy") Calendar period) {
        if (period != null) {
            return payRollService.getCurrentEmployeeDataByPeriod(calendarToYearMonth(period));
        } else {
            return payRollService.getAllCurrentEmployeeData();
        }
    }

    private YearMonth calendarToYearMonth(Calendar calendar) {
        return YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
    }
}