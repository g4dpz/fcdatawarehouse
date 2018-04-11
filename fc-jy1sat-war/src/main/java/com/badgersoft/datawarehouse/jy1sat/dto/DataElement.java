package com.badgersoft.datawarehouse.jy1sat.dto;

import java.util.LinkedList;

public class DataElement {
	
	private String name;

	private boolean visible = false;
	
	@SuppressWarnings("rawtypes")
	private LinkedList data = new LinkedList();

	public DataElement(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@SuppressWarnings("rawtypes")
	public LinkedList getData() {
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public void addDatum(Double value) {
		data.add(value);
	}
	
	@SuppressWarnings("unchecked")
	public void addDatum(Long value) {
		data.add(value);
	}
	
	@SuppressWarnings("unchecked")
	public void addDatum(Long value1, Double value2 ) {
		LinkedList<Number> dataPair = new LinkedList<Number>();
		dataPair.add(value1);
		dataPair.add(value2);
		data.add(dataPair);
	}

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
		return visible;
	}
}
