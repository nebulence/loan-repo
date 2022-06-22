/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loanrepo.controller;

import com.example.loanrepo.api.CalculationApi;
import com.example.loanrepo.dto.AmortizationScheduleDto;
import com.example.loanrepo.dto.LoadDto;
import com.example.loanrepo.dto.ResultDto;
import com.example.loanrepo.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Neba
 */
@Validated
@RestController
public class LoanCalculationController implements CalculationApi{
    
    private final CalculatorService calculatorService;
    
    @Autowired
    public LoanCalculationController(CalculatorService calculatorService){
        this.calculatorService = calculatorService;
    }

    @Override
    public ResultDto calculateSimpleLoan(LoadDto loadDto) {
        return calculatorService.calculateSimpleLoan(loadDto);
    }

    @Override
    public AmortizationScheduleDto calculateLoanWithAmortization(LoadDto loadDto) {
        return calculatorService.calculateLoanWithAmortization(loadDto);
    }
    
}
