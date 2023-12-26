package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PlatingMaterial;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PlatingMaterialRepositoryWithBagRelationships {
    Optional<PlatingMaterial> fetchBagRelationships(Optional<PlatingMaterial> platingMaterial);

    List<PlatingMaterial> fetchBagRelationships(List<PlatingMaterial> platingMaterials);

    Page<PlatingMaterial> fetchBagRelationships(Page<PlatingMaterial> platingMaterials);
}
