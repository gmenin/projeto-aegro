/**
 * 
 */
package com.aegro.projetoaegro.service;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.aegro.projetoaegro.model.Glebe;

/**
 * @author Gabriel Menin
 *
 */
public interface GlebeService {

	Collection<Glebe> findAllGlebesByFarmId(Long farmId) throws DataAccessException; 
	
	Glebe findGlebeByFarmId(Long farmId, Long glebeId) throws DataAccessException;

	Glebe saveGlebe(Glebe glebe) throws DataAccessException;

	void deleteGlebe(Glebe glebe) throws DataAccessException;

	Glebe findGlebeById(Long glebeId) throws DataAccessException;
	
	void updateGlebeProductivity(Glebe glebe) throws DataAccessException;
	
	double calculateGlebeProductivity(Glebe glebe) throws DataAccessException;
	
	double calculateGlebeAmountProduced(Glebe glebe) throws DataAccessException;
	
}
