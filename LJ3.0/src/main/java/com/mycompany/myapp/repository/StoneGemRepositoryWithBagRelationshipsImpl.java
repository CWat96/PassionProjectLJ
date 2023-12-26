package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.StoneGem;
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
public class StoneGemRepositoryWithBagRelationshipsImpl implements StoneGemRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<StoneGem> fetchBagRelationships(Optional<StoneGem> stoneGem) {
        return stoneGem.map(this::fetchEffects);
    }

    @Override
    public Page<StoneGem> fetchBagRelationships(Page<StoneGem> stoneGems) {
        return new PageImpl<>(fetchBagRelationships(stoneGems.getContent()), stoneGems.getPageable(), stoneGems.getTotalElements());
    }

    @Override
    public List<StoneGem> fetchBagRelationships(List<StoneGem> stoneGems) {
        return Optional.of(stoneGems).map(this::fetchEffects).orElse(Collections.emptyList());
    }

    StoneGem fetchEffects(StoneGem result) {
        return entityManager
            .createQuery("select stoneGem from StoneGem stoneGem left join fetch stoneGem.effects where stoneGem.id = :id", StoneGem.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<StoneGem> fetchEffects(List<StoneGem> stoneGems) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, stoneGems.size()).forEach(index -> order.put(stoneGems.get(index).getId(), index));
        List<StoneGem> result = entityManager
            .createQuery(
                "select stoneGem from StoneGem stoneGem left join fetch stoneGem.effects where stoneGem in :stoneGems",
                StoneGem.class
            )
            .setParameter("stoneGems", stoneGems)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
