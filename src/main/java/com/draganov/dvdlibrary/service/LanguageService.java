package com.draganov.dvdlibrary.service;

import com.draganov.dvdlibrary.model.Language;

import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
public interface LanguageService {
    List<Language> getAll();
	
    Language getById(Long id);
	
    Language create(Language Language);
	
    Language update(Language Language);
	
    void delete(Language Language);
	
    void deleteById(Long id);
}
