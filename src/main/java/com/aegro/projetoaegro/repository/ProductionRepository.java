/**
 * 
 */
package com.aegro.projetoaegro.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aegro.projetoaegro.model.Production;

/**
 * @author Gabriel Menin
 *
 */
public interface ProductionRepository extends JpaRepository<Production, Long>{

	@Query(value = "SELECT * FROM PRODUCTION p WHERE p.GLEBE_ID = ?1 ORDER BY p.ID ASC", nativeQuery = true)
	Collection<Production> findAllByGlebeId(Long glebeId);

	@Query(value = "SELECT * FROM PRODUCTION p WHERE p.GLEBE_ID = ?1 AND p.ID = ?2 ORDER BY p.ID ASC", nativeQuery = true)
	Production findByGlebeId(Long glebeId, Long productionId);
}
