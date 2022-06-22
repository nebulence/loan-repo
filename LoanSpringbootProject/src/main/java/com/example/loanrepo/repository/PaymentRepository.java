/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loanrepo.repository;

import com.example.loanrepo.dao.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Neba
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
    
}
