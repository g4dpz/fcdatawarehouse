// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package com.badgersoft.datawarehouse.rawdata.domain;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "epoch")
public class Epoch {

	@Id
	private Long satelliteId;
	private Long sequenceNumber;
	private Timestamp referenceTime;

	public Epoch() {
	}

	public Epoch(Long satelliteId, Long sequenceNumber, Timestamp referenceTime) {
		super();
		this.satelliteId = satelliteId;
		this.sequenceNumber = sequenceNumber;
		this.referenceTime = referenceTime;
	}

	public final Long getSatelliteId() {
		return satelliteId;
	}

	public final void setSatelliteId(Long satelliteId) {
		this.satelliteId = satelliteId;
	}

	public final Long getSequenceNumber() {
		return sequenceNumber;
	}

	public final void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public final Timestamp getReferenceTime() {
		return referenceTime;
	}

	public final void setReferenceTime(Timestamp referenceTime) {
		this.referenceTime = referenceTime;
	}
	

}
