/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loanrepo.dao;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author Neba
 */
@Entity
@Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calculation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Double amount;

    @NotNull
    private Double interest;

    @NotNull
    private Integer numberOfMonths;

    @NotNull
    private Double monthlyPayment;

    @NotNull
    private Double totalInterestPaid;

    @CreatedDate
    private Instant createdDate;

    @OneToMany(mappedBy = "calculation")    
    private List<Payment> payments;
}
