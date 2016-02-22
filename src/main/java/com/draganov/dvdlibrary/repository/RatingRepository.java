package com.draganov.dvdlibrary.repository;

import com.draganov.dvdlibrary.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Borislav
 * @since 21/02/2016
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
