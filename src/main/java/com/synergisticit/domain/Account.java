package com.synergisticit.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "account")
public class Account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long accountId;
	
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	
	private Long accountNumber;
	
	@NotNull
	@ManyToOne
	private Branch accountBranch;
	
	private String accountHolder;
	
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private LocalDate accountDateOpened;
	
	@NotNull
	private Double accountBalance;
	
	@NotNull
	@ManyToOne
	private Customer accountCustomer;

}
