package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Effects;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface EffectsRepositoryWithBagRelationships {
    Optional<Effects> fetchBagRelationships(Optional<Effects> effects);

    List<Effects> fetchBagRelationships(List<Effects> effects);

    Page<Effects> fetchBagRelationships(Page<Effects> effects);
}
