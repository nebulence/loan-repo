/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loanrepo.service;

import com.example.loanrepo.dao.Calculation;
import com.example.loanrepo.dao.DaoMapper;
import com.example.loanrepo.dao.Payment;
import com.example.loanrepo.dto.AmortizationScheduleDto;
import com.example.loanrepo.dto.LoadDto;
import com.example.loanrepo.dto.PaymentDto;
import com.example.loanrepo.dto.ResultDto;
import com.example.loanrepo.repository.CalculationRepository;
import com.example.loanrepo.repository.PaymentRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Neba
 */
@Service
public class CalculatorServiceImpl implements CalculatorService{
    
    private final CalculationRepository calculationRepository;
    private final PaymentRepository paymentRepository;
    private final DaoMapper daoMapper;

    @Autowired
    public CalculatorServiceImpl(CalculationRepository calculationRepository, PaymentRepository paymentRepository, DaoMapper daoMapper) {
        this.paymentRepository = paymentRepository;
        this.calculationRepository = calculationRepository; 
        this.daoMapper = daoMapper;
    }
    
    @Override
    public ResultDto calculateSimpleLoan(LoadDto loadDto) {
        BigDecimal amount = loadDto.getAmount();
        BigDecimal interest = loadDto.getInterest();
        int numberOfMonths = loadDto.getNumberOfMonths();  
        List<PaymentDto> payments = new ArrayList<>();
        BigDecimal monthlyPayment = BigDecimal.ZERO.compareTo(interest) != 0 ? calculateMonthlyPayment(loadDto) : amount.divide(new BigDecimal(numberOfMonths), 10, RoundingMode.HALF_EVEN);
        BigDecimal totalInterestPaid = monthlyPayment.multiply(new BigDecimal(numberOfMonths)).subtract(amount).setScale(2, RoundingMode.HALF_EVEN); 
        
        for (int i = 0; i < numberOfMonths; i++){            
            payments.add(recalculatePayments(payments, loadDto, monthlyPayment, i));
        }
        
        saveToDB(daoMapper.mapToDao(loadDto, new AmortizationScheduleDto(monthlyPayment, totalInterestPaid, payments)));  //persist calculation
        return new ResultDto(monthlyPayment, totalInterestPaid);
    }
    
    @Override
    public AmortizationScheduleDto calculateLoanWithAmortization(LoadDto loadDto) {
        BigDecimal interest = loadDto.getInterest();
        BigDecimal amount = loadDto.getAmount();        
        int numberOfMonths = loadDto.getNumberOfMonths();         
        List<PaymentDto> payments = new ArrayList<>();
        
        BigDecimal monthlyPayment = BigDecimal.ZERO.compareTo(interest) != 0 ? calculateMonthlyPayment(loadDto) : amount.divide(new BigDecimal(numberOfMonths), 10, RoundingMode.HALF_EVEN);
        BigDecimal totalInterest = monthlyPayment.multiply(new BigDecimal(numberOfMonths)).subtract(amount).setScale(2, RoundingMode.HALF_EVEN);
        for (int i = 0; i < numberOfMonths; i++){            
            payments.add(recalculatePayments(payments, loadDto, monthlyPayment, i));
        }
        saveToDB(daoMapper.mapToDao(loadDto, new AmortizationScheduleDto(monthlyPayment, totalInterest, payments)));  //persist calculation
        return new AmortizationScheduleDto(monthlyPayment, totalInterest, payments);
    }
    
    private PaymentDto recalculatePayments(List<PaymentDto> paymentList, LoadDto loadDto, BigDecimal monthlyPayment, int numberValueOfMonth) {
        
        BigDecimal monthlyInterest = loadDto.getInterest().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_EVEN).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_EVEN);
        PaymentDto tempPayment = numberValueOfMonth > 0 ? paymentList.get(numberValueOfMonth - 1) : null;
        BigDecimal tempBalance = tempPayment == null ? loadDto.getAmount() : tempPayment.getBalanceOwed();
        BigDecimal tempInterest = tempBalance.multiply(monthlyInterest).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal tempPrincipal = monthlyPayment.subtract(tempInterest).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal payment = tempPrincipal.add(tempInterest).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal newBalance = tempBalance.subtract(tempPrincipal).setScale(2, RoundingMode.HALF_EVEN);
        return new PaymentDto(payment, tempPrincipal, tempInterest, newBalance, numberValueOfMonth + 1);
    }
    
    private BigDecimal calculateMonthlyPayment(LoadDto loadDto){
        BigDecimal amount = loadDto.getAmount();
        BigDecimal yearlyInterest = loadDto.getInterest();
        int numberOfMonths = loadDto.getNumberOfMonths(); 
                
        BigDecimal monthlyInterest = yearlyInterest.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_EVEN).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_EVEN);//(rate/100.0) / 12;
        BigDecimal powerOfMonthlyInterest = BigDecimal.ONE.add(monthlyInterest).pow(numberOfMonths);
        BigDecimal monthlyPayment = monthlyInterest.multiply(amount).multiply(powerOfMonthlyInterest);       
        monthlyPayment = monthlyPayment.divide(powerOfMonthlyInterest.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_EVEN);
        return monthlyPayment;
    }
    
    @Transactional
    @Override
    public void saveToDB(@NotNull Calculation calc){
        Calculation persisted = calculationRepository.saveAndFlush(calc);
        for (Payment payment : calc.getPayments()){
            paymentRepository.saveAndFlush(payment);
        }        
    }  
}
