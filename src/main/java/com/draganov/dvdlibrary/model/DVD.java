package com.draganov.dvdlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.draganov.dvdlibrary.model.util.LanguageAudioPair;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
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
    @JoinColumn(name = "MOVIE")
    private Movie movie;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "EDITION")
    private String edition;

    @Column(name = "SCREEN_FORMAT")
    private String screenFormat;

    @Column(name = "REGION")
    private String region;

//    @ManyToMany
//    @JoinTable(name = "DVD_AUDIO_LANGUAGE",
//            joinColumns = @JoinColumn(name = "DVD_ID"))
//    private List<LanguageAudioPair> languageAudioPairs;

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
//        this.languageAudioPairs = languageAudioPairs;
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

//    public List<LanguageAudioPair> getLanguageAudioPairs() {
//        return languageAudioPairs;
//    }
//
//    public void setLanguageAudioPairs(List<LanguageAudioPair> languageAudioPairs) {
//        this.languageAudioPairs = languageAudioPairs;
//    }
}
