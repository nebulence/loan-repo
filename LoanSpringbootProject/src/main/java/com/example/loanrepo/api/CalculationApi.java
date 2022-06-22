/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loanrepo.api;

import com.example.loanrepo.dto.AmortizationScheduleDto;
import com.example.loanrepo.dto.LoadDto;
import com.example.loanrepo.dto.ResultDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

/**
 *
 * @author Neba
 */
@RequestMapping("/calculator")
public interface CalculationApi {
    @PostMapping(value = "/simple-calculation", produces = {"application/json"})
    ResultDto calculateSimpleLoan(@Valid @RequestBody LoadDto loadDto);
    
    @PostMapping(value = "/amortization-calculation", produces = {"application/json"})
    AmortizationScheduleDto calculateLoanWithAmortization(@Valid @RequestBody LoadDto loadDto);
}
