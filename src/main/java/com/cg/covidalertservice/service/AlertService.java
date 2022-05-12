package com.cg.covidalertservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.covidalertservice.dto.AlertStatus;
import com.cg.covidalertservice.dto.StateData;
import com.cg.covidalertservice.dto.SummaryData;

@Service
public class AlertService 
{
	@Autowired
	private Covid19DataProvider covid19DataProvider;
	
	public AlertStatus getAlertAboutState(String state)
	{
		AlertStatus alertStatus = new AlertStatus();
		
		//buisness logic to derive the alert here
		StateData stateData = covid19DataProvider.getStateData(state);
		
		alertStatus.setSummaryData(stateData);
		
		if(stateData.getTotalConfirmed() < 1000)
		{
			alertStatus.setAlertLevel("GREEN");
			alertStatus.setMeasuresToBeTaken(Arrays.asList("Everything is normal !!"));
		}
		else if (stateData.getTotalConfirmed() >= 1000 && stateData.getTotalConfirmed() < 10000) 
		{
			alertStatus.setAlertLevel("ORANGE");
			alertStatus.setMeasuresToBeTaken(Arrays.asList("Only essential services are allowed.","Providing list of essential services"));
		}
		else
		{
			alertStatus.setAlertLevel("RED");
			alertStatus.setMeasuresToBeTaken(Arrays.asList("Absolute Lockdown.","Only medical and food services are allowed here."));
		}
		return alertStatus;
	}

	public SummaryData getOverAllSummary() 
	{
		return covid19DataProvider.getSummaryData();
	}

}
