package com.draganov.dvdlibrary.repository;

import com.draganov.dvdlibrary.model.DVD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Borislav
 * @since 21/02/2016
 */
@Repository
public interface DvdRepository extends JpaRepository<DVD, Long> {
}
