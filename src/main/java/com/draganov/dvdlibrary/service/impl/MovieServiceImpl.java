package com.draganov.dvdlibrary.service.impl;

import com.draganov.dvdlibrary.exceptions.DvdLibraryException;
import com.draganov.dvdlibrary.model.Genre;
import com.draganov.dvdlibrary.model.Movie;
import com.draganov.dvdlibrary.repository.MovieRepository;
import com.draganov.dvdlibrary.service.GenreService;
import com.draganov.dvdlibrary.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private GenreService genreService;

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }
	
    public Movie getById(Long id) {
		return movieRepository.findOne(id);
	}
	
    public Movie create(Movie movie) {
        // Ensure null ID to avoid update
        movie.setId(null);

		return createOrUpdate(movie);
	}
	
    public Movie update(Movie movie) {
		return createOrUpdate(movie);
	}
	
    public void delete(Movie movie) {
		movieRepository.delete(movie);
	}
	
    public void deleteById(Long id) {
		movieRepository.delete(id);
	}
	
	private Movie createOrUpdate(Movie movie) {
		if (movie == null) {
			throw new DvdLibraryException("Invalid entity. Sent null.");
		}

		// Ensure proper nested entities
		List<Genre> genres = new ArrayList<>();
		for (Genre genre : movie.getGenres()) {
			Genre fullGenre = genreService.getById(genre.getId());
			genres.add(fullGenre);
		}
		movie.setGenres(genres);
		
		return movieRepository.save(movie);
	}
}
