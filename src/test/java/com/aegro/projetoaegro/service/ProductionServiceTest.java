/**
 * 
 */
package com.aegro.projetoaegro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.aegro.projetoaegro.model.Glebe;
import com.aegro.projetoaegro.model.Production;
import com.aegro.projetoaegro.repository.ProductionRepository;

/**
 * @author gabriel.menin
 *
 */
@SpringBootTest
public class ProductionServiceTest {
	@Autowired
	private ProductionService productionService;
	
	@MockBean
	private ProductionRepository productionRepository;
	
	private List<Production> productions;
	
	@BeforeEach
	public void init() {
		productions = new ArrayList<Production>();

		productions.add(new Production(60));
		productions.add(new Production(70));
		productions.add(new Production(80));
	}
	
	@Test
	public void givenGlebeId_whenFindAllProductionsByGlebeId_thenReturnProductionCollection() {
		BDDMockito.given(productionRepository.findByGlebeId(1L)).willReturn(productions);
		
		Collection<Production> productionList = productionService.findAllProductionsByGlebeId(1L);
		
		assertThat(productionList).isNotEmpty();
		assertThat(productionList.size()).isEqualTo(3);
	}
	
	@Test
	public void givenGlebeIdProductionId_whenFindProductionByGlebeId_thenReturnProductionObject() {
		BDDMockito.given(productionRepository.findProductionByGlebeId(1L, 1L)).willReturn(productions.get(0));
		
		Production production = productionService.findProductionByGlebeId(1L, 1L);
		
		assertThat(production).isNotNull();
		assertThat(production.getAmount()).isEqualTo(productions.get(0).getAmount());
	}
	
	@Test
	public void givenProductionId_whenFindProductionById_thenReturnProductionObject() {
		BDDMockito.given(productionRepository.findById(1L)).willReturn(Optional.of(productions.get(0)));
		
		Production production = productionService.findProductionById(1L);
		
		assertThat(production).isNotNull();
		assertThat(production.getAmount()).isEqualTo(productions.get(0).getAmount());
	}
	
	@Test
	public void givenProductionObject_whenSaveProduction_thenReturnProductionObject() {
		Production production = new Production(120);
		
		BDDMockito.given(productionRepository.save(production)).willReturn(production);
		
		Production savedProduction = productionService.saveProduction(production);
		
		assertThat(savedProduction).isNotNull();
		assertThat(savedProduction.getAmount()).isEqualTo(production.getAmount());
	}
	
	@Test
	public void givenProductionObject_whenDeleteProduction_thenNothing() {
		
		BDDMockito.willDoNothing().given(productionRepository).delete(productions.get(0));
		
		productionService.deleteProduction(productions.get(0));
		
		verify(productionRepository, times(1)).delete(productions.get(0));
	}
}
