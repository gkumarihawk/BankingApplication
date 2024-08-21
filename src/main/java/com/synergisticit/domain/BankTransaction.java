package com.synergisticit.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class BankTransaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@NotNull
	private Long bankTransactionId;
	
	//private Long bankTransactionFromAccount; //For withdrawal and transfer
	
	//private Long bankTransactionToAccount;
	
	private Double bankTransactionAmount;
	
	
	@ManyToOne
	private Account bankTransactionFromAccount;
	
	@ManyToOne
	private Account bankTransactionToAccount;
	
	@Enumerated(EnumType.STRING)
	private BankTransactionType bankTransactionType;
	
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime bankTransactionDateTime; //Takes current date
	
	private String comments;
	
	

}
