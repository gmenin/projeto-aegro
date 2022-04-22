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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Gabriel Menin
 *
 */
@Entity
public class Farm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotBlank
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "glebes")
	@ElementCollection(targetClass=Glebe.class)
	private Set<Glebe> glebes;

	/**
	 * @param <code>name</code> the farm name	
	 */
	public Farm(String name) {
		this.name = name;
	}
	
	/**
	 * Default constructor
	 */
	public Farm() {}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Glebe> getGlebes() {
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
		return "Farm [id=" + id + ", name=" + name + ", glebes=" + Arrays.toString(getGlebes().toArray()) + "]";
	}
	
}
