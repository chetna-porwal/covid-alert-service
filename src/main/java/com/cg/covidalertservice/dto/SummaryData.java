package com.cg.covidalertservice.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SummaryData 
{
	private int total;
	private int confirmedCasesIndian;
	private int confirmedCasesForeign;
	private int discharged;
	private int deaths;
	private int confirmedButLocationUnidentified;
	
	private ZonedDateTime updateTime;

}
