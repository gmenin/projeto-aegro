/**
 * 
 */
package com.aegro.projetoaegro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Gabriel Menin
 *
 */
@Entity
public class Production {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "amount", nullable = false)
	private double amount;

	/**
	 * @param <code>name</code>		the production name
	 * @param <code>amount</code>	the record of the amount produced (in KG)
	 */
	public Production(String name, double amount) {
		this.name = name;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Production [id=" + id + ", name=" + name + ", amount=" + amount + "]";
	}

}
