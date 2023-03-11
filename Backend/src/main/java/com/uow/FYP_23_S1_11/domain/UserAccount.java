package com.uow.FYP_23_S1_11.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="USERACCOUNT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int accountId;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private String role;
}
