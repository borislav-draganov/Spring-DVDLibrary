package com.wolffangx.dvdlibrary.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wolffangx.dvdlibrary.entities.util.LanguageAudioPair;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * The entity mapping for DVD
 *
 * @author borislav.draganov
 */

// @XmlRootElement - makes the class serializable in XML and JSON
// @JsonIgnoreProperties - makes the Jackson JSON Processor ignore unknown fields instead of throwing an exception
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)

public class DVD {
    private int id;
    private Movie movie;
    private String isbn;
    private String edition;
    private String screenFormat;
    private String region;
    private List<LanguageAudioPair> languageAudioPairs;

    public DVD() { }

    public DVD(Movie movie,
               String ISBN,
               String edition,
               String screenFormat,
               String region,
               List<LanguageAudioPair> languageAudioPairs) {
        this.movie = movie;
        this.isbn = ISBN;
        this.edition = edition;
        this.screenFormat = screenFormat;
        this.region = region;
        this.languageAudioPairs = languageAudioPairs;
    }

    // Getters and Setters
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getScreenFormat() {
        return screenFormat;
    }

    public void setScreenFormat(String screenFormat) {
        this.screenFormat = screenFormat;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // @XmLElementWrapper - generates a wrapper element around XML representation
	// @XmlElement - sets the name of the entities
    @XmlElementWrapper(name = "languageAudioPairs")
    @XmlElement(name = "languageAudioPair")
    public List<LanguageAudioPair> getLanguageAudioPairs() {
        return languageAudioPairs;
    }

    public void setLanguageAudioPairs(List<LanguageAudioPair> languageAudioPairs) {
        this.languageAudioPairs = languageAudioPairs;
    }
}