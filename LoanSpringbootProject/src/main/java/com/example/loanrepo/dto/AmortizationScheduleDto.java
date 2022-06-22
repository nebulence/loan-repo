/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loanrepo.dto;

/**
 *
 * @author Neba
 */
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmortizationScheduleDto {

  BigDecimal monthlyPayment;
  BigDecimal totalInterestPaid;
  List<PaymentDto> payments;
}
