package com.uow.FYP_23_S1_11.domain;

import com.uow.FYP_23_S1_11.enums.ETokenType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TOKEN")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private ETokenType type;
    private String token;

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "accountId")
    private UserAccount tokenAccount;
}
