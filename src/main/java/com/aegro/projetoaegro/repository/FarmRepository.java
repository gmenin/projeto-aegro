/**
 * 
 */
package com.aegro.projetoaegro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aegro.projetoaegro.model.Farm;

/**
 * @author Gabriel Menin
 *
 */
public interface FarmRepository extends JpaRepository<Farm, Long>{
	
	boolean existsByName(String name);
	
}
