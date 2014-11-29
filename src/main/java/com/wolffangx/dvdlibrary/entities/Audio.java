package com.wolffangx.dvdlibrary.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The entity mapping for Audio
 *
 * @author borislav.draganov
 */

// @XmlRootElement - makes the class serializable in XML and JSON
// @JsonIgnoreProperties - makes the Jackson JSON Processor ignore unknown fields instead of throwing an exception
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Audio {
	private int id;
	private String name;

	public Audio() { }

	public Audio(String name) {
		this.setName(name);
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
