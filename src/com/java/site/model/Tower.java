package com.java.site.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/**
 * Entity implementation class for Entity: Tower (database table)
 *
 */
@Entity
@NamedQuery(name="Tower.findAll", query="SELECT t FROM Tower t")
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Tower implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@XmlAttribute
	private int id;

	@XmlAttribute
	private double height;

	@XmlAttribute
	private String name;

	@XmlAttribute
	private int sides;
	
	//One to many association with Equipment
	@OneToMany(mappedBy="tower", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	@XmlElement(name="equipment")
	private List<Equipment> equipments = new ArrayList<Equipment>();

	//Many to one association to Site
	@ManyToOne
	@JoinColumn(name="siteId")
	@XmlTransient
	private Site site;

	public Tower() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSides() {
		return sides;
	}

	public void setSides(int sides) {
		this.sides = sides;
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	public Equipment addEquipment(Equipment equipment) {
		getEquipments().add(equipment);
		equipment.setTower(this);
		return equipment;
	}

	public Equipment removeEquipment(Equipment equipment) {
		getEquipments().remove(equipment);
		equipment.setTower(null);
		return equipment;
	}
   
}
