package com.draganov.dvdlibrary.service.impl;

import com.draganov.dvdlibrary.exceptions.DvdLibraryException;
import com.draganov.dvdlibrary.model.Audio;
import com.draganov.dvdlibrary.model.DVD;
import com.draganov.dvdlibrary.model.Language;
import com.draganov.dvdlibrary.model.Movie;
import com.draganov.dvdlibrary.repository.DvdRepository;
import com.draganov.dvdlibrary.service.AudioService;
import com.draganov.dvdlibrary.service.DvdService;
import com.draganov.dvdlibrary.service.LanguageService;
import com.draganov.dvdlibrary.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
@Service
public class DvdServiceImpl implements DvdService {
    @Autowired
	DvdRepository dvdRepository;
	
	@Autowired
	MovieService movieService;
	
	@Autowired
	AudioService audioService;

	@Autowired
	LanguageService languageService;

    public List<DVD> getAll() {
        return dvdRepository.findAll();
    }
	
    public DVD getById(Long id) {
		return dvdRepository.findOne(id);
	}
	
    public DVD create(DVD dvd) {
        // Ensure null ID to avoid update
        dvd.setId(null);

		return createOrUpdate(dvd);
	}
	
    public DVD update(DVD dvd) {
		return createOrUpdate(dvd);
	}
	
    public void delete(DVD dvd) {
		dvdRepository.delete(dvd);
	}
	
    public void deleteById(Long id) {
		dvdRepository.delete(id);
	}
	
	private DVD createOrUpdate(DVD dvd) {
		if (dvd == null) {
			throw new DvdLibraryException("Invalid entity. Sent null.");
		}

		// Ensure proper nested entities
		Movie fullMovie = movieService.getById(dvd.getMovie().getId());
		dvd.setMovie(fullMovie);

		List<Audio> audios = new ArrayList<>();
		for (Audio audio : dvd.getAudios()) {
			Audio fullAudio = audioService.getById(audio.getId());
			audios.add(fullAudio);
		}
		dvd.setAudios(audios);

		List<Language> languages = new ArrayList<>();
		for (Language language : dvd.getLanguages()) {
			Language fullLanguage = languageService.getById(language.getId());
			languages.add(fullLanguage);
		}
		dvd.setLanguages(languages);
		
		return dvdRepository.save(dvd);
	}
}
