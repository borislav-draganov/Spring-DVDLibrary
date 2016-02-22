package com.draganov.dvdlibrary.service.impl;

import com.draganov.dvdlibrary.exceptions.DvdLibraryException;
import com.draganov.dvdlibrary.model.Language;
import com.draganov.dvdlibrary.repository.LanguageRepository;
import com.draganov.dvdlibrary.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
@Service
public class LanguageServiceImpl implements LanguageService {
    @Autowired
	LanguageRepository languageRepository;

    public List<Language> getAll() {
        return languageRepository.findAll();
    }
	
    public Language getById(Long id) {
		return languageRepository.findOne(id);
	}
	
    public Language create(Language audio) {
        // Ensure null ID to avoid update
        audio.setId(null);

		return createOrUpdate(audio);
	}
	
    public Language update(Language audio) {
		return createOrUpdate(audio);
	}
	
    public void delete(Language audio) {
		languageRepository.delete(audio);
	}
	
    public void deleteById(Long id) {
		languageRepository.delete(id);
	}
	
	private Language createOrUpdate(Language audio) {
		if (audio == null) {
			throw new DvdLibraryException("Invalid entity. Sent null.");
		}
		
		return languageRepository.save(audio);
	}
}
