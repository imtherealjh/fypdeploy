package com.uow.FYP_23_S1_11.domain;

import java.time.LocalDate;

import com.uow.FYP_23_S1_11.enums.ESubscriptionTier;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="SUBSCRIPTION")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private ESubscriptionTier tier;
    private LocalDate startDate;
    private LocalDate endDate;
}
