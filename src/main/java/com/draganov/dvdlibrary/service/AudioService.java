package com.draganov.dvdlibrary.service;

import com.draganov.dvdlibrary.model.Audio;
import java.util.List;

/**
 * @author Borislav
 * @since 21/02/2016
 */
public interface AudioService {
    List<Audio> getAll();
	
    Audio getById(Long id);
	
    Audio create(Audio audio);
	
    Audio update(Audio audio);
	
    void delete(Audio audio);
	
    void deleteById(Long id);
}
