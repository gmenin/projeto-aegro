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
import com.aegro.projetoaegro.model.Glebe;
import com.aegro.projetoaegro.service.FarmService;
import com.aegro.projetoaegro.service.GlebeService;

/**
 * @author Gabriel Menin
 *
 */
@RestController
@RequestMapping("/v1/farm")
public class GlebeRestController {
	
	@Autowired
	GlebeService glebeService;
	
	@Autowired
	FarmService farmService;
	
	@GetMapping(value = "/{farmId}/glebe", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Glebe>> getAllGlebesFromFarm(@PathVariable("farmId") String farmId) {
		
		Long farmIdL;

		try {
			farmIdL = Long.parseLong(farmId);
		} catch (Exception e) {
			return new ResponseEntity<Collection<Glebe>>(HttpStatus.BAD_REQUEST);
		}
		
		Collection<Glebe> glebes = glebeService.findAllGlebesByFarmId(farmIdL);
		
		return new ResponseEntity<Collection<Glebe>>(glebes, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{farmId}/glebe/{glebeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Glebe> getGlebeFromFarm(@PathVariable("farmId") String farmId, @PathVariable("glebeId") String glebeId) {
		
		Long farmIdL, glebeIdL;

		try {
			farmIdL = Long.parseLong(farmId);
			glebeIdL = Long.parseLong(glebeId);
		} catch (Exception e) {
			return new ResponseEntity<Glebe>(HttpStatus.BAD_REQUEST);
		}
		
		Glebe glebe = glebeService.findGlebeByFarmId(farmIdL, glebeIdL);
		
		if (glebe == null) {
			return new ResponseEntity<Glebe>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Glebe>(glebe, HttpStatus.OK);
	}
	
	@PostMapping(value = "/{farmId}/glebe", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Glebe> addGlebe(@PathVariable("farmId") String farmId, @RequestBody @Valid Glebe glebe, BindingResult bindingResult) {
		
		Long farmIdL;

		try {
			farmIdL = Long.parseLong(farmId);
		} catch (Exception e) {
			return new ResponseEntity<Glebe>(HttpStatus.BAD_REQUEST);
		}
		
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<Glebe>(HttpStatus.BAD_REQUEST);
		}
		
		if (glebe == null) {
			return new ResponseEntity<Glebe>(HttpStatus.BAD_REQUEST);
		}

		Farm farm = this.farmService.findFarmById(farmIdL);

		if (farm == null) {
			return new ResponseEntity<Glebe>(HttpStatus.NOT_FOUND);
		}
		
		farm.addGlebe(glebe);
		glebe.setFarm(farm);
		
		Glebe savedGlebe = this.glebeService.saveGlebe(glebe);

		return new ResponseEntity<Glebe>(savedGlebe, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{farmId}/glebe/{glebeId}")
	public ResponseEntity<Glebe> deleteGlebe(@PathVariable("farmId") String farmId, @PathVariable("glebeId") String glebeId) {
		
		Long farmIdL, glebeIdL;

		try {
			farmIdL = Long.parseLong(farmId);
			glebeIdL = Long.parseLong(glebeId);
		} catch (Exception e) {
			return new ResponseEntity<Glebe>(HttpStatus.BAD_REQUEST);
		}

		Glebe glebe = glebeService.findGlebeByFarmId(farmIdL, glebeIdL);
		
		if (glebe == null) {
			return new ResponseEntity<Glebe>(HttpStatus.NOT_FOUND);
		}

		this.glebeService.deleteGlebe(glebe);

		return new ResponseEntity<Glebe>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/{farmId}/glebe/{glebeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Glebe> updateGlebe(@PathVariable("farmId") String farmId, @PathVariable("glebeId") String glebeId, @RequestBody Glebe glebe) {

		Long farmIdL, glebeIdL;

		try {
			farmIdL = Long.parseLong(farmId);
			glebeIdL = Long.parseLong(glebeId);
		} catch (Exception e) {
			return new ResponseEntity<Glebe>(HttpStatus.BAD_REQUEST);
		}

		Glebe currentGlebe = this.glebeService.findGlebeByFarmId(farmIdL, glebeIdL);
		
		if (currentGlebe == null) {
			return new ResponseEntity<Glebe>(HttpStatus.NOT_FOUND);
		}

		if (glebe == null) {
			return new ResponseEntity<Glebe>(HttpStatus.BAD_REQUEST);
		}

		if (glebe.getName() != null) {
			currentGlebe.setName(glebe.getName());
		}
		
		if (glebe.getArea() != 0.0) {
			currentGlebe.setArea(glebe.getArea());
		}

		Glebe savedGlebe = this.glebeService.saveGlebe(currentGlebe);

		return new ResponseEntity<Glebe>(savedGlebe, HttpStatus.OK);
	}
}
