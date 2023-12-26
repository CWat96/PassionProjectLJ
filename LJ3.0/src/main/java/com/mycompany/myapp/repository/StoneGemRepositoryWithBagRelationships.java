package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.StoneGem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface StoneGemRepositoryWithBagRelationships {
    Optional<StoneGem> fetchBagRelationships(Optional<StoneGem> stoneGem);

    List<StoneGem> fetchBagRelationships(List<StoneGem> stoneGems);

    Page<StoneGem> fetchBagRelationships(Page<StoneGem> stoneGems);
}
