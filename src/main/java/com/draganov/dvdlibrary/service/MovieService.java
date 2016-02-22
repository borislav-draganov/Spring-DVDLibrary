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
	
    Movie create(Movie Movie);
	
    Movie update(Movie Movie);
	
    void delete(Movie Movie);
	
    void deleteById(Long id);
}
