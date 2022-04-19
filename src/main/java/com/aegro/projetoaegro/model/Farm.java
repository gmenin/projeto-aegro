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
	
	@Column(name = "land_plots", nullable = true)
	private Set<LandPlot> landPlots;

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
	
	public List<LandPlot> getLandPlots() {
		List<LandPlot> landPlots = new ArrayList<>(getLandPlotsInternal());
		return landPlots;
	}

	protected Set<LandPlot> getLandPlotsInternal() {
		if(this.landPlots == null) {
			this.landPlots = new HashSet<>();
		}
		return this.landPlots;
	}

	public void addLandPlot(LandPlot landPlot) {
		getLandPlotsInternal().add(landPlot);
	}

	@Override
	public String toString() {
		return "Farm [id=" + id + ", name=" + name + ", landPlots=" + Arrays.toString(getLandPlots().toArray()) + "]";
	}
	
}
