/**
 * 
 */
package com.aegro.projetoaegro.service;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import com.aegro.projetoaegro.model.Production;

/**
 * @author Gabriel Menin
 *
 */
public interface ProductionService {

	Collection<Production> findAllProductionsByGlebeId(Long glebeId) throws DataAccessException;

	Production findProductionByGlebeId(Long glebeId, Long productionId) throws DataAccessException;

	Production findProductionById(Long productionIdL) throws DataAccessException;
	
	Production saveProduction(Production production) throws DataAccessException;

	void deleteProduction(Production production);

}
