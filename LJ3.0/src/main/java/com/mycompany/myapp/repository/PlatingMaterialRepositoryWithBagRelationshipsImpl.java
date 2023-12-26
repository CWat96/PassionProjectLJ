package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PlatingMaterial;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PlatingMaterialRepositoryWithBagRelationshipsImpl implements PlatingMaterialRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PlatingMaterial> fetchBagRelationships(Optional<PlatingMaterial> platingMaterial) {
        return platingMaterial.map(this::fetchEffects).map(this::fetchMaterialsideeffects);
    }

    @Override
    public Page<PlatingMaterial> fetchBagRelationships(Page<PlatingMaterial> platingMaterials) {
        return new PageImpl<>(
            fetchBagRelationships(platingMaterials.getContent()),
            platingMaterials.getPageable(),
            platingMaterials.getTotalElements()
        );
    }

    @Override
    public List<PlatingMaterial> fetchBagRelationships(List<PlatingMaterial> platingMaterials) {
        return Optional.of(platingMaterials).map(this::fetchEffects).map(this::fetchMaterialsideeffects).orElse(Collections.emptyList());
    }

    PlatingMaterial fetchEffects(PlatingMaterial result) {
        return entityManager
            .createQuery(
                "select platingMaterial from PlatingMaterial platingMaterial left join fetch platingMaterial.effects where platingMaterial.id = :id",
                PlatingMaterial.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<PlatingMaterial> fetchEffects(List<PlatingMaterial> platingMaterials) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, platingMaterials.size()).forEach(index -> order.put(platingMaterials.get(index).getId(), index));
        List<PlatingMaterial> result = entityManager
            .createQuery(
                "select platingMaterial from PlatingMaterial platingMaterial left join fetch platingMaterial.effects where platingMaterial in :platingMaterials",
                PlatingMaterial.class
            )
            .setParameter("platingMaterials", platingMaterials)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    PlatingMaterial fetchMaterialsideeffects(PlatingMaterial result) {
        return entityManager
            .createQuery(
                "select platingMaterial from PlatingMaterial platingMaterial left join fetch platingMaterial.materialsideeffects where platingMaterial.id = :id",
                PlatingMaterial.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<PlatingMaterial> fetchMaterialsideeffects(List<PlatingMaterial> platingMaterials) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, platingMaterials.size()).forEach(index -> order.put(platingMaterials.get(index).getId(), index));
        List<PlatingMaterial> result = entityManager
            .createQuery(
                "select platingMaterial from PlatingMaterial platingMaterial left join fetch platingMaterial.materialsideeffects where platingMaterial in :platingMaterials",
                PlatingMaterial.class
            )
            .setParameter("platingMaterials", platingMaterials)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
