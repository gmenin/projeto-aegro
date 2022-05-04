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

	Collection<Production> findByGlebeId(Long glebeId);

	/**
	 * Query criada para buscar uma única Produção, 
	 * dado o Id do Talhão a que ela pertence e o
	 * seu próprio Id, respectivamente.
	 */
	@Query(value = "SELECT * FROM PRODUCTION p WHERE p.GLEBE_ID = ?1 AND p.ID = ?2 ORDER BY p.ID ASC", nativeQuery = true)
	Production findProductionByGlebeId(Long glebeId, Long productionId);
}
