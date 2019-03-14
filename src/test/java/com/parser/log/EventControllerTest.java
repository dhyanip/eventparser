package com.parser.log;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.parser.log.EventRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private AlertRepository alertRepository;
	
	String uri = "/v1/event";

	@Before
	public void deleteAllBeforeTests() throws Exception {
		eventRepository.deleteAll();
		alertRepository.deleteAll();
	}

	@Test
	public void shouldRetrieveEntity() throws Exception {

		
		Event event = new Event();
		event.setId("1");
		event.setHost("host");
		event.setProcessTime("11");
		event.setType("type");
		eventRepository.save(event);

		
		mockMvc.perform(get(uri+"?id=1")).andExpect(status().isOk()).andExpect(
				jsonPath("$..host").value("host")).andExpect(
						jsonPath("$..type").value("type"));
	}

	

	


	@Test
	public void shouldDeleteEntity() throws Exception {

		Event event = new Event();
		event.setId("1");
		event.setHost("host");
		event.setProcessTime("11");
		eventRepository.save(event);
		mockMvc.perform(delete(uri+"?id=1")).andExpect(status().isOk());

		mockMvc.perform(get(uri+"?id=1")).andExpect(status().isOk());
	}
}