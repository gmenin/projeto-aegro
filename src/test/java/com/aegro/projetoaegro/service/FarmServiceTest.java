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

import com.aegro.projetoaegro.model.Farm;
import com.aegro.projetoaegro.repository.FarmRepository;

/**
 * @author Gabriel Menin
 *
 */
@SpringBootTest
public class FarmServiceTest {

	@Autowired
	private FarmService farmService;
	
	@MockBean
	private FarmRepository farmRepository;
	
	private List<Farm> farms;
	
	@BeforeEach
	public void init() {
		
		farms = new ArrayList<Farm>();

		farms.add(new Farm("Fazenda 1"));
		farms.add(new Farm("Fazenda 2"));
		farms.add(new Farm("Fazenda 3"));
		
	}
	
	@Test
	public void givenFarmId_whenFindFarmById_thenReturnFarmObject() {
		BDDMockito.given(farmRepository.findById(1L)).willReturn(Optional.of(farms.get(0)));
		
		Farm farm = farmService.findFarmById(1L);
		
		assertThat(farm.getName()).isEqualTo("Fazenda 1");
	}
	
	@Test
	public void givenFarmId_whenFindFarmById_thenReturnNullObject() {
		BDDMockito.given(farmRepository.findById(1L)).willReturn(null);
		
		Farm farm = farmService.findFarmById(1L);
		
		assertThat(farm).isNull();
	}
	
	@Test
	public void givenFarmList_whenFindAllFarms_thenReturnFarmCollection() {
		BDDMockito.given(farmRepository.findAll()).willReturn(farms);
		
		Collection<Farm> farmList = farmService.findAllFarms();
		
		assertThat(farmList).isNotNull();
        assertThat(farmList.size()).isEqualTo(3);
	}
	
	@Test
	public void givenEmptyList_whenFindAllFarms_thenReturnEmptyFarmCollection() {
		farms.clear();
		
		BDDMockito.given(farmRepository.findAll()).willReturn(farms);
		
		Collection<Farm> farmList = farmService.findAllFarms();
		
		assertThat(farmList).isEmpty();
        assertThat(farmList.size()).isEqualTo(0);
	}
	
	@Test
    public void givenFarmObject_whenSaveFarm_thenReturnFarmObject() {
    	Farm farm = new Farm("Fazenda da Dona Benta");
    		
		BDDMockito.given(farmRepository.save(farm)).willReturn(farm);
	
		Farm savedFarm = farmService.saveFarm(farm);

		assertThat(savedFarm).isNotNull();
		assertThat(savedFarm.getName()).isEqualTo(farm.getName());
	}
	
	@Test
    public void givenNull_whenSaveFarm_thenReturnNull() {	
		
		BDDMockito.given(farmRepository.save(null)).willReturn(null);
		
		Farm savedFarm = farmService.saveFarm(null);
		
		assertThat(savedFarm).isNull();
	}
	
	@Test
	public void givenFarmObject_whenDeleteFarm_thenNothing() {
		Farm farm = farms.get(0);
		
		BDDMockito.willDoNothing().given(farmRepository).delete(farm);
		
		farmService.deleteFarm(farm);
		
		verify(farmRepository, times(1)).delete(farm);
	}
	
	@Test
	public void givenFarmName_whenFarmExistsByName_thenReturnTrue() {
		String name = farms.get(0).getName();
		
		BDDMockito.given(farmRepository.existsByName(name)).willReturn(true);
		
		boolean existsByName = farmService.existsByName(name);
		
		assertThat(existsByName).isTrue();
	}
	
	@Test
	public void givenFarmName_whenFarmExistsByName_thenReturnFalse() {
		String name = "Fazenda do Tonico";
		
		BDDMockito.given(farmRepository.existsByName(name)).willReturn(false);
		
		boolean existsByName = farmService.existsByName(name);
		
		assertThat(existsByName).isFalse();
	}
	
	@Test
	public void givenFarmId_whenFarmExistsById_thenReturnTrue() {
		BDDMockito.given(farmRepository.existsById(1L)).willReturn(true);
		
		boolean existsById = farmService.existsById(1L);
		
		assertThat(existsById).isTrue();
	}
	
	@Test
	public void givenFarmId_whenFarmExistsById_thenReturnFalse() {
		BDDMockito.given(farmRepository.existsById(1L)).willReturn(false);
		
		boolean existsById = farmService.existsById(1L);
		
		assertThat(existsById).isFalse();
	}

}
