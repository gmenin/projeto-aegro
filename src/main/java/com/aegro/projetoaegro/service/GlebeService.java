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

	Collection<Glebe> findAllGlebesByFarmId(Long id) throws DataAccessException; 
	
	Glebe findGlebeByFarmId(Long farmId, Long glebeId) throws DataAccessException;

	Glebe saveGlebe(Glebe glebe) throws DataAccessException;

	void deleteGlebe(Glebe glebe) throws DataAccessException;

	Glebe findGlebeById(Long glebeId) throws DataAccessException;
	
}
