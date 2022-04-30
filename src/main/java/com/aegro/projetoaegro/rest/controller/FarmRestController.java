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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aegro.projetoaegro.model.Farm;
import com.aegro.projetoaegro.service.FarmService;

/**
 * @author Gabriel Menin
 *
 */
@RestController
@RequestMapping("/api/v1/farm")
public class FarmRestController {
	
	@Autowired
	FarmService farmService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Farm>> getAllFarms() {
		
		Collection<Farm> farms = this.farmService.findAllFarms();

		if (farms == null) {
			return new ResponseEntity<Collection<Farm>>(HttpStatus.NOT_FOUND);
		}
		
		if (farms.isEmpty()) {
			return new ResponseEntity<Collection<Farm>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Collection<Farm>>(farms, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Farm> getFarmById(@PathVariable("id") Long id) {

		Farm farm = this.farmService.findFarmById(id);

		if (farm == null) {
			return new ResponseEntity<Farm>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Farm>(farm, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Farm> addFarm(@RequestBody @Valid Farm farm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<Farm>(HttpStatus.BAD_REQUEST);
		}
		
		if (farm == null) {
			return new ResponseEntity<Farm>(HttpStatus.BAD_REQUEST);
		}

		if (farmService.existsByName(farm.getName())) {
			return new ResponseEntity<Farm>(HttpStatus.BAD_REQUEST);
		}

		Farm savedFarm = this.farmService.saveFarm(farm);

		return new ResponseEntity<Farm>(savedFarm, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Farm> deleteFarm(@PathVariable("id") Long id) {

		Farm farm = this.farmService.findFarmById(id);

		if (farm == null) {
			return new ResponseEntity<Farm>(HttpStatus.NOT_FOUND);
		}

		this.farmService.deleteFarm(farm);

		return new ResponseEntity<Farm>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Farm> updateFarm(@PathVariable("id") Long id, @RequestBody @Valid Farm farm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<Farm>(HttpStatus.BAD_REQUEST);
		}

		if (farm == null) {
			return new ResponseEntity<Farm>(HttpStatus.BAD_REQUEST);
		}

		if (farmService.existsByName(farm.getName())) {
			return new ResponseEntity<Farm>(HttpStatus.BAD_REQUEST);
		}
		
		Farm currentFarm = this.farmService.findFarmById(id);

		if (currentFarm == null) {
			return new ResponseEntity<Farm>(HttpStatus.NOT_FOUND);
		}

		currentFarm.setName(farm.getName());
		Farm savedFarm = this.farmService.saveFarm(currentFarm);

		return new ResponseEntity<Farm>(savedFarm, HttpStatus.OK);
	}
}
