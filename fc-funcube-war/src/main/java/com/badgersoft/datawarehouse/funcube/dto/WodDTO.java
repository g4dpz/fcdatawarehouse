package com.badgersoft.datawarehouse.funcube.dto;

import java.util.LinkedList;

public class WodDTO {
	
	private LinkedList<DataElement> series = new LinkedList<DataElement>();
	
	public WodDTO() {
		
	}
	
	public LinkedList<DataElement> getSeries() {
		return series;
	}
	
	public void addElement(DataElement element) {
		series.add(element);
	}

}
