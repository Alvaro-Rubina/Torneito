package org.alvarub.fulbitoapi.repository;

import org.alvarub.fulbitoapi.model.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeasonRepository extends JpaRepository<Season, Long> {

    Optional<Season> findByCode(String code);
}
