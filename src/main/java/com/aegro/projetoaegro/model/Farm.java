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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Gabriel Menin
 *
 */
@Entity
public class Farm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "glebes", nullable = true)
	private Set<Glebe> glebes;

	/**
	 * @param <code>name</code> the farm name	
	 */
	public Farm(String name) {
		this.name = name;
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
	
	public List<Glebe> getGlabes() {
		List<Glebe> glebes = new ArrayList<>(getGlebesInternal());
		return glebes;
	}

	protected Set<Glebe> getGlebesInternal() {
		if(this.glebes == null) {
			this.glebes = new HashSet<>();
		}
		return this.glebes;
	}

	public void addGlebe(Glebe glebe) {
		getGlebesInternal().add(glebe);
	}

	@Override
	public String toString() {
		return "Farm [id=" + id + ", name=" + name + ", glebes=" + Arrays.toString(getGlabes().toArray()) + "]";
	}
	
}
