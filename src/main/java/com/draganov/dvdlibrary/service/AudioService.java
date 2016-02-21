package com.draganov.dvdlibrary.service;

import com.draganov.dvdlibrary.model.Audio;
import com.draganov.dvdlibrary.repository.AudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
@Service
public class AudioService {
    @Autowired
    AudioRepository audioRepository;

    public List<Audio> getAll() {
        return audioRepository.findAll();
    }
}
