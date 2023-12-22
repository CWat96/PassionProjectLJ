package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PlatingMaterial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlatingMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlatingMaterialRepository extends JpaRepository<PlatingMaterial, Long> {}
