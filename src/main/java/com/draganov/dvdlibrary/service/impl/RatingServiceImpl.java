package com.draganov.dvdlibrary.service.impl;

import com.draganov.dvdlibrary.exceptions.DvdLibraryException;
import com.draganov.dvdlibrary.model.Rating;
import com.draganov.dvdlibrary.repository.RatingRepository;
import com.draganov.dvdlibrary.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
	RatingRepository ratingRepository;

    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }
	
    public Rating getById(Long id) {
		return ratingRepository.findOne(id);
	}
	
    public Rating create(Rating rating) {
        // Ensure null ID to avoid update
        rating.setId(null);

		return createOrUpdate(rating);
	}
	
    public Rating update(Rating rating) {
		return createOrUpdate(rating);
	}
	
    public void delete(Rating rating) {
		ratingRepository.delete(rating);
	}
	
    public void deleteById(Long id) {
		ratingRepository.delete(id);
	}
	
	private Rating createOrUpdate(Rating rating) {
		if (rating == null) {
			throw new DvdLibraryException("Invalid entity. Sent null.");
		}
		
		return ratingRepository.save(rating);
	}
}
