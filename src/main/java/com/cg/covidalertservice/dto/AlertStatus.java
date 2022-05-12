package com.cg.covidalertservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlertStatus 
{
	private String alertLevel; //red,green,orange
	private List<String> measuresToBeTaken;	
	private StateData summaryData;
}
