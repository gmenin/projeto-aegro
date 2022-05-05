/**
 * 
 */
package com.aegro.projetoaegro.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Gabriel Menin
 *
 */
@Entity
public class Glebe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "name", nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "glebe", cascade = CascadeType.MERGE, orphanRemoval = true)
	@JsonIgnore
	private Set<Production> productions;
	
	@NotNull
	@Positive
	@Column(name = "area", nullable = false)
	private double area;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="farm_id")	
	@JsonIgnore
	private Farm farm;
	
	/**
	 * A produtividade do Talhão é armazenada porque é 
	 * um dado que precisará ser acessado constantemente
	 * e seria custoso recalculá-la toda vez
	 */
	@Column(name = "productivity")
	private double productivity;

	/**
	 * @param <code>name</code>			the glebe name
	 * @param <code>area</code>			the glebe area (in Hectare)
	 */
	public Glebe(String name, double area) {
		this.name = name;
		this.area = area;
	}

	/**
	 * Default constructor
	 */
	public Glebe() {}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	public List<Production> getProductions() {
		List<Production> productions = new ArrayList<>(getProductionsInternal());
		return productions;
	}
	
	@JsonIgnore
	protected Set<Production> getProductionsInternal() {
		if(this.productions == null) {
			this.productions = new HashSet<>();
		}
		return this.productions;
	}

	public void addProduction(Production production) {
		getProductionsInternal().add(production);
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}
	
	public void setFarm(Farm farm) {
		this.farm = farm;
	}
	
	@JsonIgnore
	public Farm getFarm() {
		return this.farm;
	}

	@Override
	public String toString() {
		return "Glebe [id=" + id + ", name=" + name + ", area=" + area + ", productions=" + Arrays.toString(getProductions().toArray()) + "]";
	}
	
	public double getProductivity() {
		return this.productivity;
	}
	
	public void setProductivity(double productivity) {
		this.productivity = productivity;
	}
	
}
