package com.draganov.dvdlibrary.repository;

import com.draganov.dvdlibrary.model.Audio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Borislav
 * @since 21/02/2016
 */
@Repository
public interface AudioRepository extends JpaRepository<Audio, Long> {
}
