package com.cg.covidalertservice.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.cg.covidalertservice.dto.CountryData;
import com.cg.covidalertservice.dto.CovidApiData;
import com.cg.covidalertservice.dto.StateData;
import com.cg.covidalertservice.dto.SummaryData;

public class Covid19DataProviderTest 
{
	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private Covid19DataProvider covid19DataProvider;
	
	{
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void getStateDataTest()
	{
		CovidApiData covidApiData =new CovidApiData();
		CountryData countryData = new CountryData();
		SummaryData summaryData = new SummaryData();
		StateData sd = new StateData();
		
		sd.setDeaths(2);
		sd.setLoc("Delhi");
		sd.setDischarged(4);
		sd.setConfirmedCasesIndian(1000);
		sd.setConfirmedCasesForeign(0);
		sd.setTotalConfirmed(1000);
		
		
		/*
		 * summaryData.setTotal(100); summaryData.setDeaths(2);
		 * summaryData.setDischarged(1); summaryData.setConfirmedCasesIndian(90);
		 * summaryData.setConfirmedCasesForeign(10);
		 * summaryData.setUpdateTime(ZonedDateTime.now());
		 */
		countryData.setSummary(summaryData);
		countryData.setRegional(new StateData[] {sd});
		
		covidApiData.setData(countryData);
		//covidApiData.setSuccess(true);
		//covidApiData.setLastRefreshed(ZonedDateTime.now());
		
		when(restTemplate.getForObject(ArgumentMatchers.anyString(), any())).thenReturn(covidApiData);
		
		StateData sd1= covid19DataProvider.getStateData("Delhi");
		
		assertAll(
				() -> assertEquals("Delhi", sd1.getLoc()),
				() -> assertEquals(2, sd1.getDeaths()),
				() -> assertEquals(1000, sd1.getTotalConfirmed()),
				() -> assertEquals(1000, sd1.getConfirmedCasesIndian()),
				() -> assertEquals(0, sd1.getConfirmedCasesForeign()),
				() -> assertEquals(4, sd1.getDischarged())
				
				);
	}
	
	@Test
	@DisplayName("state data provider - no data found")
	void getStateDataTestNoDataFoundForState()
	{
		CovidApiData covidApiData =new CovidApiData();
		CountryData countryData = new CountryData();
		SummaryData summaryData = new SummaryData();
		StateData sd = new StateData();
		
		sd.setDeaths(2);
		sd.setLoc("Delhi");
		sd.setDischarged(4);
		sd.setConfirmedCasesIndian(1000);
		sd.setConfirmedCasesForeign(0);
		sd.setTotalConfirmed(1000);
		
		countryData.setSummary(summaryData);
		countryData.setRegional(new StateData[] {sd});
		
		covidApiData.setData(countryData);
		//covidApiData.setSuccess(true);
		//covidApiData.setLastRefreshed(ZonedDateTime.now());
		
		when(restTemplate.getForObject(ArgumentMatchers.anyString(), any())).thenReturn(covidApiData);
		
		StateData sd1= covid19DataProvider.getStateData("Maharashtra");
		
		assertAll(
				() -> assertEquals(null, sd1.getLoc()),
				() -> assertEquals(0, sd1.getDeaths()),
				() -> assertEquals(0, sd1.getTotalConfirmed()),
				() -> assertEquals(0, sd1.getConfirmedCasesIndian()),
				() -> assertEquals(0, sd1.getConfirmedCasesForeign()),
				() -> assertEquals(0, sd1.getDischarged())
				
				);
	}
	
	@Test
	void getSummaryDataTest()
	{
		when(restTemplate.getForObject(ArgumentMatchers.anyString(), any())).thenReturn(getCovidApiDataForSummary());
		
		SummaryData data = covid19DataProvider.getSummaryData();
		
		assertAll(
				() -> assertEquals(0, data.getConfirmedButLocationUnidentified()),
				() -> assertEquals(2, data.getDeaths()),
				() -> assertEquals(100, data.getTotal()),
				() -> assertEquals(90, data.getConfirmedCasesIndian()),
				() -> assertEquals(10, data.getConfirmedCasesForeign()),
				() -> assertEquals(1, data.getDischarged())
				
				);
	
		
	}
	private CovidApiData getCovidApiDataForSummary()
	{
		CovidApiData covidApiData =new CovidApiData();
		CountryData countryData = new CountryData();
		SummaryData summaryData = new SummaryData();
		
		summaryData.setTotal(100); summaryData.setDeaths(2);
		summaryData.setDischarged(1); summaryData.setConfirmedCasesIndian(90);
		summaryData.setConfirmedCasesForeign(10);
		summaryData.setUpdateTime(ZonedDateTime.now());
		
		countryData.setSummary(summaryData);
		
		covidApiData.setData(countryData);
		covidApiData.setSuccess(true);
		covidApiData.setLastRefreshed(ZonedDateTime.now());
		
		return covidApiData; 
	}

}
