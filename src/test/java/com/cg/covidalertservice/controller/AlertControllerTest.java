package com.cg.covidalertservice.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.cg.covidalertservice.dto.AlertStatus;
import com.cg.covidalertservice.dto.SummaryData;
import com.cg.covidalertservice.service.AlertService;

@SpringBootTest
@AutoConfigureMockMvc
public class AlertControllerTest 
{
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AlertService alertService;
	
	@Test
	void getAlertAboutStateTest() throws Exception
	{
		AlertStatus alertStatus = new AlertStatus();
		alertStatus.setAlertLevel("GREEN");
		
		Mockito.when(alertService.getAlertAboutState(ArgumentMatchers.anyString())).thenReturn(alertStatus);
		mockMvc.perform(get("/india/delhi")
		.accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
        	
	}
	
	@Test
	void getSummaryTest() throws Exception
	{
		SummaryData summaryData = new SummaryData();
		
		Mockito.when(alertService.getOverAllSummary()).thenReturn(summaryData);
		
		mockMvc.perform(get("/india/summary"))
        .andExpect(status().isOk());
        	
	}
	
	@Test
	void  invalidUrlTest() throws Exception
	{
		mockMvc.perform(get("/india1234"))
        .andExpect(status().isNotFound());
        
	}

}
