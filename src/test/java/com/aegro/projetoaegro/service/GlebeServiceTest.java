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
import com.aegro.projetoaegro.repository.GlebeRepository;

/**
 * @author Gabriel Menin
 *
 */
@SpringBootTest
public class GlebeServiceTest {
	@Autowired
	private GlebeService glebeService;
	
	@MockBean
	private GlebeRepository glebeRepository;
	
	private List<Glebe> glebes;
	
	@BeforeEach
	public void init() {
		
		glebes = new ArrayList<Glebe>();

		glebes.add(new Glebe("Talhao 1", 40));
		glebes.add(new Glebe("Talhao 2", 50));
		
	}
	
	@Test
	public void givenFarmId_whenFindAllGlebesByFarmId_thenReturnGlebeCollection() {
		BDDMockito.given(glebeRepository.findByFarmId(1L)).willReturn(glebes);
		
		Collection<Glebe> glebeList = glebeService.findAllGlebesByFarmId(1L);
		
		assertThat(glebeList).isNotEmpty();
		assertThat(glebeList.size()).isEqualTo(2);
	}
	
	@Test
	public void givenFarmIdGlebeID_whenFindGlebeByFarmId_thenReturnGlebeObject() {
		BDDMockito.given(glebeRepository.findGlebeByFarmId(1L, 1L)).willReturn(glebes.get(0));
		
		Glebe glebe = glebeService.findGlebeByFarmId(1L, 1L);
		
		assertThat(glebe).isNotNull();
		assertThat(glebe.getName()).isEqualTo(glebes.get(0).getName());
	}
	
	@Test
	public void givenGlebeId_whenFindGlebeById_thenReturnGlebeObject() {
		BDDMockito.given(glebeRepository.findById(1L)).willReturn(Optional.of(glebes.get(0)));
		
		Glebe glebe = glebeService.findGlebeById(1L);
		
		assertThat(glebe).isNotNull();
		assertThat(glebe.getName()).isEqualTo(glebes.get(0).getName());
	}
	
	@Test
	public void givenGlebeObject_whenSaveGlebe_thenReturnGlebeObject() {
		Glebe glebe = new Glebe("Talhão Amarelo", 60);
		
		BDDMockito.given(glebeRepository.save(glebe)).willReturn(glebe);
		
		Glebe savedGlebe = glebeService.saveGlebe(glebe);
		
		assertThat(savedGlebe).isNotNull();
		assertThat(savedGlebe.getName()).isEqualTo(glebe.getName());
		assertThat(savedGlebe.getArea()).isEqualTo(glebe.getArea());
	}
	
	@Test
	public void givenGlebeObject_whenDeleteGlebe_thenNothing() {
		
		BDDMockito.willDoNothing().given(glebeRepository).delete(glebes.get(0));
		
		glebeService.deleteGlebe(glebes.get(0));
		
		verify(glebeRepository, times(1)).delete(glebes.get(0));
	}
	
	
}
