package com.draganov.dvdlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * The entity mapping for DVD
 *
 * @author borislav.draganov
 */

@Entity
@Table(name = "DVD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DVD {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "EDITION")
    private String edition;

    @Column(name = "SCREEN_FORMAT")
    private String screenFormat;

    @Column(name = "REGION")
    private String region;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "DVD_AUDIO",
                joinColumns = @JoinColumn(name = "DVD_ID"),
                inverseJoinColumns = @JoinColumn(name = "AUDIO_ID"))
    private List<Audio> audios;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "DVD_LANGUAGE",
                joinColumns = @JoinColumn(name = "DVD_ID"),
                inverseJoinColumns = @JoinColumn(name = "LANGUAGE_ID"))
    private List<Language> languages;

    public DVD() { }

    public DVD(Movie movie,
               String ISBN,
               String edition,
               String screenFormat,
               String region,
               List<Audio> audios,
               List<Language> languages) {
        this.movie = movie;
        this.isbn = ISBN;
        this.edition = edition;
        this.screenFormat = screenFormat;
        this.region = region;
        this.audios = audios;
        this.languages = languages;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Audio> getAudios() {
        return audios;
    }

    public void setAudios(List<Audio> audios) {
        this.audios = audios;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
}
