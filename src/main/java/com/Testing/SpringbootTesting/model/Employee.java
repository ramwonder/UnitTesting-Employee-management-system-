package com.Testing.SpringbootTesting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Builder
@Entity
@Table(name="employees")
public class Employee {
 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private  long id;
	@Column(name="first_name",nullable=false)
	private String firstname;
	@Column(name="last_name",nullable=false)
	private String lastname;
	@Column(nullable=false)
	private String email;
	
	
	
}
