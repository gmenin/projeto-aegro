/**
 * 
 */
package com.aegro.projetoaegro.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Gabriel Menin
 *
 */
@Entity
public class Glebe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "productions", nullable = true)
	@ElementCollection(targetClass=Production.class)
	private Set<Production> productions;
	
	@Column(name = "area", nullable = false)
	private double area;

	/**
	 * @param <code>name</code>			the glebe name
	 * @param <code>area</code>			the glebe area (in Hectare)
	 */
	public Glebe(String name, double area) {
		this.name = name;
		this.area = area;
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
	
	public List<Production> getProductions() {
		List<Production> productions = new ArrayList<>(getProductionsInternal());
		return productions;
	}
	
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

	@Override
	public String toString() {
		return "Glebe [id=" + id + ", name=" + name + ", productions=" + Arrays.toString(getProductions().toArray()) + ", area=" + area + "]";
	}

}
