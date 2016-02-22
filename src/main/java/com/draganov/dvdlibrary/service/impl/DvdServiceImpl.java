package com.draganov.dvdlibrary.service.impl;

import com.draganov.dvdlibrary.exceptions.DvdLibraryException;
import com.draganov.dvdlibrary.model.DVD;
import com.draganov.dvdlibrary.repository.DvdRepository;
import com.draganov.dvdlibrary.service.DvdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
@Service
public class DvdServiceImpl implements DvdService {
    @Autowired
	DvdRepository dvdRepository;

    public List<DVD> getAll() {
        return dvdRepository.findAll();
    }
	
    public DVD getById(Long id) {
		return dvdRepository.findOne(id);
	}
	
    public DVD create(DVD audio) {
        // Ensure null ID to avoid update
        audio.setId(null);

		return createOrUpdate(audio);
	}
	
    public DVD update(DVD audio) {
		return createOrUpdate(audio);
	}
	
    public void delete(DVD audio) {
		dvdRepository.delete(audio);
	}
	
    public void deleteById(Long id) {
		dvdRepository.delete(id);
	}
	
	private DVD createOrUpdate(DVD audio) {
		if (audio == null) {
			throw new DvdLibraryException("Invalid entity. Sent null.");
		}
		
		return dvdRepository.save(audio);
	}
}
