/**
 * 
 */
package com.aegro.projetoaegro.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aegro.projetoaegro.model.Glebe;

/**
 * @author Gabriel Menin
 *
 */
public interface GlebeRepository extends JpaRepository<Glebe, Long>{
	
	@Query(value = "SELECT * FROM GLEBE g WHERE g.FARM_ID = ?1 ORDER BY g.ID ASC", nativeQuery = true)
	Collection<Glebe> findAllByFarmId(Long id);
	
	@Query(value = "SELECT * FROM GLEBE g WHERE g.FARM_ID = ?1 AND g.ID = ?2 ORDER BY g.ID ASC", nativeQuery = true)
	Glebe findByFarmId(Long farmId, Long glebeId);
}
