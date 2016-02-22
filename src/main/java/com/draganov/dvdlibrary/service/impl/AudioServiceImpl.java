package com.draganov.dvdlibrary.service.impl;

import com.draganov.dvdlibrary.exceptions.DvdLibraryException;
import com.draganov.dvdlibrary.model.Audio;
import com.draganov.dvdlibrary.repository.AudioRepository;
import com.draganov.dvdlibrary.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
@Service
public class AudioServiceImpl implements AudioService {
    @Autowired
    AudioRepository audioRepository;

    public List<Audio> getAll() {
        return audioRepository.findAll();
    }
	
    public Audio getById(Long id) {
		return audioRepository.findOne(id);
	}
	
    public Audio create(Audio audio) {
        // Ensure null ID to avoid update
        audio.setId(null);

		return createOrUpdate(audio);
	}
	
    public Audio update(Audio audio) {
		return createOrUpdate(audio);
	}
	
    public void delete(Audio audio) {
		audioRepository.delete(audio);
	}
	
    public void deleteById(Long id) {
		audioRepository.delete(id);
	}
	
	private Audio createOrUpdate(Audio audio) {
		if (audio == null) {
			throw new DvdLibraryException("Invalid entity. Sent null.");
		}
		
		return audioRepository.save(audio);
	}
}
