package com.cg.covidalertservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StateData 
{
	private String loc;
	private int confirmedCasesIndian;
	private int confirmedCasesForeign;
	private int discharged;
	private int deaths;
	private int totalConfirmed;
}
