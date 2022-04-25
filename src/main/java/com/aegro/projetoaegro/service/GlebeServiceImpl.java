/**
 * 
 */
package com.aegro.projetoaegro.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aegro.projetoaegro.model.Glebe;
import com.aegro.projetoaegro.repository.GlebeRepository;

/**
 * @author Gabriel Menin
 *
 */
@Service
public class GlebeServiceImpl implements GlebeService{
	
	private GlebeRepository repository;
	
	@Autowired
	public GlebeServiceImpl(GlebeRepository repository) {
		this.repository = repository;
	}

	@Override
	public Collection<Glebe> findAllGlebesByFarmId(Long id) throws DataAccessException {
		
		try {
			return repository.findAllByFarmId(id);
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public Glebe findGlebeByFarmId(Long farmId, Long glebeId) throws DataAccessException {
		
		try {
			return repository.findByFarmId(farmId, glebeId);
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
	
	
}
