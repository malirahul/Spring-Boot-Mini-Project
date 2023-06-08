package com.example.aop.springaop.model;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table
@Data
public class Employee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String department;
}
