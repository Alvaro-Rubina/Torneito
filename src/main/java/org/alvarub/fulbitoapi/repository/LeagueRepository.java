package org.alvarub.fulbitoapi.repository;

import org.alvarub.fulbitoapi.model.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long> {
}
