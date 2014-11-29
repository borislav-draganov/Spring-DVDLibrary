package com.wolffangx.dvdlibrary.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * The entity mapping for Movie
 *
 * @author borislav.draganov
 */

// @XmlRootElement - makes the class serializable in XML and JSON
// @JsonIgnoreProperties - makes the Jackson JSON Processor ignore unknown fields instead of throwing an exception
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    private int id;
    private String title;
    private int length;
    private String releaseDate;
    private int imdbId;
    private int rating;
    private List<Genre> genres;

    public Movie() { }

    public Movie(String title,
                 int length,
                 String releaseDate,
                 int imdbId,
                 int rating,
                 List<Genre> genres) {
        this.title = title;
        this.length = length;
        this.releaseDate = releaseDate;
        this.imdbId = imdbId;
        this.rating = rating;
        this.genres = genres;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getImdbId() {
        return imdbId;
    }

    public void setImdbId(int imdbId) {
        this.imdbId = imdbId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // @XmLElementWrapper - generates a wrapper element around XML representation
    // @XmlElement - sets the name of the entities
    @XmlElementWrapper(name = "genres")
    @XmlElement(name = "genre")
    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
