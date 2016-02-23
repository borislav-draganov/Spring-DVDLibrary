package com.draganov.dvdlibrary.model.util;

import com.draganov.dvdlibrary.model.Audio;
import com.draganov.dvdlibrary.model.Language;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * An utility class used for the Language and Audio pairs of a DVD
 *
 * @author borislav.draganov
 */

//@Entity
//@Table(name = "DVD_AUDIO_LANGUAGE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageAudioPair {
    @JoinColumn(name = "AUDIO_ID")
    private Language language;

    @JoinColumn(name = "LANGUAGE_ID")
    private Audio audio;

    public LanguageAudioPair() { }

    public LanguageAudioPair(Language language, Audio audio) {
        this.language = language;
        this.audio = audio;
    }

    // Getters and Setters
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }
}
