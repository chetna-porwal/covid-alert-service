package com.cg.covidalertservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cg.covidalertservice.dto.AlertStatus;
import com.cg.covidalertservice.dto.StateData;
import com.cg.covidalertservice.dto.SummaryData;

public class AlertServiceTest 
{
	@InjectMocks
	private AlertService alertService;
	
	@Mock
	private Covid19DataProvider covid19DataProvider;
	
	@BeforeEach
	void setup()
	{
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	@DisplayName("When the total no. of confirmed cases are less than 1000")
	void getAlertAboutStateTestGreen()
	{
		StateData stateData= new StateData();
		stateData.setTotalConfirmed(100);
		
		Mockito.when(covid19DataProvider.getStateData(ArgumentMatchers.anyString())).thenReturn(stateData);
		AlertStatus status = alertService.getAlertAboutState("Arunachal Pradesh");
		
		assertEquals("GREEN", status.getAlertLevel());
		assertEquals(Arrays.asList("Everything is normal !!"), status.getMeasuresToBeTaken());
		assertEquals(stateData, status.getSummaryData());
		
		Mockito.verify(covid19DataProvider,Mockito.times(1)).getStateData("Arunachal Pradesh");
	}
	
	@Test
	@DisplayName("When the total no. of confirmed cases are between 1000 & 10000")
	void getAlertAboutStateTestOrange()
	{
		StateData stateData= new StateData();
		stateData.setTotalConfirmed(5000);
		
		Mockito.when(covid19DataProvider.getStateData(ArgumentMatchers.anyString())).thenReturn(stateData);
		AlertStatus status = alertService.getAlertAboutState("uttar Pradesh");
		
		assertEquals("ORANGE", status.getAlertLevel());
		assertEquals(Arrays.asList("Only essential services are allowed.","Providing list of essential services"), status.getMeasuresToBeTaken());
		assertEquals(stateData, status.getSummaryData());
		
		Mockito.verify(covid19DataProvider,Mockito.times(1)).getStateData("uttar Pradesh");
	}
	
	@Test
	@DisplayName("When the total no. of confirmed cases are greater than 1000")
	void getAlertAboutStateTestRed()
	{
		StateData stateData= new StateData();
		stateData.setTotalConfirmed(11000);
		
		Mockito.when(covid19DataProvider.getStateData(ArgumentMatchers.anyString())).thenReturn(stateData);
		AlertStatus status = alertService.getAlertAboutState("Delhi");
		
		assertEquals("RED", status.getAlertLevel());
		assertEquals(Arrays.asList("Absolute Lockdown.","Only medical and food services are allowed here."), status.getMeasuresToBeTaken());
		assertEquals(stateData, status.getSummaryData());

		Mockito.verify(covid19DataProvider).getStateData("Delhi");
	}
	
	@Test
	void getOverAllSummaryTest()
	{
		SummaryData summaryData = new SummaryData();
		summaryData.setUpdateTime(ZonedDateTime.now());
		summaryData.setConfirmedButLocationUnidentified(10);
		summaryData.setConfirmedCasesForeign(1);
		summaryData.setConfirmedCasesIndian(1000);
		summaryData.setDischarged(20);
		summaryData.setDeaths(2);
		summaryData.setTotal(1011);
		
		Mockito.when(covid19DataProvider.getSummaryData()).thenReturn(summaryData);
		
		SummaryData actualSummary = alertService.getOverAllSummary();
		
		assertEquals(summaryData, actualSummary);
	}



}
