package org.alvarub.fulbitoapi.repository;

import org.alvarub.fulbitoapi.model.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {

    Optional<League> findByName(String name);
}
