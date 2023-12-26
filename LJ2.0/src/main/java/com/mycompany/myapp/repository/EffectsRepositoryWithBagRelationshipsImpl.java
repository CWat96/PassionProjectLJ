package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Effects;
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
public class EffectsRepositoryWithBagRelationshipsImpl implements EffectsRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Effects> fetchBagRelationships(Optional<Effects> effects) {
        return effects.map(this::fetchEffects);
    }

    @Override
    public Page<Effects> fetchBagRelationships(Page<Effects> effects) {
        return new PageImpl<>(fetchBagRelationships(effects.getContent()), effects.getPageable(), effects.getTotalElements());
    }

    @Override
    public List<Effects> fetchBagRelationships(List<Effects> effects) {
        return Optional.of(effects).map(this::fetchEffects).orElse(Collections.emptyList());
    }

    Effects fetchEffects(Effects result) {
        return entityManager
            .createQuery("select effects from Effects effects left join fetch effects.effects where effects.id = :id", Effects.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Effects> fetchEffects(List<Effects> effects) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, effects.size()).forEach(index -> order.put(effects.get(index).getId(), index));
        List<Effects> result = entityManager
            .createQuery("select effects from Effects effects left join fetch effects.effects where effects in :effects", Effects.class)
            .setParameter("effects", effects)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
