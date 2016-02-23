package com.draganov.dvdlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * The entity mapping for Movie
 *
 * @author borislav.draganov
 */

@Entity
@Table(name = "MOVIE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "LENGTH")
    private int length;

    @Column(name = "REALEASEDATE")
    private String releaseDate;

    @Column(name = "IMDB")
    private String imdbId;

    @JoinColumn(name = "RATING")
    private int rating;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "MOVIE_GENRE",
            joinColumns = @JoinColumn(name = "MOVIE_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID"))
    private List<Genre> genres;

    public Movie() { }

    public Movie(String title,
                 int length,
                 String releaseDate,
                 String imdbId,
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
