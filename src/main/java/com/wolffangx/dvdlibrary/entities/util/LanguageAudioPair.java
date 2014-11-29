package com.wolffangx.dvdlibrary.entities.util;

import com.wolffangx.dvdlibrary.entities.Audio;
import com.wolffangx.dvdlibrary.entities.Language;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * An utility class used for the Language and Audio pairs of a DVD
 *
 * @author borislav.draganov
 */

// @XmlRootElement - makes the class serializable in XML and JSON
@XmlRootElement
public class LanguageAudioPair {
    private Language language;
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
