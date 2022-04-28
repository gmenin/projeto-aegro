/**
 * 
 */
package com.aegro.projetoaegro.service;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.aegro.projetoaegro.model.Farm;

/**
 * @author Gabriel Menin
 *
 */
public interface FarmService {

	Farm findFarmById(Long id) throws DataAccessException;
	
	Collection<Farm> findAllFarms() throws DataAccessException;
	
	Farm saveFarm(Farm farm) throws DataAccessException;
	
	void deleteFarm(Farm farm) throws DataAccessException;
	
	boolean existsByName(String name) throws DataAccessException;
	
	boolean existsById(Long id) throws DataAccessException;
	
	void updateFarmProductivity(Farm farm) throws DataAccessException;
	
	double calculateFarmArea(Farm farm) throws DataAccessException;
	
	double calculateFarmAmountProduced(Farm farm) throws DataAccessException;
	
}
