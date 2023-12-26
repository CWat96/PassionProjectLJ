package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PlatingMaterial;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlatingMaterial entity.
 *
 * When extending this class, extend PlatingMaterialRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface PlatingMaterialRepository extends PlatingMaterialRepositoryWithBagRelationships, JpaRepository<PlatingMaterial, Long> {
    default Optional<PlatingMaterial> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<PlatingMaterial> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<PlatingMaterial> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
