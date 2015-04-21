package com.java.site.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/**
 * Entity implementation class for Entity: Site(Database table)
 *
 */

@Entity
@NamedQuery(name="Site.findAll", query="SELECT s FROM Site s")
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Site implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@XmlAttribute
	private int id;

	@XmlAttribute
	private String latitude;
	
	@XmlAttribute
	private String longitude;

	@XmlAttribute
	private String name;

	//One to many association with Tower
	@OneToMany(mappedBy="site", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	@XmlElement(name="tower")
	private List<Tower> towers = new ArrayList<Tower>();

	public Site() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Tower> getTowers() {
		return towers;
	}

	public void setTowers(List<Tower> towers) {
		this.towers = towers;
	}
	
	public Tower addTower(Tower tower) {
		getTowers().add(tower);
		tower.setSite(this);
		return tower;
	}

	public Tower removeTower(Tower tower) {
		getTowers().remove(tower);
		tower.setSite(null);
		return tower;
	}
   
}
