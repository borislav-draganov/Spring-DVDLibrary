package com.draganov.dvdlibrary.service.impl;

import com.draganov.dvdlibrary.exceptions.DvdLibraryException;
import com.draganov.dvdlibrary.model.Movie;
import com.draganov.dvdlibrary.repository.MovieRepository;
import com.draganov.dvdlibrary.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
	MovieRepository movieRepository;

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }
	
    public Movie getById(Long id) {
		return movieRepository.findOne(id);
	}
	
    public Movie create(Movie audio) {
        // Ensure null ID to avoid update
        audio.setId(null);

		return createOrUpdate(audio);
	}
	
    public Movie update(Movie audio) {
		return createOrUpdate(audio);
	}
	
    public void delete(Movie audio) {
		movieRepository.delete(audio);
	}
	
    public void deleteById(Long id) {
		movieRepository.delete(id);
	}
	
	private Movie createOrUpdate(Movie audio) {
		if (audio == null) {
			throw new DvdLibraryException("Invalid entity. Sent null.");
		}
		
		return movieRepository.save(audio);
	}
}
