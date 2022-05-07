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
import com.aegro.projetoaegro.service.FarmService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Gabriel Menin
 *
 */
@SpringBootTest
public class FarmRestControllerTest {

	@Autowired
	private FarmRestController farmRestController;

	@MockBean
	private FarmService farmService;

	private MockMvc mockMvc;

	private List<Farm> farms;

	private final String url = "/api/v1/farm";

	@BeforeEach
	public void initFarmList() {
		mockMvc = MockMvcBuilders.standaloneSetup(farmRestController).setControllerAdvice().build();
		farms = new ArrayList<Farm>();

		farms.add(new Farm("Fazenda do Zé Leôncio"));
		farms.add(new Farm("Fazenda do Tenório"));
	}

	/**
	 * GET TESTS
	 */
	@Test
	public void testGetAllFarmsSuccess() throws Exception {
		BDDMockito.given(farmService.findAllFarms()).willReturn(farms);

		this.mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.[0].name").value(farms.get(0).getName()));
	}

	@Test
	public void testGetAllFarmsFail() throws Exception {
		farms.clear();
		BDDMockito.given(farmService.findAllFarms()).willReturn(farms);

		this.mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	public void testGetFarmSuccess() throws Exception {
		BDDMockito.given(farmService.findFarmById(1L)).willReturn(farms.get(0));

		this.mockMvc.perform(get(url + "/1").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.name").value(farms.get(0).getName()));
	}

	@Test
	public void testGetFarmFail() throws Exception {
		BDDMockito.given(farmService.findFarmById(1L)).willReturn(null);

		this.mockMvc.perform(get(url + "/1").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
	}

	/**
	 * POST TESTS
	 */
	@Test
	public void testCreateFarmSuccess() throws Exception {
		Farm farmFromClient = new Farm("Fazenda do Joventino");

		ObjectMapper mapper = new ObjectMapper();
		String farmAsJson = mapper.writeValueAsString(farmFromClient);

		BDDMockito.given(farmService.existsByName(farmFromClient.getName())).willReturn(false);

		this.mockMvc.perform(post(url).content(farmAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());
	}

	@Test
	public void testCreateFarmWhenUsingNameAsNullFail() throws Exception {
		Farm farmFromClient = new Farm(null);

		ObjectMapper mapper = new ObjectMapper();
		String farmAsJson = mapper.writeValueAsString(farmFromClient);

		this.mockMvc.perform(post(url).content(farmAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateFarmWhenUsingNullEntityFail() throws Exception {
		Farm farmFromClient = null;

		ObjectMapper mapper = new ObjectMapper();
		String farmAsJson = mapper.writeValueAsString(farmFromClient);

		this.mockMvc.perform(post(url).content(farmAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateFarmWhenFarmAlreadyExistFail() throws Exception {
		Farm farmFromClient = new Farm("Fazenda do Tenório");

		ObjectMapper mapper = new ObjectMapper();
		String farmAsJson = mapper.writeValueAsString(farmFromClient);

		BDDMockito.given(farmService.existsByName(farmFromClient.getName())).willReturn(true);

		this.mockMvc.perform(post(url).content(farmAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateFarmWhenNameIsEmpty() throws Exception {
		Farm farmFromClient = new Farm("		");

		ObjectMapper mapper = new ObjectMapper();
		String farmAsJson = mapper.writeValueAsString(farmFromClient);

		this.mockMvc.perform(post(url).content(farmAsJson).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
	}
	
	/**
	 * DELETE TESTS
	 */
	@Test
	public void testDeleteFarmSuccess() throws Exception {
		
		BDDMockito.given(farmService.findFarmById(1L)).willReturn(farms.get(0));

		ObjectMapper mapper = new ObjectMapper();
		String farmAsJson = mapper.writeValueAsString(farms.get(0));

		this.mockMvc.perform(delete(url + "/1")
				.content(farmAsJson).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void testDeleteFarmWhenFarmNotExistFail() throws Exception {
		
		BDDMockito.given(farmService.findFarmById(4L)).willReturn(null);

		ObjectMapper mapper = new ObjectMapper();
		String farmAsJson = mapper.writeValueAsString(farms.get(0));

		this.mockMvc.perform(delete(url + "/4")
				.content(farmAsJson).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

}
