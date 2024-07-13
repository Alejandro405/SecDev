package com.example.secdev.repo;


import com.example.secdev.model.PayRoll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface PayRollRepo extends JpaRepository<PayRoll, BigInteger> {
    boolean existsByEmployeeIgnoreCaseAndPeriod(String employee, YearMonth period);

    Optional<PayRoll> findByEmployeeIgnoreCaseAndPeriod(String employee, YearMonth period);

    List<PayRoll> findAllByEmployeeIgnoreCaseOrderByPeriodDesc(String employee);
}
