package com.draganov.dvdlibrary.service;

import com.draganov.dvdlibrary.model.DVD;

import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
public interface DvdService {
    List<DVD> getAll();
	
    DVD getById(Long id);
	
    DVD create(DVD dvd);
	
    DVD update(DVD dvd);
	
    void delete(DVD dvd);
	
    void deleteById(Long id);
}
