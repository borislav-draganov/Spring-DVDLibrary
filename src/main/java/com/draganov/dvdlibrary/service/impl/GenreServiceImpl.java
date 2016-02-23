package com.draganov.dvdlibrary.service.impl;

import com.draganov.dvdlibrary.exceptions.DvdLibraryException;
import com.draganov.dvdlibrary.model.Genre;
import com.draganov.dvdlibrary.repository.GenreRepository;
import com.draganov.dvdlibrary.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
	GenreRepository genreRepository;

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
	
    public Genre getById(Long id) {
		return genreRepository.findOne(id);
	}
	
    public Genre create(Genre genre) {
        // Ensure null ID to avoid update
        genre.setId(null);

		return createOrUpdate(genre);
	}
	
    public Genre update(Genre genre) {
		return createOrUpdate(genre);
	}
	
    public void delete(Genre genre) {
		genreRepository.delete(genre);
	}
	
    public void deleteById(Long id) {
		genreRepository.delete(id);
	}
	
	private Genre createOrUpdate(Genre genre) {
		if (genre == null) {
			throw new DvdLibraryException("Invalid entity. Sent null.");
		}
		
		return genreRepository.save(genre);
	}
}
