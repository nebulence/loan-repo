/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loanrepo.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Neba
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    
    private BigDecimal monthlyPayment;
    private BigDecimal totalInterestPaid;
    
}
