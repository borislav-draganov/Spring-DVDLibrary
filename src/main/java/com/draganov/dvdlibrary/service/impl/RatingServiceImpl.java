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
	
    public Rating create(Rating audio) {
        // Ensure null ID to avoid update
        audio.setId(null);

		return createOrUpdate(audio);
	}
	
    public Rating update(Rating audio) {
		return createOrUpdate(audio);
	}
	
    public void delete(Rating audio) {
		ratingRepository.delete(audio);
	}
	
    public void deleteById(Long id) {
		ratingRepository.delete(id);
	}
	
	private Rating createOrUpdate(Rating audio) {
		if (audio == null) {
			throw new DvdLibraryException("Invalid entity. Sent null.");
		}
		
		return ratingRepository.save(audio);
	}
}
