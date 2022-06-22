/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loanrepo;

import com.example.loanrepo.dao.Calculation;
import com.example.loanrepo.dao.DaoMapper;
import com.example.loanrepo.dao.Payment;
import com.example.loanrepo.repository.CalculationRepository;
import com.example.loanrepo.repository.PaymentRepository;
import com.example.loanrepo.service.CalculatorServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Neba
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CalculatorServiceImpl.class})
public class CalculatorServiceTest {

  @MockBean
  private CalculationRepository calculationRepository;

  @MockBean
  private PaymentRepository paymentRepository;
  
  @MockBean
  private DaoMapper daoMapper;

  @Autowired
  private CalculatorServiceImpl calculatorService;

  @Test
  void testSaveLoanCalculation() {

    final Payment payment1 = createPayment(1, 1263.05, 1242.22, 20.83, 3757.78);
    final Payment payment2 = createPayment(2, 1263.05, 1247.39, 15.66, 2510.39);
    final Payment payment3 = createPayment(3, 1263.05, 1252.59, 10.46, 1257.80);
    final Payment payment4 = createPayment(4, 1263.04, 1257.80, 5.24, 0);

    final List<Payment> payments = new ArrayList<>();
    payments.add(payment1);
    payments.add(payment2);
    payments.add(payment3);
    payments.add(payment4);
    final Calculation calculation = Calculation.builder()
        .amount(5000.0)
        .interest(5.0)
        .numberOfMonths(4)
        .monthlyPayment(1263.05)
        .totalInterestPaid(52.19)
        .payments(payments)
        .build();
    
    doReturn(calculation).when(calculationRepository).saveAndFlush(eq(calculation));
    doReturn(payment1).when(paymentRepository).saveAndFlush(eq(payment1));
    doReturn(payment2).when(paymentRepository).saveAndFlush(eq(payment2));
    doReturn(payment3).when(paymentRepository).saveAndFlush(eq(payment3));
    doReturn(payment4).when(paymentRepository).saveAndFlush(eq(payment4));

    calculatorService.saveToDB(calculation);

    verify(calculationRepository).saveAndFlush(eq(calculation));
    verify(paymentRepository).saveAndFlush(eq(payment1));
    verify(paymentRepository).saveAndFlush(eq(payment2));
    verify(paymentRepository).saveAndFlush(eq(payment3));
    verify(paymentRepository).saveAndFlush(eq(payment4));          
  }

  private Payment createPayment(int paymentIndex, double paymentAmount,
      double principalAmount, double interestAmount, double balanceOwed) {
    return Payment.builder()
        .paymentIndex(paymentIndex)
        .paymentAmount(paymentAmount)
        .principalAmount(principalAmount)
        .interestAmount(interestAmount)
        .balanceOwed(balanceOwed)
        .build();
  }
}
