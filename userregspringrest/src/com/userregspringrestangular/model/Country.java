package com.userregspringrestangular.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author zulfiqar
 * This class is to hold data of all countries in world
 */

@Entity
@Table(name = "country")
//@ManagedBean(name = "country")
public class Country {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "region_id")
	private long regionId;

	//@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", insertable = false, updatable = false, nullable = false)
	private Region region;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getRegionId() {
		return regionId;
	}

	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
}
