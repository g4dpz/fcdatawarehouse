package com.badgersoft.datawarehouse.nayif1.dto;

import java.io.Serializable;
import java.util.LinkedList;

public class HighResJson implements Serializable {
	
	private LinkedList<DataElement> series = new LinkedList<DataElement>();
	private XAxis xAxis = new XAxis();
	
	public HighResJson() {
		xAxis.setMax(0);
	}

	public LinkedList<DataElement> getSeries() {
		return series;
	}

	public void setSeries(LinkedList<DataElement> series) {
		this.series = series;
	}

	public XAxis getxAxis() {
		return xAxis;
	}

	public void setxAxis(XAxis xAxis) {
		this.xAxis = xAxis;
	}

	public void addElement(DataElement element) {
		series.add(element);
	}

	public void setMinX(int value) {
		xAxis.setMin(value);
	}
}
