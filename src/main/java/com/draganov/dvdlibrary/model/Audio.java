package com.draganov.dvdlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * The entity mapping for Audio
 *
 * @author borislav.draganov
 */

// @JsonIgnoreProperties - makes the Jackson JSON Processor ignore unknown fields instead of throwing an exception
@Entity
@Table(name = "AUDIO", schema = "PUBLIC")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Audio {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "NAME")
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
