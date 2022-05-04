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
	
	Collection<Glebe> findByFarmId(Long farmId);
	
	/**
	 * Query criada para buscar um único Talhão, 
	 * dado o Id da Fazenda a que ele pertence
	 * e o seu próprio Id, respectivamente.
	 */
	@Query(value = "SELECT * FROM GLEBE g WHERE g.FARM_ID = ?1 AND g.ID = ?2 ORDER BY g.ID ASC", nativeQuery = true)
	Glebe findGlebeByFarmId(Long farmId, Long glebeId);
}
