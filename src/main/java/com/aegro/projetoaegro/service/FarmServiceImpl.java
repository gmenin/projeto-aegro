/**
 * 
 */
package com.aegro.projetoaegro.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aegro.projetoaegro.model.Farm;
import com.aegro.projetoaegro.model.Glebe;
import com.aegro.projetoaegro.repository.FarmRepository;

/**
 * @author Gabriel Menin
 *
 */
@Service
public class FarmServiceImpl implements FarmService {

	private FarmRepository repository;

	private GlebeService glebeService;

	@Autowired
	public FarmServiceImpl(FarmRepository repository, GlebeService glebeService) {
		this.repository = repository;
		this.glebeService = glebeService;
	}

	@Override
	public Farm findFarmById(Long id) throws DataAccessException {

		try {
			return repository.findById(id).get();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Collection<Farm> findAllFarms() throws DataAccessException {

		try {
			return repository.findAll();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Farm saveFarm(Farm farm) throws DataAccessException {

		try {
			return repository.save(farm);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void deleteFarm(Farm farm) throws DataAccessException {

		try {
			repository.delete(farm);
		} catch (Exception e) {

		}
	}

	@Override
	public boolean existsByName(String name) throws DataAccessException {

		try {
			return repository.existsByName(name);
		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public boolean existsById(Long id) throws DataAccessException {

		try {
			return repository.existsById(id);
		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public void updateFarmProductivity(Farm farm) throws DataAccessException {
		
		try {
			double totalAmount = this.calculateFarmAmountProduced(farm);
			double totalArea = this.calculateFarmArea(farm);
			
			if ((totalAmount > 0) && (totalArea > 0)) {
				farm.setProductivity(totalAmount/totalArea);
				this.saveFarm(farm);
			}	
		} catch (Exception e) {
			
		}
	}

	@Override
	public double calculateFarmArea(Farm farm) throws DataAccessException {

		double totalArea = 0;

		try {
			Collection<Glebe> glebes = this.glebeService.findAllGlebesByFarmId(farm.getId());
			for (Glebe glebe : glebes) {
				totalArea += glebe.getArea();
			}
			return totalArea;
			
		} catch (Exception e) {
			return totalArea;
		}
	}

	@Override
	public double calculateFarmAmountProduced(Farm farm) throws DataAccessException {
		
		double totalAmount = 0;
		
		try {
			Collection<Glebe> glebes = this.glebeService.findAllGlebesByFarmId(farm.getId());
			for (Glebe glebe: glebes) {
				totalAmount += glebeService.calculateAmountProduced(glebe);
			}
			return totalAmount;
			
		} catch (Exception e) {
			return totalAmount;
		}
	}

}
