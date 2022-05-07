/**
 * 
 */
package com.aegro.projetoaegro.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.aegro.projetoaegro.model.Farm;
import com.aegro.projetoaegro.model.Glebe;
import com.aegro.projetoaegro.model.Production;
import com.aegro.projetoaegro.service.GlebeService;
import com.aegro.projetoaegro.service.ProductionService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Gabriel Menin
 *
 */
@SpringBootTest
public class ProductionRestControllerTest {

	@Autowired
	private ProductionRestController productionRestController;

	@MockBean
	private ProductionService productionService;

	@MockBean
	private GlebeService glebeService;

	private MockMvc mockMvc;

	private Farm farm;
	private Glebe glebe;
	private List<Production> productions;

	private final String url = "/api/v1";

	@BeforeEach
	public void initFarmList() {
		mockMvc = MockMvcBuilders.standaloneSetup(productionRestController).setControllerAdvice().build();
		productions = new ArrayList<Production>();

		farm = new Farm("Fazendo do Tibério");
		glebe = new Glebe("Talhão A", 40);

		farm.addGlebe(glebe);
		glebe.setFarm(farm);
		
		productions.add(new Production(60));
		productions.add(new Production(70));
		productions.add(new Production(80));
	}

	/**
	 * GET TESTS
	 */
	@Test
	public void testGetAllProductionsFromGlebeSuccess() throws Exception {

		BDDMockito.given(productionService.findAllProductionsByGlebeId(1L)).willReturn(productions);

		this.mockMvc.perform(get(url + "/glebe/1/production").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.[0].amount").value(productions.get(0).getAmount()));
	}

	@Test
	public void testGetAllProductionsFromGlebeWhenProductionsIsEmptyFail() throws Exception {

		productions.clear();

		BDDMockito.given(productionService.findAllProductionsByGlebeId(1L)).willReturn(productions);

		this.mockMvc.perform(get(url + "/glebe/1/production").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetAllProductionsFromGlebeWhenProductionsIsNullFail() throws Exception {

		BDDMockito.given(productionService.findAllProductionsByGlebeId(1L)).willReturn(null);

		this.mockMvc.perform(get(url + "/glebe/1/production").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetProductionFromGlebeSuccess() throws Exception {

		BDDMockito.given(productionService.findProductionByGlebeId(1L, 1L)).willReturn(productions.get(0));
		
		this.mockMvc.perform(get(url + "/glebe/1/production/1").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.amount").value(productions.get(0).getAmount()));
	}
	
	@Test
	public void testGetProductionFromGlebeFail() throws Exception {

		BDDMockito.given(productionService.findProductionByGlebeId(3L, 1L)).willReturn(null);
		
		this.mockMvc.perform(get(url + "/glebe/3/production/1").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetProductionSuccess() throws Exception {

		BDDMockito.given(productionService.findProductionById(1L)).willReturn(productions.get(0));

		this.mockMvc.perform(get(url + "/production/1").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType("application/json")).andExpect(status().isOk())
				.andExpect(jsonPath("$.amount").value(productions.get(0).getAmount()));
	}

	@Test
	public void testGetProductionFail() throws Exception {

		BDDMockito.given(glebeService.findGlebeById(4L)).willReturn(null);

		this.mockMvc.perform(get(url + "/production/4").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}
	
	/**
	 * POST TESTS
	 */
	@Test
	public void testCreateProductionSuccess() throws Exception {
		Production productionFromClient = new Production(120);

		ObjectMapper mapper = new ObjectMapper();
		String productionAsJson = mapper.writeValueAsString(productionFromClient);

		BDDMockito.given(glebeService.findGlebeById(1L)).willReturn(glebe);

		this.mockMvc.perform(post(url + "/glebe/1/production").content(productionAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());
	}

	@Test
	public void testCreateProductionWhenUsingNullEntityFail() throws Exception {
		Production productionFromClient = null;

		ObjectMapper mapper = new ObjectMapper();
		String productionAsJson = mapper.writeValueAsString(productionFromClient);

		BDDMockito.given(glebeService.findGlebeById(1L)).willReturn(glebe);

		this.mockMvc.perform(post(url + "/glebe/1/production").content(productionAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateProductionWhenGlebeNotExistFail() throws Exception {
		Production productionFromClient = new Production(120);

		ObjectMapper mapper = new ObjectMapper();
		String productionAsJson = mapper.writeValueAsString(productionFromClient);

		BDDMockito.given(glebeService.findGlebeById(1L)).willReturn(null);

		this.mockMvc.perform(post(url + "/farm/1/glebe").content(productionAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
	}
	
	/**
	 * DELETE TESTS
	 */
	
}
