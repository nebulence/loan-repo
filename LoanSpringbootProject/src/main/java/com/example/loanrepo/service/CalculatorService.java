/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loanrepo.service;

import com.example.loanrepo.dao.Calculation;
import com.example.loanrepo.dto.AmortizationScheduleDto;
import com.example.loanrepo.dto.LoadDto;
import com.example.loanrepo.dto.ResultDto;

/**
 *
 * @author Neba
 */
public interface CalculatorService {
    ResultDto calculateSimpleLoan(LoadDto loadDto);
    AmortizationScheduleDto calculateLoanWithAmortization(LoadDto loadDto);
    void saveToDB(Calculation calc);
}
