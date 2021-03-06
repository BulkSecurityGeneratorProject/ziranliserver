package com.alienlab.ziranli.repository;

import com.alienlab.ziranli.domain.Exhibition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Exhibition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition,Long> {

}
