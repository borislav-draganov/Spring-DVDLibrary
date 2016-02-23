package com.draganov.dvdlibrary.service;

import com.draganov.dvdlibrary.model.Movie;

import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
public interface MovieService {
    List<Movie> getAll();
	
    Movie getById(Long id);
	
    Movie create(Movie movie);
	
    Movie update(Movie movie);
	
    void delete(Movie movie);
	
    void deleteById(Long id);
}
