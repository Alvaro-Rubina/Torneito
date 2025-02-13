package org.alvarub.fulbitoapi.repository;

import org.alvarub.fulbitoapi.model.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long> {
}
