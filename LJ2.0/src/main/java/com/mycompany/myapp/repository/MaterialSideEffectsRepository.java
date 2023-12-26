package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MaterialSideEffects;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MaterialSideEffects entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialSideEffectsRepository extends JpaRepository<MaterialSideEffects, Long> {}
