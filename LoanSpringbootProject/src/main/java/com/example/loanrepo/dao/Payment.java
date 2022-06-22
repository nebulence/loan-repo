/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.loanrepo.dao;

import com.sun.istack.NotNull;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotNull
    @ManyToOne
    @JoinColumn(name = "calculation_id", referencedColumnName = "id")    
    private Calculation calculation;

    @NotNull
    private Integer paymentIndex;

    @NotNull
    private Double paymentAmount;

    @NotNull
    private Double principalAmount;

    @NotNull
    private Double interestAmount;

    @NotNull
    private Double balanceOwed;
}
