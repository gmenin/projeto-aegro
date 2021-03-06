/**
 * 
 */
package com.aegro.projetoaegro.rest.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aegro.projetoaegro.model.Farm;
import com.aegro.projetoaegro.model.Glebe;
import com.aegro.projetoaegro.model.Production;
import com.aegro.projetoaegro.service.FarmService;
import com.aegro.projetoaegro.service.GlebeService;
import com.aegro.projetoaegro.service.ProductionService;

/**
 * @author Gabriel Menin
 *
 */
@RestController
@RequestMapping("/api/v1")
public class ProductionRestController {

	@Autowired
	ProductionService productionService;

	@Autowired
	GlebeService glebeService;

	@Autowired
	FarmService farmService;

	@GetMapping(value = "/glebe/{glebeId}/production", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Production>> getAllProductionsFromGlebe(@PathVariable("glebeId") Long glebeId) {

		Collection<Production> productions = productionService.findAllProductionsByGlebeId(glebeId);

		if (productions == null) {
			return new ResponseEntity<Collection<Production>>(HttpStatus.NOT_FOUND);
		}
		
		if (productions.isEmpty()) {
			return new ResponseEntity<Collection<Production>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Collection<Production>>(productions, HttpStatus.OK);
	}

	@GetMapping(value = "/glebe/{glebeId}/production/{productionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Production> getProductionFromGlebe(@PathVariable("glebeId") Long glebeId,
			@PathVariable("productionId") Long productionId) {

		Production production = productionService.findProductionByGlebeId(glebeId, productionId);

		if (production == null) {
			return new ResponseEntity<Production>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Production>(production, HttpStatus.OK);
	}

	@GetMapping(value = "/production/{productionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Production> getProductionById(@PathVariable("productionId") Long productionId) {

		Production production = productionService.findProductionById(productionId);

		if (production == null) {
			return new ResponseEntity<Production>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Production>(production, HttpStatus.OK);
	}

	@PostMapping(value = "/glebe/{glebeId}/production", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Production> addProduction(@PathVariable("glebeId") Long glebeId,
			@RequestBody @Valid Production production) {

		Glebe glebe = this.glebeService.findGlebeById(glebeId);

		if (glebe == null) {
			return new ResponseEntity<Production>(HttpStatus.NOT_FOUND);
		}

		glebe.addProduction(production);
		production.setGlebe(glebe);

		Production savedProduction = this.productionService.saveProduction(production);

		Farm farm = this.farmService.findFarmById(glebe.getFarm().getId());
		
		this.glebeService.updateGlebeProductivity(glebe);
		this.farmService.updateFarmProductivity(farm);

		return new ResponseEntity<Production>(savedProduction, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/production/{productionId}")
	public ResponseEntity<Production> deleteProduction(@PathVariable("productionId") Long productionId) {

		Production production = productionService.findProductionById(productionId);

		if (production == null) {
			return new ResponseEntity<Production>(HttpStatus.NOT_FOUND);
		}

		this.productionService.deleteProduction(production);

		Glebe glebe = this.glebeService.findGlebeById(production.getGlebe().getId());
		Farm farm = this.farmService.findFarmById(glebe.getFarm().getId());
		
		this.glebeService.updateGlebeProductivity(glebe);
		this.farmService.updateFarmProductivity(farm);

		return new ResponseEntity<Production>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/production/{productionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Production> updateProduction(@PathVariable("productionId") Long productionId,
			@RequestBody @Valid Production production) {

		Production currentProduction = this.productionService.findProductionById(productionId);

		if (currentProduction == null) {
			return new ResponseEntity<Production>(HttpStatus.NOT_FOUND);
		}

		currentProduction.setAmount(production.getAmount());
		Production savedProduction = this.productionService.saveProduction(currentProduction);
		
		Glebe glebe = this.glebeService.findGlebeById(currentProduction.getGlebe().getId());
		Farm farm = this.farmService.findFarmById(glebe.getFarm().getId());
		
		this.glebeService.updateGlebeProductivity(glebe);
		this.farmService.updateFarmProductivity(farm);

		return new ResponseEntity<Production>(savedProduction, HttpStatus.OK);
	}
}
