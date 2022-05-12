package com.cg.covidalertservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryData 
{
	private SummaryData summary;
	
	private StateData[] regional;

}
