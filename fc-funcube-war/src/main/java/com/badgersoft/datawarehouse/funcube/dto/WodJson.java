package com.badgersoft.datawarehouse.funcube.dto;

import java.util.LinkedList;

public class WodJson {
	
	private LinkedList<DataElement> series = new LinkedList<DataElement>();
	
	public WodJson() {
		
	}
	
	public LinkedList<DataElement> getSeries() {
		return series;
	}
	
	public void addElement(DataElement element) {
		series.add(element);
	}

}
