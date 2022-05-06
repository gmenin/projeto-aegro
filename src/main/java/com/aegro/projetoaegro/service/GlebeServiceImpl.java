/**
 * 
 */
package com.aegro.projetoaegro.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aegro.projetoaegro.model.Glebe;
import com.aegro.projetoaegro.model.Production;
import com.aegro.projetoaegro.repository.GlebeRepository;

/**
 * @author Gabriel Menin
 *
 */
@Service
public class GlebeServiceImpl implements GlebeService{
	
	private GlebeRepository repository;
	
	private ProductionService productionService;
	
	@Autowired
	public GlebeServiceImpl(GlebeRepository repository, ProductionService productionService) {
		this.repository = repository;
		this.productionService = productionService;
	}

	@Override
	public Collection<Glebe> findAllGlebesByFarmId(Long farmId) throws DataAccessException {
		
		try {
			return repository.findByFarmId(farmId);
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public Glebe findGlebeByFarmId(Long farmId, Long glebeId) throws DataAccessException {
		
		try {
			return repository.findGlebeByFarmId(farmId, glebeId);
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public Glebe saveGlebe(Glebe glebe) throws DataAccessException {
		
		try {
			return repository.save(glebe);
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public void deleteGlebe(Glebe glebe) throws DataAccessException {
		
		try {
			repository.delete(glebe);
		} catch (Exception e) {

		}
	}

	@Override
	public Glebe findGlebeById(Long glebeId) throws DataAccessException {
		
		try {
			return repository.findById(glebeId).get();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void updateGlebeProductivity(Glebe glebe) throws DataAccessException {
		
		try {
			double productivity = this.calculateGlebeProductivity(glebe);
			if (productivity > 0) {
				glebe.setProductivity(productivity);
				this.saveGlebe(glebe);
			}	
			
		}catch (Exception e) {
			
		}
	}
	
	@Override
	public double calculateGlebeProductivity(Glebe glebe) throws DataAccessException {
		double productivity = 0;
		
		try {
			double totalAmount = this.calculateGlebeAmountProduced(glebe);
			
			if (totalAmount > 0) {
				productivity = totalAmount/glebe.getArea();
			}	
			return productivity;
		}catch (Exception e) {
			return -1;
		}
	}

	@Override
	public double calculateGlebeAmountProduced(Glebe glebe) throws DataAccessException {
		
		double totalAmount = 0;
		
		try {
			Collection<Production> productions = this.productionService.findAllProductionsByGlebeId(glebe.getId());
			
			for (Production production: productions) {
				totalAmount += production.getAmount();
			}
			return totalAmount;
					
		} catch (Exception e) {
			return totalAmount;
		}
	}
		
}
