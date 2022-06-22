/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loanrepo.dao;

import com.example.loanrepo.dto.AmortizationScheduleDto;
import com.example.loanrepo.dto.LoadDto;
import com.example.loanrepo.dto.PaymentDto;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 *
 * @author Neba
 */
@Component
public class DaoMapper {
    public Calculation mapToDao(@NotNull LoadDto loadDto, @NotNull AmortizationScheduleDto amortizationSchedule) {

    final Calculation calculation = Calculation.builder()
        .amount(loadDto.getAmount().doubleValue())
        .interest(loadDto.getInterest().doubleValue())
        .numberOfMonths(loadDto.getNumberOfMonths())
        .monthlyPayment(amortizationSchedule.getMonthlyPayment().doubleValue())
        .createdDate(Instant.now())
        .totalInterestPaid(amortizationSchedule.getTotalInterestPaid().doubleValue())
        .build();
    calculation.setPayments(
        getLoanPayments(amortizationSchedule.getPayments(), calculation));

    return calculation;
  }

  public Payment mapToDao(PaymentDto paymentDto, Calculation calculation) {
    return Payment.builder()
        .calculation(calculation)
        .paymentIndex(paymentDto.getPaymentIndex())
        .paymentAmount(paymentDto.getPaymentAmount().doubleValue())
        .principalAmount(paymentDto.getPrincipalAmount().doubleValue())
        .interestAmount(paymentDto.getInterestAmount().doubleValue())
        .balanceOwed(paymentDto.getBalanceOwed().doubleValue())
        .build();
  }

  private List<Payment> getLoanPayments(List<PaymentDto> paymentDtoList, Calculation calculation) {

    if (paymentDtoList == null || paymentDtoList.isEmpty()) {
      return Collections.emptyList();
    }

    final List<Payment> payments = new ArrayList<>(paymentDtoList.size());
    for (PaymentDto p : paymentDtoList){
        payments.add(mapToDao(p, calculation));
    }
    return payments;
  }
}
