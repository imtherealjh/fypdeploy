package com.uow.FYP_23_S1_11.domain;

// Importing required classes
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

// Annotations
//@Entity
//@Table(name = "EMAIL")
//@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// Class
public class MailDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// Class data members
	private String recipient;
	private String msgBody;
	private String subject;
	private String attachment;
}
