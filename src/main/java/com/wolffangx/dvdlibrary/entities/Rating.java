package com.wolffangx.dvdlibrary.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The entity mapping for Rating
 *
 * @author borislav.draganov
 */

// @XmlRootElement - makes the class serializable in XML and JSON
// @JsonIgnoreProperties - makes the Jackson JSON Processor ignore unknown fields instead of throwing an exception
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating {
    private int id;
    private String name;

    public Rating() { }

    public Rating(String name) {
        this.name = name;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
