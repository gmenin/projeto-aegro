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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Gabriel Menin
 *
 */
@Entity
public class Farm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@OneToMany(mappedBy = "farm", cascade = CascadeType.MERGE, orphanRemoval = true)
	@JsonIgnore
	private Set<Glebe> glebes;
	
	/**
	 * A produtividade da Fazenda é armazenada porque é 
	 * um dado que precisará ser acessado constantemente
	 * e seria custoso recalculá-la toda vez
	 */
	@Column(name = "productivity")
	private double productivity;

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
	
	@JsonIgnore
	public List<Glebe> getGlebes() {
		List<Glebe> glebes = new ArrayList<>(getGlebesInternal());
		return glebes;
	}

	@JsonIgnore
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
	
	public double getProductivity() {
		return this.productivity;
	}
	
	public void setProductivity(double productivity) {
		this.productivity = productivity;
	}
	
}
