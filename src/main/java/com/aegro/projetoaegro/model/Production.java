/**
 * 
 */
package com.aegro.projetoaegro.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Gabriel Menin
 *
 */
@Entity
public class Production {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Positive
	@Column(name = "amount", nullable = false)
	private double amount;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="glebe_id")
	@JsonIgnore
	private Glebe glebe;

	/**
	 * @param <code>amount</code>	the record of the amount produced (in KG)
	 */
	public Production(double amount) {
		this.amount = amount;
	}

	/**
	 * Default constructor
	 */
	public Production() {}

	public Long getId() {
		return id;
	}
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@JsonIgnore
	public Glebe getGlebe() {
		return glebe;
	}

	public void setGlebe(Glebe glebe) {
		this.glebe = glebe;
	}

	@Override
	public String toString() {
		return "Production [amount=" + amount + "]";
	}

}
