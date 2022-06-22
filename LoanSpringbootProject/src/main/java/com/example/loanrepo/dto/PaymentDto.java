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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

  BigDecimal paymentAmount;
  BigDecimal principalAmount;
  BigDecimal interestAmount;
  BigDecimal balanceOwed;
  int paymentIndex;
}
