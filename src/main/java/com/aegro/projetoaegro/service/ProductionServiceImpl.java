/**
 * 
 */
package com.aegro.projetoaegro.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aegro.projetoaegro.model.Production;
import com.aegro.projetoaegro.repository.ProductionRepository;

/**
 * @author Gabriel Menin
 *
 */
@Service
public class ProductionServiceImpl implements ProductionService{

	private ProductionRepository repository;
	
	@Autowired
	public ProductionServiceImpl(ProductionRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Collection<Production> findAllProductionsByGlebeId(Long glebeId) throws DataAccessException {
		
		try {
			return repository.findByGlebeId(glebeId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Production findProductionByGlebeId(Long glebeId, Long productionId) throws DataAccessException {
		
		try {
			return repository.findProductionByGlebeId(glebeId, productionId);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Production findProductionById(Long productionId) throws DataAccessException {
		
		try {
			return repository.findById(productionId).get();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Production saveProduction(Production production) throws DataAccessException {
		
		try {
			return repository.save(production);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void deleteProduction(Production production) {
		
		try {
			repository.delete(production);
		} catch (Exception e) {
			
		}
	}
	
}
