package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.StoneGem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StoneGem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoneGemRepository extends JpaRepository<StoneGem, Long> {}
