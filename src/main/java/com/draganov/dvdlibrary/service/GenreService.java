package com.draganov.dvdlibrary.service;

import com.draganov.dvdlibrary.model.Genre;

import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
public interface GenreService {
    List<Genre> getAll();
	
    Genre getById(Long id);
	
    Genre create(Genre genre);
	
    Genre update(Genre genre);
	
    void delete(Genre genre);
	
    void deleteById(Long id);
}
