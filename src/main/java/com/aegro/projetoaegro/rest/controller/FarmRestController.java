/**
 * 
 */
package com.aegro.projetoaegro.rest.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.aegro.projetoaegro.service.FarmService;

/**
 * @author Gabriel Menin
 *
 */
@RestController
@RequestMapping("/v1/farm")
public class FarmRestController {
	
	@Autowired
	FarmService farmService;

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Farm> getFarmById(@PathVariable("id") String id) {

		Long idL;

		try {
			idL = Long.parseLong(id);
		} catch (Exception e) {
			return new ResponseEntity<Farm>(HttpStatus.BAD_REQUEST);
		}

		Farm farm = this.farmService.findFarmById(idL);

		if (farm == null) {
			return new ResponseEntity<Farm>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Farm>(farm, HttpStatus.OK);
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<Collection<Farm>> getAllFarms() {
		Collection<Farm> farms = this.farmService.findAllFarms();

		if (farms == null) {
			return new ResponseEntity<Collection<Farm>>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Collection<Farm>>(farms, HttpStatus.OK);
	}

	@PostMapping(produces = "application/json")
	public ResponseEntity<Farm> addFarm(@RequestBody Farm farm) {
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
	public ResponseEntity<Farm> deleteFarm(@PathVariable("id") String id) {
		Long idL;

		try {
			idL = Long.parseLong(id);
		} catch (Exception e) {
			return new ResponseEntity<Farm>(HttpStatus.BAD_REQUEST);
		}

		Farm farm = this.farmService.findFarmById(idL);

		if (farm == null) {
			return new ResponseEntity<Farm>(HttpStatus.NOT_FOUND);
		}

		this.farmService.deleteFarm(farm);

		return new ResponseEntity<Farm>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Farm> updateFarm(@PathVariable("id") String id, @RequestBody Farm farm) {

		Long idL;

		try {
			idL = Long.parseLong(id);
		} catch (Exception e) {
			return new ResponseEntity<Farm>(HttpStatus.BAD_REQUEST);
		}

		Farm currentFarm = this.farmService.findFarmById(idL);

		if (farm == null) {
			return new ResponseEntity<Farm>(HttpStatus.BAD_REQUEST);
		}

		if (farmService.existsByName(farm.getName())) {
			return new ResponseEntity<Farm>(HttpStatus.BAD_REQUEST);
		}

		if (currentFarm == null) {
			return new ResponseEntity<Farm>(HttpStatus.NOT_FOUND);
		}

		currentFarm.setName(farm.getName());
		Farm savedFarm = this.farmService.saveFarm(currentFarm);

		return new ResponseEntity<Farm>(savedFarm, HttpStatus.OK);
	}
}
