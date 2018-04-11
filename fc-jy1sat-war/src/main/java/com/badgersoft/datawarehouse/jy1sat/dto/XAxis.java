package com.badgersoft.datawarehouse.jy1sat.dto;

public class XAxis {
	
	private int min;
	private int max;
	private boolean startOnTick = true;

	public XAxis() {
	}

	public void setMax(int value) {
		this.max = value;
	}

	public void setMin(int value) {
		this.min = value;
	}

	public final int getMin() {
		return min;
	}

	public final int getMax() {
		return max;
	}
	
	public boolean startOnTick() {
		return startOnTick;
	}

}
