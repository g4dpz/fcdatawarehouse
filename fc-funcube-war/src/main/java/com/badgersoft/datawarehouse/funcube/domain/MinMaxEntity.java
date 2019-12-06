// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package com.badgersoft.datawarehouse.funcube.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "min_max")
public class MinMaxEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long channel;
	private Double minimum;
	private Double maximum;
	@Column(name = "ref_date")
	private Date refDate;
	private Boolean enabled;
	
	public MinMaxEntity() {
	}

	/**
	 * @param channel
	 * @param minimum
	 * @param maximum
	 */
	public MinMaxEntity(Long channel, Double minimum,
			Double maximum, Date refDate, Boolean enabled) {
		super();
		this.channel = channel;
		this.minimum = minimum;
		this.maximum = maximum;
		this.refDate = refDate;
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}

	public Double getMinimum() {
		return minimum;
	}

	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}

	public Double getMaximum() {
		return maximum;
	}

	public void setMaximum(Double maximum) {
		this.maximum = maximum;
	}

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "MinMaxEntity{" +
				"id=" + id +
				", channel=" + channel +
				", minimum=" + minimum +
				", maximum=" + maximum +
				", refDate=" + refDate +
				", enabled=" + enabled +
				'}';
	}
}

