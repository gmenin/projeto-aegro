/**
 * 
 */
package com.aegro.projetoaegro.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aegro.projetoaegro.model.Farm;
import com.aegro.projetoaegro.repository.FarmRepository;

/**
 * @author Gabriel Menin
 *
 */
@Service
public class FarmServiceImpl implements FarmService {

	private FarmRepository repository;

	@Autowired
	public FarmServiceImpl(FarmRepository repository) {
		this.repository = repository;
	}

	@Override
	public Farm findFarmById(Long id) throws DataAccessException {

		try {
			return repository.findById(id).get();
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public Collection<Farm> findAllFarms() throws DataAccessException {

		try {
			return repository.findAll();
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public Farm saveFarm(Farm farm) throws DataAccessException {

		try {
			return repository.save(farm);
		}catch (Exception e) {
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
		}catch (Exception e) {
			return true;
		}
	}

	@Override
	public boolean existsById(Long id) throws DataAccessException {

		try {
			return repository.existsById(id);
		}catch (Exception e) {
			return true;
		}
	}

}
