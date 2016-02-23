package com.draganov.dvdlibrary.service;

import com.draganov.dvdlibrary.model.Rating;

import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
public interface RatingService {
    List<Rating> getAll();
	
    Rating getById(Long id);
	
    Rating create(Rating rating);
	
    Rating update(Rating rating);
	
    void delete(Rating rating);
	
    void deleteById(Long id);
}
