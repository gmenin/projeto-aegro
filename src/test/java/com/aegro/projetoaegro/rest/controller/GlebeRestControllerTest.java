/**
 * 
 */
package com.aegro.projetoaegro.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.aegro.projetoaegro.service.FarmService;
import com.aegro.projetoaegro.service.GlebeService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Gabriel Menin
 *
 */
@SpringBootTest
public class GlebeRestControllerTest {

	@Autowired
	private GlebeRestController glebeRestController;

	@MockBean
	private GlebeService glebeService;

	@MockBean
	private FarmService farmService;

	private MockMvc mockMvc;

	private Farm farm;
	private List<Glebe> glebes;

	private final String url = "/api/v1";

	@BeforeEach
	public void initFarmList() {
		mockMvc = MockMvcBuilders.standaloneSetup(glebeRestController).setControllerAdvice().build();
		glebes = new ArrayList<Glebe>();

		farm = new Farm("Fazenda do Joventino");

		glebes.add(new Glebe("Talhão 1", 40));
		glebes.add(new Glebe("Talhão 2", 50));

//		glebes.get(0).setFarm(farm);
//		glebes.get(1).setFarm(farm);
//		
//		farm.addGlebe(glebes.get(0));
//		farm.addGlebe(glebes.get(1));

	}

	/**
	 * GET TESTS
	 */
	@Test
	public void testGetAllGlebesFromFarmSuccess() throws Exception {

		BDDMockito.given(glebeService.findAllGlebesByFarmId(1L)).willReturn(glebes);

		this.mockMvc.perform(get(url + "/farm/1/glebe").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.[0].name").value(glebes.get(0).getName()));
	}

	@Test
	public void testGetAllGlebesFromFarmFail() throws Exception {

		glebes.clear();

		BDDMockito.given(glebeService.findAllGlebesByFarmId(1L)).willReturn(glebes);

		this.mockMvc.perform(get(url + "/farm/1/glebe").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testGetGlebeFromFarmSuccess() throws Exception {

		BDDMockito.given(glebeService.findGlebeByFarmId(1L, 1L)).willReturn(glebes.get(0));

		this.mockMvc.perform(get(url + "/farm/1/glebe/1").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.name").value(glebes.get(0).getName()))
				.andExpect(jsonPath("$.area").value(glebes.get(0).getArea()));
	}

	@Test
	public void testGetGlebeFromFarmFail() throws Exception {

		BDDMockito.given(glebeService.findGlebeByFarmId(3L, 1L)).willReturn(null);

		this.mockMvc.perform(get(url + "/farm/3/glebe/1").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testGetGlebeSuccess() throws Exception {

		BDDMockito.given(glebeService.findGlebeById(1L)).willReturn(glebes.get(0));

		this.mockMvc.perform(get(url + "/glebe/1").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType("application/json")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(glebes.get(0).getName()))
				.andExpect(jsonPath("$.area").value(glebes.get(0).getArea()));
	}

	@Test
	public void testGetGlebeFail() throws Exception {

		BDDMockito.given(glebeService.findGlebeById(4L)).willReturn(null);

		this.mockMvc.perform(get(url + "/glebe/4").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

	/**
	 * POST TESTS
	 */
	@Test
	public void testCreateGlebeSuccess() throws Exception {
		Glebe glebeFromClient = new Glebe("Talhão Z", 80);

		ObjectMapper mapper = new ObjectMapper();
		String glebeAsJson = mapper.writeValueAsString(glebeFromClient);

		BDDMockito.given(farmService.findFarmById(1L)).willReturn(farm);

		this.mockMvc.perform(post(url + "/farm/1/glebe").content(glebeAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());
	}

	@Test
	public void testCreateGlebeWhenUsingNullEntityFail() throws Exception {
		Glebe glebeFromClient = null;

		ObjectMapper mapper = new ObjectMapper();
		String glebeAsJson = mapper.writeValueAsString(glebeFromClient);

		BDDMockito.given(farmService.findFarmById(1L)).willReturn(farm);

		this.mockMvc.perform(post(url + "/farm/1/glebe").content(glebeAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateGlebeWhenUsingInvalidFieldsFail() throws Exception {
		Glebe glebeFromClient = new Glebe();

		ObjectMapper mapper = new ObjectMapper();
		String glebeAsJson = mapper.writeValueAsString(glebeFromClient);

		BDDMockito.given(farmService.findFarmById(1L)).willReturn(farm);

		this.mockMvc.perform(post(url + "/farm/1/glebe").content(glebeAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateGlebeWhenFarmNotExistFail() throws Exception {
		Glebe glebeFromClient = new Glebe("Talhão Z", 80);

		ObjectMapper mapper = new ObjectMapper();
		String glebeAsJson = mapper.writeValueAsString(glebeFromClient);

		BDDMockito.given(farmService.findFarmById(1L)).willReturn(null);

		this.mockMvc.perform(post(url + "/farm/1/glebe").content(glebeAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
	}

	/**
	 * DELETE TESTS
	 */
	@Test
	public void testDeleteGlebeSuccess() throws Exception {
		
		glebes.get(0).setFarm(farm);
		farm.addGlebe(glebes.get(0));
		
		BDDMockito.given(glebeService.findGlebeById(1L)).willReturn(glebes.get(0));

		ObjectMapper mapper = new ObjectMapper();
		String glebeAsJson = mapper.writeValueAsString(glebes.get(0));

		this.mockMvc.perform(delete(url + "/glebe/1").content(glebeAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNoContent());
	}
	
	
	@Test
	public void testDeleteGlebeFail() throws Exception {

		BDDMockito.given(glebeService.findGlebeById(1L)).willReturn(null);

		ObjectMapper mapper = new ObjectMapper();
		String glebeAsJson = mapper.writeValueAsString(glebes.get(0));

		this.mockMvc.perform(delete(url + "/glebe/1").content(glebeAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
	}
}
