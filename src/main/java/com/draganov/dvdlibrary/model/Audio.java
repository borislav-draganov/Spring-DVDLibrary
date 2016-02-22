package com.draganov.dvdlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * The entity mapping for Audio
 *
 * @author borislav.draganov
 */

@Entity
@Table(name = "AUDIO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Audio {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
